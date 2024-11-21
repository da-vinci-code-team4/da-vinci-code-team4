package main.game.player;

import java.util.ArrayList;

import main.game.tiles.Tile;
import main.game.tiles.TileManager;

public class User extends Player{
    
    public User(TileManager tileManager, String name, int rating, ArrayList<Tile> deck){
        super(tileManager, name, rating, deck);
    }
}
