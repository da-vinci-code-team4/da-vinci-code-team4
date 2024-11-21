package main.game.tiles;

public class Tile implements Comparable<Tile>{
    
    public static final int MIN_TILE_NUMBER = 0;
    public static final int MAX_TILE_NUMBER = 11;
    public static final int NUMBER_OF_INIT_TILE = 4;
    
    private boolean isOpen;
    private int weight; //정렬 때 사용할 가중치(가중치 기준 오름차순으로 정렬됨)
    private final TileType tileType;
    private final TileColor tileColor;



    public Tile(TileType tileType, TileColor tileColor){
        this.isOpen = false;
        this.tileType = tileType;
        this.tileColor = tileColor;
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

    //타일 타입 비교
    public boolean compareTileType(TileType tileType) {
        return this.tileType.equals(tileType);
    }

    //타일 공개
    public void setOpen(boolean bool){
        this.isOpen = bool;
    }

    // return isOpen
    public boolean getOpen(){
        return this.isOpen;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
