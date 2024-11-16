package game.player;

import game.tile.Tile;
import game.tile.TileManager;

import static game.tile.TileType.*;

abstract public class Player {

    private final TileManager tileManager;
    private final String name;

    public Player(TileManager tileManager, String name) {
        this.tileManager = tileManager;
        this.name = name;
    }

    public boolean turnStart() {
        Tile newTile = getTileFromDeque();
        if (newTile.isTileType(JOKER)) {
            insertJokerTile();
        }

        Tile selectedTile = selectOpponentPlayerTile();
        //만약 같으면 chooseToKeepTurn() 호출
        return false;
    }

    public Tile getTileFromDeque() {
        //턴마다 (남아 있다면)타일을 하나씩 가져옴
        return null;
    }

    abstract public Tile selectOpponentPlayerTile();


    abstract public int insertJokerTile();

    abstract public boolean chooseToKeepTurn();
}
