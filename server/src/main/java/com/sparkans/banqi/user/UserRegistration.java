package com.sparkans.banqi.user;

import com.sparkans.banqi.db.*;
import java.sql.*;
import java.util.regex.*;

public class UserRegistration {

	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private Connection conn = null;

	// validating if email_id is unique
	public boolean validateEmail(String email, Connection conn) throws SQLException {

		boolean isValid = false;
		try {
			statement = conn.prepareStatement("SELECT 1 FROM sparkans.Banqi_Users where email_id = ? ");
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			if (resultSet.next())
				throw new RuntimeException("Email_id already exists");
			else
				isValid = true;

		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
		}
		return isValid;
	}

	// validating if nickname is unique and follows the criteria.
	public boolean validateNickName(String nickName, Connection conn) throws SQLException {

		boolean isValid = false;
		try {
			statement = conn.prepareStatement("SELECT 1 FROM sparkans.Banqi_Users where nickname = ?");
			statement.setString(1, nickName);
			resultSet = statement.executeQuery();
			String nickname_Pattern = "^[ A-Za-z0-9_.\\s]*$";
			Pattern pattern = Pattern.compile(nickname_Pattern);
			Matcher matcher = pattern.matcher(nickName);

			if (resultSet.next())
				throw new RuntimeException("Nickname already exists");
			if (!matcher.matches())
				throw new RuntimeException("Nickname entered doesnot staisfy the minimum criteria.");
			else
				isValid = true;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
		}
		return isValid;
	}

	/*
	 * Password Criteria implemented are: Be between 8 and 40 characters long.
	 * Contain at least one digit. Contain at least one lower case character.
	 * Contain at least one upper case character. Contain at least on special
	 * character from [ @ # $ % ! . ].
	 */
	public boolean validatePassword(String password) {

		boolean isValid = false;
		String password_Pattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
		Pattern pattern = Pattern.compile(password_Pattern);
		Matcher matcher = pattern.matcher(password);

		if (matcher.matches())
			isValid = true;
		else {
			throw new RuntimeException("Password entered doesnot staisfy the minimum criteria.");
		}
		return isValid;
	}

	public boolean createUser(UserBean user) throws SQLException {
		
		boolean created = false;
		try {
			conn = MySqlCon.getConnection();
			if (validateEmail(user.getEmail(),conn) && validateNickName(user.getNickname(),conn)
					&& validatePassword(user.getPassword())) 
			{
				System.out.println("\nInserting records into table...");

				String sql = "INSERT INTO sparkans.Banqi_Users"
						+ "(nickname, password, email_id, isActive_flag, created_TS, isLoggedIn_flag, lastLoggedIn_TS)"
						+ " VALUES(?, ?, ?, ?, ?, ?,?)";

				statement = conn.prepareStatement(sql);
				statement.setString(1, user.getNickname());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getEmail());
				statement.setString(4, String.valueOf('Y'));
				statement.setTimestamp(5, user.getCreateTS());
				statement.setString(6, String.valueOf('N'));
				statement.setTimestamp(7, null);

				statement.executeUpdate();
				System.out.println("You are successfully Registered in the Game!!");
				created = true;

			} 
			else 
				throw new RuntimeException("Could not Register!!");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Something went wrong in User Registration!!");
		} finally {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
			if (conn != null) 
				conn.close();
		}
		return created;
	}

}
