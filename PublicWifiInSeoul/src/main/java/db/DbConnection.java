package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbConnection {
private static final String url = "jdbc:sqlite::resource:/db/PublicWifiDB.db";
	
	private Connection connection = null;
	
	public Connection getConnection() {
		try {
			// JDBC 로드
			Class.forName("org.sqlite.JDBC");
			
			// 연결 객체 생성
			connection = DriverManager.getConnection(url);
		} catch(Exception e) {
			System.err.println("JDBC 오류:" + e);
		}
 		
		return connection;
	}
	
	public void close(ResultSet resultSet, PreparedStatement statement) {
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
            }

            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
			
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch(Exception e) {
			System.err.println("JDBC 오류:" + e);
		}
	}
}
