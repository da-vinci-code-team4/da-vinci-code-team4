package com.example.project.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.example.project.config.Tile;
import com.example.project.controllers.Controller;
import com.example.project.controllers.TileManager;
/**
 * Computer.java
 *
 * 컴퓨터 플레이어를 나타냅니다.
 */
public class Computer extends Player {

    public Computer(String name) {
        super(name);
    }

    public int countGuessedCorrectly() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.isGuessedCorrectly()) {
                count++;
            }
        }
        return count;
    }


    @Override
    public int guessNumber(Tile tile) {
        // 간단한 AI: 0부터 11 사이의 숫자를 무작위로 추측
        List<Integer> possibleNumbers = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            //상대 플레이어의 타일이 열려있지 않은 경우에만 추측
            if (!tile.isOpened()) {
                possibleNumbers.add(i * 10);
            }
        }
        return possibleNumbers.get(new Random().nextInt(possibleNumbers.size()));
    }

    @Override
    public Tile selectTile() {
        // 상대방의 열리지 않은 타일 중 하나를 선택
        return getRandomUnopenedTile();
    }
}
