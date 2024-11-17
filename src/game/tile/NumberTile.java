package game.tile;

public class NumberTile extends Tile {
    private static final int TILE_WEIGHT_GAP = 10;
    private int number;

    public NumberTile(TileType tileType, TileColor tileColor, int number) {
        super(tileType, tileColor);
        this.number = number;
        defineTileWeightTo(this.number * TILE_WEIGHT_GAP);
    }
}
