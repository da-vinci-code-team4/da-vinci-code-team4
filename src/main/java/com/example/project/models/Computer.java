package com.example.project.models;

import com.example.project.config.Tile;

/**
 * Computer.java
 *
 * Đại diện cho máy tính.
 */
public class Computer extends Player {

    public Computer(String name) {
        super(name);
    }

    @Override
    public int guessNumber(Tile tile) {
        // AI đơn giản: Đoán ngẫu nhiên một số từ 0 đến 11
        return random.nextInt(12);
    }

    @Override
    public Tile selectTile() {
        // Chọn một thẻ bài chưa được mở từ đối thủ
        return getRandomUnopenedTile();
    }
}