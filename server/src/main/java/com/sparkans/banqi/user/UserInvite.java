package com.sparkans.banqi.user;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sparkans.banqi.db.MySqlCon;


//createInvite(user1,user2)
//getInvites(user) return list of invites for user
//updateInivte(user1,user2,String status)
//getStatus(user1,user2) return status

public class UserInvite {

	private Connection conn = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	public boolean checkInvitedUser(String user, Connection conn) throws SQLException {

		try {
			statement = conn.prepareStatement("SELECT nickname,isActive_flag FROM sparkans.Banqi_Users WHERE nickname =?");
			statement.setString(1, user);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				if (resultSet.getString("isActive_flag").equals("Y"))
					return true;
				else {
					System.out.println("User has unregistered from the Game. Please register again to play.");
					return false;
				}
			} else {
				System.out.println("Please register to play!!");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong in checkInvitedUser()!!");
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
		return false;
	}

	public boolean createInvite(String user1, String user2) throws SQLException {

		boolean invited = false;
		try {
			conn = MySqlCon.getConnection();
			if (checkInvitedUser(user2, conn)) 
			{
				String sql = "INSERT INTO sparkans.Banqi_Invitation(sent_user, received_user, status) VALUES(?, ?, ?)";

				statement = conn.prepareStatement(sql);
				statement.setString(1, user1);
				statement.setString(2, user2);
				statement.setString(3, "Waiting");

				statement.executeUpdate();
				System.out.println("Invitation sent!!");
				invited = true;
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong in createInvite()!! \n" + e.getMessage());
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
		return invited;
	}

	public List<InviteObject> getSentInvites(String user) throws SQLException {

		InviteObject invObj = new InviteObject();
		List<InviteObject> sentInviteList = new ArrayList<>();
		try {
			conn = MySqlCon.getConnection();
			statement = conn.prepareStatement("SELECT received_user,status FROM sparkans.Banqi_Invitation WHERE sent_user =?");
			statement.setString(1, user);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				invObj.setReceivedUser(resultSet.getString("received_user"));
				invObj.setStatus(resultSet.getString("status"));
				sentInviteList.add(invObj);
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong in getSentInvites()!!");
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
		return sentInviteList;
	}
	
	public List<InviteObject> getReceivedInvites(String user) throws SQLException {

		InviteObject invObj = new InviteObject();
		List<InviteObject> recInviteList = new ArrayList<>();
		try {
			conn = MySqlCon.getConnection();
			statement = conn.prepareStatement("SELECT sent_user,status FROM sparkans.Banqi_Invitation WHERE received_user =?");
			statement.setString(1, user);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				invObj.setSentUser(resultSet.getString("sent_user"));
				invObj.setStatus(resultSet.getString("status"));
				recInviteList.add(invObj);
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong in getReceivedInvites()!!");
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
		return recInviteList;
	}

	public void updateInvite(String user1, String user2, String status) throws SQLException {

		try {
			conn = MySqlCon.getConnection();
			String sql = "UPDATE sparkans.Banqi_Invitation SET status=?"
					+ "WHERE sent_user =? AND received_user=? AND status = ?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, status);
			statement.setString(2, user1);
			statement.setString(3, user2);
			statement.setString(4, "Waiting");

			statement.executeUpdate();
			System.out.println("Invitation status Updated!!");

		} catch (SQLException e) {
			System.out.println("Something went wrong in updateInvite()!!");
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
	}

	public String getStatus(String user1, String user2) throws SQLException {

		String inviteStatus = null;
		try {
			conn = MySqlCon.getConnection();
			statement = conn.prepareStatement("SELECT status FROM sparkans.Banqi_Invitation WHERE sent_user=? AND received_user=?");
			statement.setString(1, user1);
			statement.setString(2, user2);
			resultSet = statement.executeQuery();

			if(resultSet.next())
				inviteStatus = resultSet.getString("status");

		} catch (SQLException e) {
			System.out.println("Something went wrong in getStatus()!!");
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
		return inviteStatus;
	}
}
