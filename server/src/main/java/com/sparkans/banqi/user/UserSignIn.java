package com.sparkans.banqi.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sparkans.banqi.db.*;

public class UserSignIn {

	private UserBean userBean;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	public UserSignIn() {
		this.userBean = new UserBean();
	}

	// validating if nickname present in Database
	public boolean validUser(String nickName, ResultSet resultSet) throws SQLException {

		if (resultSet.next())
			return true;

		return false;
	}

	public boolean signInUser(UserBean userBean) throws SQLException {

		boolean signedIn = false;
		Connection conn = null;
		try {
			conn = MySqlCon.getConnection();
			statement = conn.prepareStatement(
					"SELECT nickname, password, isActive_flag  FROM sparkans.Banqi_Users WHERE nickname =?");
			statement.setString(1, userBean.getNickname());
			resultSet = statement.executeQuery();

			if (validUser(userBean.getNickname(), resultSet)) {
				if (resultSet.getString("password").equals(userBean.getPassword())
						&& resultSet.getString("isActive_flag").equals("Y")) {

					PreparedStatement update = conn.prepareStatement("UPDATE sparkans.Banqi_Users "
							+ "SET isLoggedIn_flag = ?, lastLoggedIn_TS = ? WHERE nickname = ?");
					update.setString(1, String.valueOf('Y'));
					update.setTimestamp(2, userBean.getLastLoggedInTS());
					update.setString(3, userBean.getNickname());
					update.executeUpdate();

					System.out.println("Credentials verified. You are Logged In!!");
					if (conn != null) {
						conn.close();
					}
					signedIn = true;
					;
				} else if (!resultSet.getString("isActive_flag").equals("Y")) {
					throw new RuntimeException("User has unregistered from the Game. Please register again to play.");

				} else {
					throw new RuntimeException("Wrong Credentials entered.");
				}

			} else {
				throw new RuntimeException("Please register to play the Game!!");
			}

		} catch (SQLException e) {
			throw new RuntimeException("Something went wrong in User SignIn!!" + e.getMessage());
		} finally {
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
		return signedIn;
	}
}