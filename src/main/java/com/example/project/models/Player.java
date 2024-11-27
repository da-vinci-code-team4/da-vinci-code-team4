package com.example.project.models;

import com.example.project.config.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Player.java
 *
 * Lớp trừu tượng đại diện cho người chơi trong trò chơi.
 */
public abstract class Player {
    protected String name;
    protected List<Tile> tiles; // Thẻ bài của người chơi
    protected int score;
    protected Random random;

    public Player(String name) {
        this.name = name;
        this.tiles = new ArrayList<>();
        this.score = 0;
        this.random = new Random();
    }

    public String getName() {
        return name;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void removeTile(Tile tile) {
        tiles.remove(tile);
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score++;
    }

    public boolean hasAllTilesOpened() {
        for (Tile tile : tiles) {
            if (!tile.isOpened()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lấy một thẻ bài chưa được mở ngẫu nhiên.
     *
     * @return Thẻ bài chưa được mở hoặc null nếu tất cả đã được mở
     */
    public Tile getRandomUnopenedTile() {
        List<Tile> unopenedTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            if (!tile.isOpened() && !tile.isGuessedCorrectly()) {
                unopenedTiles.add(tile);
            }
        }
        if (!unopenedTiles.isEmpty()) {
            return unopenedTiles.get(random.nextInt(unopenedTiles.size()));
        }
        return null;
    }

    /**
     * Người chơi thực hiện đoán.
     *
     * @param tile Thẻ bài mà người chơi sẽ đoán số
     * @return Số người chơi đoán
     */
    public abstract int guessNumber(Tile tile);

    /**
     * Người chơi chọn một thẻ bài để đoán.
     *
     * @return Thẻ bài được chọn
     */
    public abstract Tile selectTile();
}