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
	private String nickName;
	private String email_id;
	private String isActive_flag;
	private String isLoggedIn_flag; 

	public UserObject() {

	}

	public UserBean returnUser(String user) throws SQLException {
		conn = MySqlCon.getConnection();
		UserBean userObject = new UserBean();
		
		try
		{
			statement = conn.prepareStatement("SELECT nickname, email_id, isActive_flag, isLoggedIn_flag  "
					+ "FROM sparkans.Banqi_Users WHERE nickname =?");
			statement.setString(1, user);
			resultSet = statement.executeQuery();

			userObject.nickName = resultSet.getString("nickname");
			userObject.email = resultSet.getString("email_id");
			userObject.isActive_flag = resultSet.getString("isActive_flag");
			userObject.isLoggedIn_flag = resultSet.getString("isLoggedIn_flag");

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


