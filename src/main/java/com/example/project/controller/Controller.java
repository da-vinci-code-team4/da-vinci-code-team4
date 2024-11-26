package com.example.project.controller;

import com.example.project.game.player.Player;
import com.example.project.game.player.User;
import com.example.project.game.tile.Tile;
import com.example.project.game.tile.TileColor;
import com.example.project.game.tile.TileType;

import com.example.project.game.manager.GameManager;
import com.example.project.game.manager.TileManager;

public class Controller {
    private static TileManager tileManager;
    private static Player firstPlayer;
    private static Player secondPlayer;

    public static void startGame(){
        System.out.println("게임시작");

        firstPlayer = new User("test1",100,100);
        secondPlayer = new User("test2",100,23991);
        tileManager = new TileManager(firstPlayer, secondPlayer);

        GameManager game = new GameManager(tileManager, firstPlayer, secondPlayer);

        game.startGame();
    }

    public static String[] placeFirstPlayerTiles(int number){
        return makeFileName(firstPlayer.getTile(number));
    }

    public static String[] placeSecondPlayerTiles(int number){
        return makeFileName(secondPlayer.getTile(number));
    }

    public static Tile placeTileManagerTiles(int number){
        //System.out.println(makeFileName(tileManager.getTile(number),1));
        // return makeFileName(tileManager.getTile(number));
        return tileManager.getTile(number);
    }

    //0번 : 파일 이름, 1번 : 타일 숫자
    public static String[] makeFileName(Tile tile) {
        String[] result = new String[2];

        if(tile.getTileType()== TileType.JOKER){
            if(tile.getTileColor() == TileColor.BLACK){
                result[0] = "BLACK";
            }
            else{
                result[0] = "WHITE";
            }
            result[1] = "joker";
        }
        else{
            if(tile.getTileColor() == TileColor.BLACK){
                result[0] = "BLACK";
                result[1] = String.valueOf(tile.getWeight()/10);
            }
            else{
                result[0] = "WHITE";
                result[1] = String.valueOf(tile.getWeight()/10);
            }
        }
        return result;
    }

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
        return firstPlayer.getDeckSize();
    }

    public static int getSecondPlayerDeckSize() {
        return secondPlayer.getDeckSize();
    }

    public static int getTileManagerSize() {
        return tileManager.getDeckSize();
    }

    public static TileManager getTileManager() {
        return tileManager;
    }

}
