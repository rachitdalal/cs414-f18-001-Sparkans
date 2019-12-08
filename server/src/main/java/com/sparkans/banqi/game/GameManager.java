package com.sparkans.banqi.game;

import com.sparkans.banqi.db.SaveLoadGame;
import com.sparkans.banqi.user.UserBean;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<BanqiBoard> boards = new ArrayList<BanqiBoard>();

    public BanqiBoard addGame(UserBean user1, UserBean user2){
        BanqiBoard b = new BanqiBoard(user1,user2);
        boards.add(b);
        //add the game to the db
        SaveLoadGame.saveGame(b);
        return b;
    }

    public BanqiBoard getGame(String user1, String user2){
        user1 = user1.toLowerCase();
        user2 = user2.toLowerCase();
        for(BanqiBoard b : boards){
            if(b.getUser1().getNickname().toLowerCase().equals(user1) && b.getUser2().getNickname().toLowerCase().equals(user2)){
                return b;
            }
        }
        return null;
/*
        try{
            BanqiBoard b = SaveLoadGame.loadGame(user1,user2);
            boards.add(b);
            return b;
        }
        catch (Exception e){
            return null;
        }


 */
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
                    if(!b.playerTurn.equals(user)){
                        return false;
                    }
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
                if(!b.playerTurn.equals(user)){
                    return false;
                }
                try {
                    if(b.getPiece(position).isFaceDown && b.isFirstMove && b.playerTurn.equals(user)){
                        b.flip(position);
                        if(b.getPiece(position).getColor().equals(BanqiPiece.Color.RED)){
                            b.setRedPlayer(user);
                            if(b.getUser1().getNickname().equals(user)){
                                b.setWhitePlayer(b.getUser2().getNickname());
                            }
                            else {
                                b.setWhitePlayer(b.getUser1().getNickname());
                            }
                        }
                        else if(b.getPiece(position).getColor().equals(BanqiPiece.Color.WHITE)){
                            b.setWhitePlayer(user);
                            if(b.getUser1().getNickname().equals(user)){
                                b.setRedPlayer(b.getUser2().getNickname());
                            }
                            else {
                                b.setWhitePlayer(b.getUser1().getNickname());
                            }
                        }

                        return true;

                    }
                    else if(b.getPiece(position).isFaceDown ){
                        b.flip(position);
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
