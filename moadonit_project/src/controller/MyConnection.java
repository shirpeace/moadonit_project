package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	private Connection connection;
	private static final String url = "jdbc:mysql://132.75.252.108:3306/ms2016"; 

	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");  //register with DriverManager
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 

	}
	
	public MyConnection() throws SQLException, Exception {
				
		connection = DriverManager.getConnection(url, "ms2016", "r118i67");
	}


	public Connection getConnection() {
		//System.out.println(connection.toString());
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
			System.out.println("Connection closed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
