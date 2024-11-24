package controller;

import game.GameManager;
import game.player.Player;
import game.player.User;
import game.tile.TileManager;

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

    public static int getFirstPlayerDeckSize() {
        return firstPlayer.getDeckSize();
    }

    public static int getSecondPlayerDeckSize() {
        return secondPlayer.getDeckSize();
    }

    public static int getTileManagerSize() {
        return tileManager.getDeckSize();
    }
}
