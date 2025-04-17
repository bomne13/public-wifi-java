package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DbConnection;
import model.WifiInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WifiController {
	
	private DbConnection dbConnection = new DbConnection();
	private static OkHttpClient client = new OkHttpClient.Builder().build();
	
	private static final String apiKey = "";
	private static String url = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/TbPublicWifiInfo";
	
	// 공공 와이파이 데이터 정보를 한번에 호출할 수 있는 데이터 개수
	private int maxRequstPage = 1000;

	// 근처 와이파이 정보 가져오기
	public List<WifiInfo> getNearWifiList(double latitude, double longitude){
		System.out.println("getNearWifiList:: 호출");
		
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			ArrayList<WifiInfo> list = new ArrayList<WifiInfo>();
			
			if (!checkDatabaseIsEmpty()) {
				String sql = " SELECT *, "
							+ " ROUND(6371 * acos( "
								+ " cos(radians(?)) * cos(radians(LATITUDE)) * "
								+ " cos(radians(LONGITUDE) - radians(?)) + "
								+ " sin(radians(?)) * sin(radians(LATITUDE)) "
							+ " ), 4) as distance "
							+ " FROM WIFI_INFO ORDER BY distance LIMIT 20; ";
				statement = connection.prepareStatement(sql);
				statement.setDouble(1, latitude);
				statement.setDouble(2, longitude);
				statement.setDouble(3, latitude);
				
				rs = statement.executeQuery();
				while(rs.next()) {
					WifiInfo wifi = new WifiInfo();

					wifi.setDistance(rs.getDouble("distance"));
					wifi.setWifiNo(rs.getString("MGR_NO"));
					wifi.setDistrict(rs.getString("DISTRICT"));
					
					wifi.setWifiName(rs.getString("WIFI_NAME"));
					wifi.setAddress1(rs.getString("ADRES1"));
					wifi.setAddress2(rs.getString("ADRES2"));
					wifi.setInstallFloor(rs.getString("INSTALL_FLOOR"));
					wifi.setInstallType(rs.getString("INSTALL_TYPE"));
					wifi.setInstallMby(rs.getString("INSTALL_MBY"));
					
					wifi.setServiceType(rs.getString("SERVICE_TYPE"));
					wifi.setNetworkType(rs.getString("NETWORK_TYPE"));
					wifi.setInstallYear(rs.getString("INSTALL_YEAR"));
					wifi.setInoutDoor(rs.getString("INOUT_DOOR"));
					wifi.setWifiConnection(rs.getString("WIFI_CONNECTION"));
					
					wifi.setLatitude(rs.getDouble("LATITUDE"));
					wifi.setLongitude(rs.getDouble("LONGITUDE"));
					wifi.setWorkDate(rs.getString("WORK_DT"));
					
					list.add(wifi);
				}
				
				// 검색 기록 저장
				HistoryController historyController = new HistoryController();
				historyController.insertHistory(latitude, longitude);
				
				return list;
			} else {
				System.out.println("getNearWifiList:: 저장된 WifiInfo가 없습니다.");
				return null;
			}
		} catch(Exception e) {
			System.err.println("getNearWifiList:: Error=" + e);
			return null;
		} finally {
			dbConnection.close(null, statement);
		}
	}
	
	// 공공 와이파이 정보 DB 저장
	public String insertWiFiList() {
		System.out.println("insertWiFiList:: 호출");
		List<WifiInfo> list = getWiFiList();
		
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			String sql = " INSERT INTO WIFI_INFO VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
			statement = connection.prepareStatement(sql);
			
			int insertCount = 0;
			boolean databaseIsEmpty = checkDatabaseIsEmpty();
			
			if (list != null && !list.isEmpty()) {
				for(WifiInfo wifi : list) {
					// DB에 저장되지 않은 데이터인 경우만 추가
					if (databaseIsEmpty || checkWiFiInfoDuplicate(wifi.getWifiNo())) {
						statement.setString(1, wifi.getWifiNo());
						statement.setString(2, wifi.getDistrict());
						statement.setString(3, wifi.getWifiName());
						statement.setString(4, wifi.getAddress1());
						statement.setString(5, wifi.getAddress2());
						statement.setString(6, wifi.getInstallFloor());
						statement.setString(7, wifi.getInstallType());
						statement.setString(8, wifi.getInstallMby());
						statement.setString(9, wifi.getServiceType());
						statement.setString(10, wifi.getNetworkType());
						statement.setString(11, wifi.getInstallYear());
						statement.setString(12, wifi.getInoutDoor());
						statement.setString(13, wifi.getWifiConnection());
						statement.setDouble(14, wifi.getLatitude());
						statement.setDouble(15, wifi.getLongitude());
						statement.setString(16, wifi.getWorkDate());
						
						Integer affected = statement.executeUpdate();
						if (affected == 1) {
							// DB 추가 성공
							insertCount++;
						}
					}
				}
			}
			
			if (insertCount > 0) {
				return insertCount + "개의 WIFI 정보를 정상적으로 저장하였습니다.";
			} else {
				return "저장할 데이터가 없습니다.";
			}
		} catch(Exception e) {
			System.err.println("insertWiFiList:: Error:" + e);
			return "데이터를 DB에 저장하지 못했습니다.";
		} finally {
			dbConnection.close(null, statement);
		}
	}
	
	// 동일한 WiFiInfo가 DB에 저장되어 있는지 확인
	private boolean checkWiFiInfoDuplicate(String wifiNo) {
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			String sql = " select count(*) as count from WIFI_INFO where MGR_NO = ?; ";
			statement = connection.prepareStatement(sql);
			statement.setString(1, wifiNo);
			
			rs = statement.executeQuery();
			
			if(rs.next()) {
				int count = rs.getInt("count");
				return count > 0? true:false; 
			} else {
				return false;
			}
		} catch(Exception e) {
			System.err.println("checkWiFiInfoDuplicate:: Error:" + e);
			return false;
		} finally {
			dbConnection.close(rs, statement);
		}
	}
	
	// DB에 저장된 데이터가 있는지 확인
	private boolean checkDatabaseIsEmpty() {
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			String sql = " select count(*) as count FROM WIFI_INFO; ";
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
	
	// 공공 와이파이 정보 가져오기
	private List<WifiInfo> getWiFiList() {
		System.out.println("getWiFiList:: 호출");
		
		int count = getWiFiTotalCount();
		if (count > 0) {
			ArrayList<WifiInfo> list = new ArrayList<WifiInfo>();
			
			for(int start = 1; start < count; start += maxRequstPage) {
				int end = start + maxRequstPage - 1;
				
				Request request = new Request.Builder()
						.url(url + "/" + start + "/" + end)
						.get()
						.build();
				
				try {
					Response response = client.newCall(request).execute();
					ResponseBody body = response.body();
					
					if (response.isSuccessful() && body != null) {
						JSONObject result = new JSONObject(body.string());
						JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
						JSONArray wifiInfo = data.getJSONArray("row");
						
						for (int i = 0; i < wifiInfo.length(); i++) {
							JSONObject tmp = (JSONObject) wifiInfo.get(i);
							
							WifiInfo wifi = new WifiInfo();
							wifi.setWifiNo(tmp.getString("X_SWIFI_MGR_NO"));
							
							wifi.setDistrict(tmp.getString("X_SWIFI_WRDOFC"));
							wifi.setWifiName(tmp.getString("X_SWIFI_MAIN_NM"));
							wifi.setAddress1(tmp.getString("X_SWIFI_ADRES1"));
							wifi.setAddress2(tmp.getString("X_SWIFI_ADRES2"));
							wifi.setInstallFloor(tmp.getString("X_SWIFI_INSTL_FLOOR"));
							wifi.setInstallType(tmp.getString("X_SWIFI_INSTL_TY"));
							wifi.setInstallMby(tmp.getString("X_SWIFI_INSTL_MBY"));
							
							wifi.setServiceType(tmp.getString("X_SWIFI_SVC_SE"));
							wifi.setNetworkType(tmp.getString("X_SWIFI_CMCWR"));
							wifi.setInstallYear(tmp.getString("X_SWIFI_CNSTC_YEAR"));
							wifi.setInoutDoor(tmp.getString("X_SWIFI_INOUT_DOOR"));
							wifi.setWifiConnection(tmp.getString("X_SWIFI_REMARS3"));
							
							wifi.setLatitude(tmp.getDouble("LAT"));
							wifi.setLongitude(tmp.getDouble("LNT"));
							wifi.setWorkDate(tmp.getString("WORK_DTTM"));

							list.add(wifi);
						}
					} else {
						System.err.println("getWiFiList:: ErrorResponse: " + body);
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			
			return list;
		} else {
			return null;
		}
		
	}
	
	// 서울시 공공 와이파이 개수
	private int getWiFiTotalCount() {
		System.out.println("getWiFiTotalCount:: 호출");
		
		try {
			Request request = new Request.Builder()
					.url(url + "/1/1")
					.get()
					.build();
			Response response = client.newCall(request).execute();
			ResponseBody body = response.body();
			
			if (response.isSuccessful() && body != null) {
				JSONObject result = new JSONObject(body.string());
				JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
				return data.getInt("list_total_count");
			} else {
				System.err.println("getWiFiTotalCount:: ErrorResponse: " + body);
				return -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
