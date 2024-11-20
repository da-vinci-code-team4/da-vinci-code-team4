package main.game.tiles;

import static main.game.tiles.TileType.*;

public class JokerTile extends Tile {

    public JokerTile(TileType tileType, TileColor tileColor) {
        super(tileType, tileColor);
    }

    public static Tile of(TileColor tileColor) {
        return new JokerTile(JOKER, tileColor);
    }
}
