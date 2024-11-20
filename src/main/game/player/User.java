package main.game.player;

import java.util.ArrayList;

import main.game.tiles.Tile;

public class User extends Player{
    
    public User(String name, int rating, ArrayList<Tile> deck){
        super(name, rating, deck);
    }
}
