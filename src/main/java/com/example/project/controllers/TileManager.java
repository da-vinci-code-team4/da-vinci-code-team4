package com.example.project.controllers;

import com.example.project.config.Tile;
import com.example.project.config.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * TileManager.java
 *
 * Quản lý các thẻ bài trong trò chơi, bao gồm việc khởi tạo và xáo trộn thẻ bài.
 */
public class TileManager {
    private List<Tile> centralTiles; // Các thẻ bài ở khu vực trung tâm
    private Random random;

    public TileManager() {
        centralTiles = new ArrayList<>();
        random = new Random();
        initializeTiles();
    }

    /**
     * Khởi tạo và xáo trộn các thẻ bài.
     */
    public void initializeTiles() {
        centralTiles.clear();

        // Thêm 12 thẻ bài trắng
        for (int i = 0; i < 12; i++) {
            centralTiles.add(new Tile(TileType.WHITE, i));
        }

        // Thêm 12 thẻ bài đen
        for (int i = 0; i < 12; i++) {
            centralTiles.add(new Tile(TileType.BLACK, i));
        }

        // Xáo trộn thẻ bài
        Collections.shuffle(centralTiles);
    }

    /**
     * Khởi tạo lại trò chơi.
     */
    public void initGame() {
        initializeTiles();
    }

    /**
     * Lấy một thẻ bài từ khu vực trung tâm tại vị trí index.
     *
     * @param index Vị trí của thẻ bài trong danh sách (0-23)
     * @return Thẻ bài được lấy
     */
    public Tile drawTile(int index) {
        if (index >= 0 && index < centralTiles.size()) {
            Tile tile = centralTiles.get(index);
            return tile;
        }
        return null;
    }

    /**
     * Lấy ngẫu nhiên thẻ bài chưa được chọn từ trung tâm.
     *
     * @return Thẻ bài chưa được chọn hoặc null nếu không còn thẻ bài nào
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
     * Lấy ngẫu nhiên chỉ số của một thẻ bài chưa được chọn.
     *
     * @return Chỉ số ngẫu nhiên hoặc -1 nếu không còn thẻ bài nào
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
     * Lấy số lượng thẻ bài còn lại trong bộ bài.
     *
     * @return Số lượng thẻ bài còn lại
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
     * Lấy danh sách các thẻ bài ở trung tâm.
     *
     * @return Danh sách các thẻ bài
     */
    public List<Tile> getCentralTiles() {
        return centralTiles;
    }

    /**
     * Kiểm tra xem còn thẻ bài ở trung tâm không.
     *
     * @return True nếu còn, False nếu hết
     */
    public boolean hasTiles() {
        for (Tile tile : centralTiles) {
            if (!tile.isOpened()) {
                return true;
            }
        }
        return false;
    }
}