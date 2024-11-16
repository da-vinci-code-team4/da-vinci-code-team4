package game.tile;

public class NumberTile extends Tile {

    private int number;

    public NumberTile(TileType tileType, TileColor tileColor, int number) {
        super(tileType, tileColor);
        this.number = number;
    }
}
