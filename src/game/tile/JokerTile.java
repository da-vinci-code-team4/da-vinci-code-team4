package game.tile;

import static game.tile.TileType.*;

public class JokerTile extends Tile {

    public JokerTile(TileType tileType, TileColor tileColor) {
        super(tileType, tileColor);
    }

    public static Tile of(TileColor tileColor) {
        return new JokerTile(JOKER, tileColor);
    }
}
