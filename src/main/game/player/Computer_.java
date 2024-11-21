package main.game.player;

import main.game.tiles.Tile;

import java.util.ArrayList;
import java.util.Random;

public class Computer_ extends Player{
    
    Random rand = new Random();
    
    //자신의 패 + 밝혀진 패
    private ArrayList<Tile> knownTiles = new ArrayList<>();

    public Computer_(String name, int rating, ArrayList<Tile> deck){
        super(name, rating, deck);
    }

    //상대의 패를 맞춤
    public Boolean guessTile(Player oPlayer, Tile tile, int num){
        for(int i = 0; i < oPlayer.getDeckSize(); i++){
            if(oPlayer.getTile(i).getTileNumber() == num){
                oPlayer.openTile(oPlayer.getTile(i));
                return true;
            }
        }
        return false;
    }

    //상대의 패를 선택
    public Tile selectTile(Player oPlayer){

        Tile tile = oPlayer.getTile(rand.nextInt(oPlayer.getDeckSize()));
        return tile;

    }

    //컴퓨터가 조커 타일을 넣는 위치 선정
    public int inputJokerTilePosition() {
        
        //deck의 랜덤 위치에 조커 타일 삽입
        return rand.nextInt(deck.size());       
    }

    public boolean chooseToKeepTurn() {
        //50프로 확률로 선택
        int val = rand.nextInt(0,2);
        if(val == 0){
            return true;
        }
        return false;
    }
}
