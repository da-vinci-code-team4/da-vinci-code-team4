package com.example.project.game.player;

import com.example.project.game.tile.Tile;

import java.util.ArrayList;
import java.util.Random;

import org.checkerframework.checker.units.qual.A;

public class Computer extends Player {

    Random rand = new Random();

    public Computer(String name, int rank, int score, int winCnt, int lossCnt) {
        super(name, rank, score, winCnt, lossCnt);
    }

    /**
     * 덱에서부터 무작위 4장을 가져옴
     */
    public void getInitTiles() {
        Tile tile;
        
        for(int i=0; i<4; i++){
            this.tileManager.shuffle();
            tile = this.tileManager.getTileFromDeck().get();
            this.myTileDeck.add(tile);
        }

        System.out.println("Computer's tile deck");
        for(Tile t : this.myTileDeck){
            System.out.println(t.getTileColor() + " " + t.getWeight()/10);
        }
    }
    

    @Override
    public Tile getSelectedTile() {
        ArrayList<Tile> opponentDeck = this.tileManager.getOpponentDeck(this);
        int val = rand.nextInt(1, opponentDeck.size()+1);
        return opponentDeck.get(val);
    }

    @Override
    public int inputJokerTilePosition() {
        return 0;
    }

    @Override
    public boolean chooseToKeepTurn() {
        //50프로 확률로 선택
        Random rand = new Random();
        int val = rand.nextInt(0, 2);
        if (val == 0) {
            return true;
        }
        return false;
    }
}
 