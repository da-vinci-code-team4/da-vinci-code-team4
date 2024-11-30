package com.example.project.models;

import com.example.project.config.Tile;
import java.util.Random;

/**
 * Computer.java
 *
 * 컴퓨터 플레이어를 나타냅니다.
 */
public class Computer extends Player {

    public Computer(String name) {
        super(name);
    }

    @Override
    public int guessNumber(Tile tile) {
        // 간단한 AI: 0부터 11 사이의 숫자를 무작위로 추측
        return random.nextInt(12)*10;
    }

    @Override
    public Tile selectTile() {
        // 상대방의 열리지 않은 타일 중 하나를 선택
        return getRandomUnopenedTile();
    }
}
