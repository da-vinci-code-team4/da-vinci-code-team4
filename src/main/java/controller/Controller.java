package controller;

import game.GameManager;
import game.player.Player;
import game.player.User;
import game.tile.TileManager;

public class Controller {
    public static void startGame(){
        System.out.println("게임시작");
        Player firstPlayer = new User("test1",100,100);
        Player secondPlayer = new User("test2",100,23991);
        TileManager tileManager = new TileManager(firstPlayer, secondPlayer);
        GameManager game = new GameManager(tileManager, firstPlayer, secondPlayer);
        game.startGame();
    }

}
