package game.tile;

/**
 * 조커 타일 클래스다.
 * Tile 클래스를 상속 받았다.
 */
public class JokerTile extends Tile {

    /**
     * @param tileColor 타일이 가지게 될 색깔을 입력 받는다
     */
    private JokerTile(TileColor tileColor) {
        super(TileType.JOKER, tileColor);
    }

    /**
     * 조커 타일을 생성하여 반환하는 메서스다.
     *
     * @param tileColor 타일이 가지게 될 색깔을 입력 받는다
     * @return 생성된 조커 타일 인스턴스를 반환한다
     */
    public static Tile of(TileColor tileColor) {
        return new JokerTile(tileColor);
    }

    @Override
    public Tile clone() {
        Tile tile = JokerTile.of(this.getTileColor());
        tile.setOpen(this.isOpened());
        tile.defineTileWeightTo(this.getWeight());
        return tile;
    }
}
