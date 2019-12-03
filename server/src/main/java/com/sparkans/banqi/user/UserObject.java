package com.sparkans.banqi.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sparkans.banqi.db.MySqlCon;

public class UserObject {

	private Connection conn = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	public UserBean returnUser(String user) throws SQLException {
		conn = MySqlCon.getConnection();
		UserBean userObject = new UserBean();
		
		try
		{
			statement = conn.prepareStatement("SELECT nickname, email_id, isActive_flag, isLoggedIn_flag  "
					+ "FROM sparkans.Banqi_Users WHERE nickname =?");
			statement.setString(1, user);
			resultSet = statement.executeQuery();

			userObject.setNickName(resultSet.getString("nickname"));
			userObject.setEmail(resultSet.getString("email_id")); 
			userObject.setActive(resultSet.getString("isActive_flag"));
			userObject.setLoggedIn(resultSet.getString("isLoggedIn_flag"));

		}catch (SQLException e) {
			System.out.println("Something went wrong in User Invite!!");
		}
		finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return userObject;
	}
}


