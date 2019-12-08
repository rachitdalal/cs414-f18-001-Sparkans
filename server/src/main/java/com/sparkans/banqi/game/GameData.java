package com.sparkans.banqi.game;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sparkans.banqi.db.MySqlCon;

public class GameData {

	public void saveGameData(String user1, String user2, String board, String status) throws SQLException { 

		Connection conn = MySqlCon.getConnection();
		PreparedStatement statement = null;

		try {

			String sql = "INSERT INTO sparkans.Banqi_Game(user1, user2, current_state, game_status)"
					+ " VALUES (?, ?, ?, ?)";

			statement = conn.prepareStatement(sql);
			statement.setString(1, user1); 
			statement.setString(2, user2);
			statement.setString(3, board);
			statement.setString(4, status);

			statement.executeUpdate();
			System.out.println("Successfully saved the Game State to DB");

		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) 
				statement.close();
			if (conn != null) 
				conn.close();
		}
	}

	public String loadGameData(String user1, String user2) throws SQLException, ClassNotFoundException, IOException{   

		Connection conn = MySqlCon.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			String sql = "SELECT current_state FROM sparkans.Banqi_Game "
					+ "WHERE user1 = ? OR user1 = ? AND user2 =? OR user2 = ?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, user1);
			statement.setString(2, user2);
			statement.setString(3, user1);
			statement.setString(4, user2);

			resultSet = statement.executeQuery();
			resultSet.next();

			return resultSet.getString("current_state");
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
			if (conn != null) 
				conn.close();
		}
	}
	
	public void removeGameData(String user1, String user2) throws SQLException, ClassNotFoundException, IOException{   

		Connection conn = MySqlCon.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			String sql = "DELETE FROM sparkans.Banqi_Game "
					+ "WHERE user1 = ? OR user1 = ? AND user2 =? OR user2 = ?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, user1);
			statement.setString(2, user2);
			statement.setString(3, user1);
			statement.setString(4, user2);

			resultSet = statement.executeQuery();
			resultSet.next();

		} catch (Exception e) {
			throw e;
		} finally {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
			if (conn != null) 
				conn.close();
		}
	}
}
