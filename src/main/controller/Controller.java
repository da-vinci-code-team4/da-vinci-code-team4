package main.controller;

import main.game.GameManager;
import main.game.player.Player;
import main.game.player.User;
import main.game.tile.TileManager;

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
