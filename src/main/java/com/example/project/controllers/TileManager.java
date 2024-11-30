package com.example.project.controllers;

import com.example.project.config.Tile;
import com.example.project.config.TileColor;
import com.example.project.config.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * TileManager.java
 *
 * 게임 내 타일을 관리하며, 타일의 초기화 및 섞기를 담당합니다.
 */
public class TileManager {
    private List<Tile> centralTiles; // 중앙 영역의 타일 목록
    private Random random;

    public TileManager() {
        centralTiles = new ArrayList<>();
        random = new Random();
        initializeTiles();
    }

    /**
     * 타일을 초기화하고 섞습니다.
     */
    public void initializeTiles() {
        centralTiles.clear();

        // 흰색 타일 12개 추가
        for (int i = 0; i < 12; i++) {
            centralTiles.add(new Tile(TileType.NUMBER,TileColor.WHITE, i*10));
        }

        // 검정색 타일 12개 추가
        for (int i = 0; i < 12; i++) {
            centralTiles.add(new Tile(TileType.NUMBER, TileColor.BLACK, i*10));
        }

        //조커타일 생성
        centralTiles.add(new Tile(TileType.JOKER, TileColor.WHITE,1000));
        centralTiles.add(new Tile(TileType.JOKER, TileColor.BLACK,1000));

        // 타일 섞기
        Collections.shuffle(centralTiles);
    }

    /**
     * 게임을 초기화합니다.
     */
    public void initGame() {
        initializeTiles();
    }

    /**
     * 중앙 영역의 특정 인덱스에서 타일을 가져옵니다.
     *
     * @param index 중앙 영역 내 타일의 위치 (0-23)
     * @return 가져온 타일 또는 null
     */
    public Tile drawTile(int index) {
        if (index >= 0 && index < centralTiles.size()) {
            Tile tile = centralTiles.get(index);
            return tile;
        }
        return null;
    }

    /**
     * 중앙에서 열리지 않은 타일 중 하나를 무작위로 가져옵니다.
     *
     * @return 가져온 타일 또는 null
     */
    public Tile drawRandomCentralTile() {
        List<Tile> availableTiles = new ArrayList<>();
        for (Tile tile : centralTiles) {
            if (!tile.isOpened()) {
                availableTiles.add(tile);
            }
        }

        if (availableTiles.isEmpty()) {
            return null;
        }

        Tile selectedTile = availableTiles.get(random.nextInt(availableTiles.size()));
        selectedTile.setOpened(true);
        return selectedTile;
    }

    /**
     * 열리지 않은 타일의 인덱스를 무작위로 가져옵니다.
     *
     * @return 무작위 인덱스 또는 -1 (더 이상 타일이 없을 경우)
     */
    public int getRandomAvailableTileIndex() {
        List<Integer> availableIndices = new ArrayList<>();
        for (int i = 0; i < centralTiles.size(); i++) {
            if (!centralTiles.get(i).isOpened()) {
                availableIndices.add(i);
            }
        }

        if (availableIndices.isEmpty()) {
            return -1;
        }

        return availableIndices.get(random.nextInt(availableIndices.size()));
    }

    /**
     * 덱에 남아있는 타일의 수를 가져옵니다.
     *
     * @return 남은 타일의 수
     */
    public int getDeckSize() {
        int count = 0;
        for (Tile tile : centralTiles) {
            if (!tile.isOpened()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 중앙 영역의 타일 목록을 가져옵니다.
     *
     * @return 중앙 타일 목록
     */
    public List<Tile> getCentralTiles() {
        return centralTiles;
    }

    /**
     * 중앙에 남아있는 타일이 있는지 확인합니다.
     *
     * @return 타일이 남아있으면 true, 아니면 false
     */
    public boolean hasTiles() {
        for (Tile tile : centralTiles) {
            if (!tile.isOpened()) {
                return true;
            }
        }
        return false;
    }

    public boolean allTilesGuessedCorrectly() {
        for (Tile tile : centralTiles) {
            if (!tile.isGuessedCorrectly()) {
                return false;
            }
        }
        return true;
    }

}