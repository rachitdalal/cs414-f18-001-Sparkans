package com.sparkans.banqi.user;

import com.sparkans.banqi.db.*;
import java.sql.*;

public class UserSignOut {

	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private Connection conn = null;
	
	public boolean signOut(UserBean user) throws SQLException {
		
		boolean signout = false;
		try {
			conn = MySqlCon.getConnection();
			PreparedStatement update = conn.prepareStatement("UPDATE sparkans.Banqi_Users SET isLoggedIn_flag = ?"
					+ "WHERE nickname=?");
			update.setString(1, String.valueOf('N'));
			update.setString(2, user.getNickname());

			update.executeUpdate();
			
			System.out.println("Signed Out Successfully!!");
			signout = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Something went wrong in User SignOut!!");
		} finally {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
			if (conn != null) 
				conn.close();
		}
		return signout;
	}
}
