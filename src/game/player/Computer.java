package game.player;

import game.tile.Tile;
import game.tile.TileManager;

public class Computer extends Player{
    public Computer(TileManager tileManager, String name) {
        super(tileManager, name);
    }

    @Override
    public Tile selectOpponentPlayerTile() {
        return null;
    }

    @Override
    public int insertJokerTile() {
        return 0;
    }

    @Override
    public boolean chooseToKeepTurn() {
        return false;
    }
}
