package com.sparkans.banqi.game;

import com.sparkans.banqi.user.UserBean;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<BanqiBoard> boards = new ArrayList<BanqiBoard>();

    public BanqiBoard addGame(UserBean user1, UserBean user2){
        BanqiBoard b = new BanqiBoard(user1,user2);
        boards.add(b);
        return b;
    }

    public BanqiBoard getGame(String user1, String user2){
        for(BanqiBoard b : boards){
            if(b.getUser1().getNickname().equals(user1) && b.getUser2().getNickname().equals(user2)){
                return b;
            }
        }
        return null;
    }

    public boolean removeGame(UserBean user1, UserBean user2){

        return false;
    }

    public boolean pauseGame(UserBean user1, UserBean user2){

        return false;
    }

    public boolean updateBoard(String user, String src, String dest){

        for(BanqiBoard b : boards) {
            if ((b.getUser1().getNickname().equals(user) || b.getUser2().getNickname().equals(user))) {
                try {
                    b.move(src, dest);
                    return true;
                } catch (IllegalMoveException e) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean updateBoard(String user, String position){

        for(BanqiBoard b : boards) {
            if ((b.getUser1().getNickname().equals(user) || b.getUser2().getNickname().equals(user))) {
                try {
                    if(b.getPiece(position).isFaceDown = true){
                        b.getPiece(position).isFaceDown = false;
                        return true;

                    }
                } catch (IllegalPositionException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
