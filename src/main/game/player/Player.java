package main.game.player;

import main.game.tiles.Tile;

import java.util.ArrayList;

public abstract class Player {
    protected String name;
    protected int rating;
    protected ArrayList<Tile> deck = new ArrayList<>();

    //생성자
    public Player(String name, int rating, ArrayList<Tile> deck){
        this.name = name;
        this.rating = rating;
        this.deck = deck;
    }

    //deck에 일반 타일 삽입
    public void insertTile(Tile tile){
        deck.add(tile);
    }    
    
    //deck에 조커 타일 삽입
    public void insertTile(Tile tile, int index){
        deck.add(index, tile);
    };

    //Tile 공개
    public void openTile(Tile tile){
        tile.setOpen(true);
    }

    //deck 사이즈 반환
    public int getDeckSize(){
        return deck.size();
    }

    //deck의 특정 위치의 타일 반환
    public Tile getTile(int index){
        return deck.get(index);
    }

}
