package game.tile;

abstract public class Tile {
    private final TileType tileType;
    private final TileColor tileColor;

    public Tile(TileType tileType, TileColor tileColor) {
        this.tileType = tileType;
        this.tileColor = tileColor;
    }

}
