package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DbConnection;
import model.History;

public class HistoryController {
	
	private DbConnection dbConnection = new DbConnection();
	
	// 검색 기록 저장
	public void insertHistory(double latitude, double longitude) {
		System.out.println("insertHistory:: 호출");
		
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			String sql = " INSERT INTO WIFI_HISTORY (LATITUDE, LONGITUDE, SRCH_DT, DELETE_YN)  "
						+ " VALUES (?, ?, ?, ?); ";
			statement = connection.prepareStatement(sql);
			
			statement.setDouble(1, latitude);
			statement.setDouble(2, longitude);
			statement.setString(3, LocalDate.now().toString());
			statement.setBoolean(4, false);
			
			Integer affected = statement.executeUpdate();
			if (affected == 1) {
				System.out.println("insertHistory:: DB 추가 성공");
			}
		} catch(Exception e) {
			System.err.println("insertHistory:: Error:" + e);
		} finally {
			dbConnection.close(null, statement);
		}
	}
	
	// 검색 기록 목록 가져오기
	public List<History> getHistoryList() {
		System.out.println("getHistoryList:: 호출");
		
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			ArrayList<History> list = new ArrayList<History>();
			
			if (!checkDatabaseIsEmpty()) {
				String sql = " SELECT * FROM WIFI_HISTORY WHERE DELETE_YN = FALSE ORDER BY HISOTRY_NO DESC; ";
				statement = connection.prepareStatement(sql);
				
				rs = statement.executeQuery();
				while(rs.next()) {
					History history = new History();
					
					history.setHistoryNo(rs.getInt("HISOTRY_NO"));
					history.setLatitude(rs.getDouble("LATITUDE"));
					history.setLongitude(rs.getDouble("LONGITUDE"));
					history.setSerchDate(rs.getString("SRCH_DT"));
					history.setDelete(rs.getBoolean("DELETE_YN"));
					
					list.add(history);
				}
				
				return list;
			} else {
				System.out.println("getHistoryList:: 저장된 History가 없습니다.");
				return null;
			}
		} catch(Exception e) {
			System.err.println("getHistoryList:: Error=" + e);
			return null;
		} finally {
			dbConnection.close(null, statement);
		}
	};
	
	// 검색 기록 삭제
	public String deleteHistory(int historyNo) {
		System.out.println("deleteHistory:: 호출");

		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			String sql = " UPDATE WIFI_HISTORY SET DELETE_YN = TRUE WHERE HISOTRY_NO = ?; ";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, historyNo);
			Integer affected = statement.executeUpdate();

			return affected == 1? "SUCCESS":"FAILL";
		} catch(Exception e) {
			System.err.println("deleteHistory:: Error:" + e);
			return "FAILL";
		} finally {
			dbConnection.close(null, statement);
		}
	}
	
	// DB에 저장된 데이터가 있는지 확인
	private boolean checkDatabaseIsEmpty() {
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			String sql = " select count(*) as count FROM WIFI_HISTORY; ";
			statement = connection.prepareStatement(sql);
			
			rs = statement.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("count");
				System.out.println("checkDatabaseIsEmpty:: count = " + count);
				return count < 1? true:false;
			} else {
				return true;
			}
		} catch(Exception e) {
			System.err.println("checkDatabaseIsEmpty:: Error:" + e);
			return true;
		} finally {
			dbConnection.close(rs, statement);
		}
	}
}
