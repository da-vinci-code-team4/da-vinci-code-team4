package main.game.player;

import main.game.tiles.Tile;
import main.game.tiles.TileManager;

import java.util.ArrayList;

public class Computer extends Player{

    public Computer(TileManager tileManager, String name, int rating, ArrayList<Tile> deck){
        super(tileManager, name, rating, deck);
    }
    
}
