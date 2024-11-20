package game.tile;

abstract public class Tile implements Comparable<Tile>{
    private int weight; //정렬 때 사용할 가중치(가중치 기준 오름차순으로 정렬됨)
    private Boolean isOpen;
    private final TileType tileType;
    private final TileColor tileColor;

    public Tile(TileType tileType, TileColor tileColor) {
        this.tileType = tileType;
        this.tileColor = tileColor;
    }

    public void defineTileWeightTo(int weight) {
        this.weight = weight;
    }

    public boolean isTileType(TileType tileType) {
        return this.tileType.equals(tileType);
    }

    @Override
    public int compareTo(Tile o) {
        //가중치가 동일하면서 색상이 다른 경우 흑이 먼저 옴
        if (this.weight == o.weight) {
            return this.tileColor.compareTo(o.tileColor);
        }
        //가중치가 작을수록 우선
        return Integer.compare(this.weight, o.weight);
    }

    public int getWeight() {
        return weight;
    }

    public Boolean getState(){
        return isOpen;
    }
}
