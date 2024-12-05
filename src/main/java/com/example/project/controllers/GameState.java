package com.example.project.controllers;

import com.example.project.config.Tile;
import java.util.List;

/**
 * GameState.java
 *
 * 현재 게임의 상태를 저장합니다.
 */
public class GameState {
    private List<Tile> centralTiles;
    private List<Tile> computerTiles;
    private List<Tile> userTiles;
    private boolean isGameOver;
    private String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

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
     * 컴퓨터 영역에서 인덱스 위치의 타일이 열렸음을 표시합니다.
     *
     * @param index 컴퓨터 영역에서의 타일 위치
     */
    public void setTileOpenedInComputer(int index) {
        if (computerTiles != null && index >= 0 && index < computerTiles.size()) {
            computerTiles.get(index).setOpened(true);
        }
    }

    /**
     * 사용자 영역에서 인덱스 위치의 타일이 열렸음을 표시합니다.
     *
     * @param index 사용자 영역에서의 타일 위치
     */
    public void setTileOpenedInUser(int index) {
        if (userTiles != null && index >= 0 && index < userTiles.size()) {
            userTiles.get(index).setOpened(true);
        }
    }
}
