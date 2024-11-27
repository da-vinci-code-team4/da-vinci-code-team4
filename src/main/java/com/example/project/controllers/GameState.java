package com.example.project.controllers;

import com.example.project.config.Tile;
import java.util.List;

/**
 * GameState.java
 *
 * Lưu trữ trạng thái hiện tại của trò chơi.
 */
public class GameState {
    private List<Tile> centralTiles;
    private List<Tile> computerTiles;
    private List<Tile> userTiles;
    private boolean isGameOver;

    public List<Tile> getCentralTiles() {
        return centralTiles;
    }

    public void setCentralTiles(List<Tile> centralTiles) {
        this.centralTiles = centralTiles;
    }

    public List<Tile> getComputerTiles() {
        return computerTiles;
    }

    public void setComputerTiles(List<Tile> computerTiles) {
        this.computerTiles = computerTiles;
    }

    public List<Tile> getUserTiles() {
        return userTiles;
    }

    public void setUserTiles(List<Tile> userTiles) {
        this.userTiles = userTiles;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    /**
     * Đánh dấu thẻ bài ở khu vực máy tính tại vị trí index là đã được mở.
     *
     * @param index Vị trí của thẻ bài trong khu vực máy tính
     */
    public void setTileOpenedInComputer(int index) {
        if (computerTiles != null && index >= 0 && index < computerTiles.size()) {
            computerTiles.get(index).setOpened(true);
        }
    }

    /**
     * Đánh dấu thẻ bài ở khu vực người chơi tại vị trí index là đã được mở.
     *
     * @param index Vị trí của thẻ bài trong khu vực người chơi
     */
    public void setTileOpenedInUser(int index) {
        if (userTiles != null && index >= 0 && index < userTiles.size()) {
            userTiles.get(index).setOpened(true);
        }
    }
}