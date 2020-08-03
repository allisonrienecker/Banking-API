package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static Connection conn = null;

	//create private constructor to prevent this class from ever being instantiated
		private ConnectionUtil() {
			super();
		}
		
		public static Connection getConnection() {
			
			//will use DriverManager to obtain connection to DB
			//need to provide it some credential information
				//Connection String: jdbc:oracle:thin:@ENDPOINT:PORT:SID
					//jdbc:oracle:thin:@ENDPOINT:1521:ORCL
					//ENDPOINT = host in DB or can pull from AWS
				//username
				//password
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				try {
					conn = DriverManager.getConnection(
							"jdbc:oracle:thin:@training.ccuihkabjfv2.us-east-1.rds.amazonaws.com:1521:ORCL", 
							"beaver", 
							"chew"); //hard coded password
							//so if you push to GitHub everyone can see your password
							//not a good idea, VERY UNSAFE	
							//recommended to use environment variables instead
					
				} catch(SQLException e) {
					e.printStackTrace();
				}
				
			} catch(ClassNotFoundException e) {
				System.out.println("Did not find Oracle JDBC Driver Class!");
			}
			
			
			return conn;
		}
}
