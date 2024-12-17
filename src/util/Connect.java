package util;

import java.sql.*;

public class Connect {
	
	// singleton untuk establish koneksi aplikasi dengan database
	
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "Stellarfest";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	private Connection con;
	private Statement st;
	private static Connect connect;
	
	public static Connect getInstance() {
		if (connect == null) {
			synchronized(Connect.class) {				
				if(connect == null) {
					connect = new Connect();
				}
			}
		}
		return connect;
	}
	
	private Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	
//	TODO: IDK IF THIS IS LEGAL (2)???
	public Connection getConnection() {
	        try {
	            if (con == null || con.isClosed()) {
	                con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return con;
	 }
	
	
}
