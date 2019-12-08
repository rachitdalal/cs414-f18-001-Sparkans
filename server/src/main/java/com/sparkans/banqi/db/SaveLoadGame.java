package com.sparkans.banqi.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparkans.banqi.game.BanqiBoard;
import com.sparkans.banqi.game.BanqiPiece;
import com.sparkans.banqi.game.GameData;
//import com.sparkans.banqi.user.UserBean;

import java.io.IOException;
import java.sql.SQLException;

public class SaveLoadGame {

    static GsonBuilder gb = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(BanqiPiece.class, new InterfaceAdapter());
    static Gson gson = gb.create();

    public static void saveGame(BanqiBoard b, String state) {

        String board = gson.toJson(b);
        try {
            GameData.saveGameData(b.getUser1().getNickname(), b.getUser2().getNickname(), board, state);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static BanqiBoard loadGame(String user1, String user2) {

        try {
            return gson.fromJson(GameData.loadGameData(user1, user2), BanqiBoard.class);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void removeGame(String user1,String user2){
        try {
            GameData.removeGameData(user1,user2);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
