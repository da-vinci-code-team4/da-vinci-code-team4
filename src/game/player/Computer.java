package game.player;

import game.tile.Tile;
import game.tile.TileManager;
import java.util.Random;
import java.util.TreeSet;

public class Computer extends Player{
    
    Random rand = new Random();
    
    //자신의 패 + 밝혀진 패
    TreeSet<Tile> knownTiles = new TreeSet<>();

    public Computer(TileManager tileManager, String name, int rank, int score) {
        super(tileManager, name, rank, score);
    }

    @Override
    public Tile selectOpponentPlayerTile() {
        return null;
    }

    /**
     * 상대의 패를 추측하는 행동 
     * @param opponent,guessNumber,tilePosition
     * @return
     */
    public Tile selectOpponentPlayerTile(Player opponent) {
        knownTiles = myTileDeck+tileManager.getOpenTiles();
        return null;
    }

    //컴퓨터가 조커 타일을 넣는 위치 선정
    @Override
    public int inputJokerTilePosition() {
        return 0;
    }

    @Override
    public boolean chooseToKeepTurn() {
        //50프로 확률로 선택
        int val = rand.nextInt(0,2);
        if(val == 0){
            return true;
        }
        return false;
    }
}
