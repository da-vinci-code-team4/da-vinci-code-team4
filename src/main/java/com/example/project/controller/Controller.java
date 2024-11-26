package com.example.project.controller;

import com.example.project.game.player.Player;
import com.example.project.game.player.User;
import com.example.project.game.tile.Tile;
import com.example.project.game.tile.TileColor;
import com.example.project.game.tile.TileType;
import com.example.project.game.manager.GameManager;
import com.example.project.game.manager.TileManager;
import com.example.project.main.SwingMain;

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
        System.out.println("before call startGame");
        game.startGame();
    }

    public static String placeFirstPlayerTiles(int number){
        return makeFileName(firstPlayer.getTile(number));
    }

    public static String placeSecondPlayerTiles(int number){
        return makeFileName(secondPlayer.getTile(number));
    }

    public static String placeTileManagerTiles(int number){
        return makeFileName(tileManager.getTile(number),1);
    }

    public static String makeFileName(Tile tile) {
        if(tile.getTileType()== TileType.JOKER){
            if(tile.getTileColor() == TileColor.BLACK){
                return "bjoker";
            }
            else{
                return "wjoker";
            }
        }
        else{
            if(tile.getTileColor() == TileColor.BLACK){
                return "b" + tile.getWeight()/10;
            }
            else{
                return "w" + tile.getWeight()/10;
            }
        }
    }

    public static String makeFileName(Tile tile, int number) {
        if(tile.getTileColor() == TileColor.BLACK){
            return "black";
        }
        else{
            return "white";
        }
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

    public static Tile getSecondPlayerTileAt(int tileIndex) {
        return secondPlayer.getTile(tileIndex + 1);
    }
}
