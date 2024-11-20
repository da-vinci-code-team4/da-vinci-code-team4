package game.tile;

/**
 * 숫자 타일 클래스다.
 * Tile 클래스를 상속 받았다.
 */
public class NumberTile extends Tile {

    /**
     * 숫자 타일 간 가중치 차이다.
     * 타일이 가진 숫자의 10배가 각 숫자 타일이 가진 가중치다.
     */
    private static final int TILE_WEIGHT_GAP = 10;

    /**타일의 숫자를 나타낸다.*/
    private int number;

    /**
     * @param tileColor 타일이 가지게 될 색깔을 입력 받는다
     * @param number 타일이 가지게 될 숫자를 입력 받는다
     */
    private NumberTile(TileColor tileColor, int number) {
        super(TileType.NUMBER, tileColor);
        this.number = number;
        defineTileWeightTo(this.number * TILE_WEIGHT_GAP);
    }

    /**
     * 숫자 타일을 생성하여 반환하는 메서스다.
     *
     * @param tileColor 타일이 가지게 될 색깔을 입력 받는다
     * @param number 타일이 가지게 될 숫자를 입력 받는다
     * @return 생성된 숫자 타일을 반환한다
     */
    public static Tile of(TileColor tileColor, int number) {
        return new NumberTile(tileColor, number);
    }
}
