package com.example.project.controller;

import com.example.project.game.player.Player;
import com.example.project.game.player.User;
import com.example.project.game.player.Computer;
import com.example.project.game.tile.Tile;
import com.example.project.game.tile.TileColor;
import com.example.project.game.tile.TileType;

import com.example.project.game.manager.GameManager;
import com.example.project.game.manager.TileManager;

public class Controller {
    private static TileManager tileManager;
    private static User user;
    private static Player opponentPlayer;

    public static void startGame(){
        System.out.println("게임시작");

        user = new User("test1",100,100,1,1);
        opponentPlayer = new Computer("test2",100,23991,1,1);
        tileManager = new TileManager(user, opponentPlayer);

        GameManager game = new GameManager(tileManager, user, opponentPlayer);
        
        user.makeTileManager(tileManager);
        opponentPlayer.makeTileManager(tileManager);

        game.startGame();
    }
    public static Tile placeFirstPlayerTiles(int number){
        return user.getTile(number);
    }

    public static Tile placeSecondPlayerTiles(int number){
        return opponentPlayer.getTile(number);
    }

    public static Tile placeTileManagerTiles(int number){
        //System.out.println(makeFileName(tileManager.getTile(number),1));
        // return makeFileName(tileManager.getTile(number));
        return tileManager.getTile(number);
    }

    // //0번 : 파일 이름, 1번 : 타일 숫자
    // public static String[] makeFileName(Tile tile) {
    //     String[] result = new String[2];

    //     if(tile.getTileType()== TileType.JOKER){
    //         if(tile.getTileColor() == TileColor.BLACK){
    //             result[0] = "BLACK";
    //         }
    //         else{
    //             result[0] = "WHITE";
    //         }
    //         result[1] = "joker";
    //     }
    //     else{
    //         if(tile.getTileColor() == TileColor.BLACK){
    //             result[0] = "BLACK";
    //             result[1] = String.valueOf(tile.getWeight()/10);
    //         }
    //         else{
    //             result[0] = "WHITE";
    //             result[1] = String.valueOf(tile.getWeight()/10);
    //         }
    //     }
    //     return result;
    // }

    public static String[] makeFileName(Tile tile, int number) {
        String[] result = new String[2];

        if(tile.getTileColor() == TileColor.BLACK){
            result[0] = "black";
        }
        else{
            result[0] = "white";
        }
        result[1] = String.valueOf(number);

        return result;
    }

    public static int getFirstPlayerDeckSize() {
        return user.getDeckSize();
    }

    public static int getSecondPlayerDeckSize() {
        return opponentPlayer.getDeckSize();
    }

    public static int getTileManagerSize() {
        return tileManager.getDeckSize();
    }

    public static void updateUserScore(int score){
        user.updateScore(score);
    }

    public static int[] updateWin(int number){
        int[] data = user.updateWin(number);
        return data;
    }

    public static TileManager getTileManager() {
        return tileManager;
    }
}

