package com.example.project.game.player;

import java.util.Random;

public class Computer extends Player {
    public Computer(String name, int rank, int score, int winCnt, int lossCnt) {
        super(name, rank, score, winCnt, lossCnt);
    }

    @Override
    public void guessTile() {
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
