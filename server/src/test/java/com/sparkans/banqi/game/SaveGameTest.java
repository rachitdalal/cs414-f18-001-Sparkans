package com.sparkans.banqi.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.sparkans.banqi.db.InterfaceAdapter;
import com.sparkans.banqi.user.UserBean;
import org.junit.jupiter.api.*;

public class SaveGameTest {

@Test
    public void saveGameTest() {
        UserBean user1 = new UserBean();
        user1.setNickName("user3");
        UserBean user2 = new UserBean();
        user2.setNickName("user4");

        BanqiBoard b = new BanqiBoard(user1, user2);

        try {
            GsonBuilder gb = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            gb.registerTypeAdapter(BanqiPiece.class, new InterfaceAdapter());
            Gson gson = gb.create();
            String board = gson.toJson(b);
            //GameData.saveGameData(user1.getNickname(), user2.getNickname(), board, "paused");

            BanqiBoard b2 = gson.fromJson(GameData.loadGameData(user1.getNickname(),user2.getNickname()), BanqiBoard.class);

            System.out.println(b2.getBoard()[0][0]);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
