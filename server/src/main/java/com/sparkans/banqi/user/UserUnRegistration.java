package com.sparkans.banqi.user;

import com.sparkans.banqi.db.*;
import java.sql.*;

public class UserUnRegistration {

	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private Connection conn = null;
	
	public boolean unRegister(String user) throws SQLException {
		
		boolean unregister = false;
		try {
			conn = MySqlCon.getConnection();
			PreparedStatement update = conn.prepareStatement("UPDATE sparkans.Banqi_Users SET isActive_flag = ?"
					+ "WHERE nickname=?");
			update.setString(1, String.valueOf('N'));
			update.setString(2, user);
			update.executeUpdate();
			
			System.out.println("Unregistered Successfully!!");
			unregister = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Something went wrong in User UnRegistration!!");
		} finally {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
			if (conn != null) 
				conn.close();
		}
		return unregister;
	}
}
