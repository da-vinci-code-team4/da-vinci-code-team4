package com.example.project.game.tile;

/**
 * 타일을 정보를 저장하는 클래스다.
 */
abstract public class Tile implements Comparable<Tile>{

    /**터알 거중치(정렬 시 사용)*/
    private int weight;

    /**타일 타입(숫자 or 조커)*/
    private final TileType tileType;

    /**타일 색깔(흑 or 백)*/
    private final TileColor tileColor;

    /**타일 공개 여부(기본 값은 false)*/
    private boolean isOpened;

    /**
     * @param tileType 타일의 타입을 나타낸다
     * @param tileColor 타일의 색깔을 나타낸다
     */
    public Tile(TileType tileType, TileColor tileColor) {
        this.tileType = tileType;
        this.tileColor = tileColor;
    }

    /**
     * 타일의 가중치를 설정하는 메서드다.
     *
     * @param weight 타일의 가중치를 입력 받는다
     */
    public void defineTileWeightTo(int weight) {
        this.weight = weight;
    }

    /**
     * 입력한 타일 타입과 객체의 타일 타입이 맞는지 판별하는 메서드다
     *
     * @param tileType 플레이어가 예상한 타일의 타입이다
     * @return 입력된 타입과 객체의 타입이 맞으면 true, 다르면 false를 반환한다
     */
    public boolean isTileType(TileType tileType) {
        return this.tileType.equals(tileType);
    }

    /**
     * Comparable의 compareTo를 오버라이딩 한 메서드며, 정렬 시 사용된다.
     *
     * @param o 비교될 타일이 입력된다
     * @return 타일 간 가중치가 동일하다면 흑색 타일이 우선, 그 외에는 가중치가 작을 수록 우선한다
     */
    @Override
    public int compareTo(Tile o) {
        //가중치가 동일하면서 색상이 다른 경우 흑이 먼저 옴
        if (this.weight == o.weight) {
            return this.tileColor.compareTo(o.tileColor);
        }
        //가중치가 작을수록 우선
        return Integer.compare(this.weight, o.weight);
    }

    /**
     * 타일의 가중치를 반환하는 메서드다.
     *
     * @return 필드에 저장된 타일의 가중치를 반환한다
     */
    public int getWeight() {
        return weight;
    }

    public TileType getTileType() {
        return tileType;
    }

    public TileColor getTileColor() {
        return tileColor;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public abstract Tile clone();

    public void setOpen(boolean opened) {
        isOpened = opened;
    }
}
