package com.sparkans.banqi.user;

import com.sparkans.banqi.db.MySqlCon;
import com.sparkans.banqi.game.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class History {


    private List<ArrayList<String>> allTheGames = new ArrayList<>();

    public List<ArrayList<String>> getUserHistory(String user) throws SQLException {
        Connection conn = MySqlCon.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement("SELECT * FROM sparkans.Banqi_Game WHERE user1 = ? OR user2 = ?");
            statement.setString(1, user);
            statement.setString(2,user);
            resultSet = statement.executeQuery();
            allTheGames.clear();
            while ( resultSet.next() ) {
                ArrayList<String> temp=new ArrayList<>();
                temp.add(resultSet.getString("user1"));
                temp.add(resultSet.getString("user2"));
                temp.add(resultSet.getString("current_state"));
                temp.add(resultSet.getString("game_status"));
                //TODO test it
              /*  System.out.println(temp);*/
                allTheGames.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTheGames;
    }
}
