package main.game.player;

import main.game.tile.Tile;
import main.game.tile.TileManager;
import java.util.Random;

public class Computer extends Player{
    public Computer(TileManager tileManager, String name, int rank, int score) {
        super(tileManager, name, rank, score);
    }

    @Override
    public Tile getSelectedTile() {
        return null;
    }

    @Override
    public int inputJokerTilePosition() {
        return 0;
    }

    @Override
    public boolean chooseToKeepTurn() {
        //50프로 확률로 선택
        Random rand = new Random();
        int val = rand.nextInt(0,2);
        if(val == 0){
            return true;
        }
        return false;
    }
}
