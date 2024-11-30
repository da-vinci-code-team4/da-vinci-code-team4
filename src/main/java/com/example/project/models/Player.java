package com.example.project.models;

import com.example.project.config.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Player.java
 *
 * 게임 내 플레이어를 추상화한 클래스입니다.
 */
public abstract class Player {
    protected String name;
    protected List<Tile> tiles; // 플레이어의 타일 목록
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
     * 무작위로 열리지 않은 타일을 가져옵니다.
     *
     * @return 열리지 않은 타일 또는 null
     */
    public Tile getRandomUnopenedTile() {
        List<Tile> unopenedTiles = new ArrayList<>();
        for (Tile tile : tiles) {
//             && !tile.isGuessedCorrectly()
            if (tile.isOpened()) {
                unopenedTiles.add(tile);
            }
        }
        if (!unopenedTiles.isEmpty()) {
            return unopenedTiles.get(random.nextInt(unopenedTiles.size()));
        }
        return null;
    }

    public void sorting() {
        tiles.sort((tile1, tile2) -> Integer.compare(tile1.getNumber(), tile2.getNumber()));
    }
    /**
     * 플레이어가 숫자를 추측합니다.
     *
     * @param tile 추측할 타일
     * @return 플레이어가 추측한 숫자
     */
    public abstract int guessNumber(Tile tile);

    /**
     * 플레이어가 타일을 선택합니다.
     *
     * @return 선택된 타일
     */
    public abstract Tile selectTile();
}