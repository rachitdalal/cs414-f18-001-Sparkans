package com.sparkans.banqi.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class History {

    private Connection conn = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public boolean getUserHistory(String user, Connection conn) throws SQLException {
        try {
            statement = conn.prepareStatement("SELECT * FROM sparkans.Banqi_Users WHERE nickname ="+ user);
            statement.setString(1, user);
            resultSet = statement.executeQuery();

            // History code will go here

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
