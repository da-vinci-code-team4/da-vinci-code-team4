// src/main/java/com/example/project/controllers/GameManager.java
package com.example.project.controllers;

import com.example.project.models.Computer;
import com.example.project.models.GameUser;
import com.example.project.config.Tile;
import javax.swing.JOptionPane;
import java.util.Optional;

/**
 * GameManager.java
 *
 * 게임의 상태와 흐름을 관리합니다.
 */
public class GameManager {
    private TileManager tileManager;
    private GameUser user;
    private Computer computer;
    private GameState gameState;
    private GameObserver observer;

    public GameManager(GameObserver observer) {
        this.observer = observer;
        tileManager = new TileManager();
        tileManager.initializeTiles(); // 24개의 타일 초기화 (12 검정, 12 흰색) 및 섞기
        gameState = new GameState();
        gameState.setCentralTiles(tileManager.getCentralTiles());
        user = new GameUser("플레이어");
        computer = new Computer("컴퓨터");
    }

    /**
     * 게임을 시작합니다.
     */
    public void startGame() {
        // 게임 상태 업데이트
        observer.onGameStateChanged(gameState);
    }

    /**
     * 플레이어가 중앙 영역에서 인덱스 위치의 타일을 가져갑니다.
     *
     * @param index 중앙 영역에서의 타일 위치 (0-23)
     */
    public void userDrawTile(int index) {
        Tile tile = tileManager.drawTile(index);
        if (tile != null && !tile.isOpened()) {
            tile.setOpened(true); // 타일이 열렸음을 표시
            user.addTile(tile);
            // 필요 시 알림 추가
            JOptionPane.showMessageDialog(null, "타일을 성공적으로 선택했습니다!");
        } else {
            JOptionPane.showMessageDialog(null, "타일이 이미 선택되었습니다!");
        }
        observer.onGameStateChanged(gameState);
    }

    /**
     * 컴퓨터가 초기 단계에서 중앙 영역에서 4개의 타일을 가져갑니다.
     */
    public void computerInitialDrawTiles() {
        int tilesToDraw = 4;
        for (int i = 0; i < tilesToDraw; i++) {
            int index = tileManager.getRandomAvailableTileIndex();
            if (index != -1) {
                Tile computerTile = tileManager.drawTile(index);
                if (computerTile != null && !computerTile.isOpened()) {
                    computerTile.setOpened(true);
                    computer.addTile(computerTile);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "컴퓨터가 초기 단계에서 4개의 타일을 선택했습니다.");
        observer.onGameStateChanged(gameState);
    }

    /**
     * 컴퓨터가 추측을 진행합니다.
     */
    public void computerTurn() {
        // 컴퓨터가 플레이어의 타일 중 하나를 선택하여 추측
        Tile userTile = user.getRandomUnopenedTile();
        if (userTile != null) {
            int guessedNumber = computer.guessNumber(userTile);
            if (guessedNumber == userTile.getNumber()) {
                JOptionPane.showMessageDialog(null, "컴퓨터가 타일의 번호를 정확히 맞췄습니다!");
                userTile.setOpened(true);
                computer.increaseScore();
            } else {
                JOptionPane.showMessageDialog(null, "컴퓨터가 타일의 번호를 틀렸습니다!");
                // 컴퓨터가 틀렸을 때의 추가 로직, 예: 턴 변경
            }
        }
        observer.onGameStateChanged(gameState);
    }

    /**
     * 게임 종료 조건을 확인합니다.
     */
    public void checkGameOver() {
        if (user.getScore() >= 8) {
            gameState.setGameOver(true);
            observer.onGameStateChanged(gameState);
            JOptionPane.showMessageDialog(null, "축하합니다! 당신이 승리했습니다!");
        } else if (computer.getScore() >= 8) {
            gameState.setGameOver(true);
            observer.onGameStateChanged(gameState);
            JOptionPane.showMessageDialog(null, "컴퓨터가 승리했습니다!");
        }
    }

    public GameUser getUser() {
        return user;
    }

    public Computer getComputer() {
        return computer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
}
