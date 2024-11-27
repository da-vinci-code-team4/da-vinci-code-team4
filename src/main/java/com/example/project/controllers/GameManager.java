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
 * Quản lý trạng thái và luồng của trò chơi.
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
        tileManager.initializeTiles(); // Khởi tạo 24 thẻ bài (12 đen, 12 trắng) và xáo trộn
        gameState = new GameState();
        gameState.setCentralTiles(tileManager.getCentralTiles());
        user = new GameUser("Người chơi");
        computer = new Computer("Máy tính");
    }

    /**
     * Bắt đầu trò chơi.
     */
    public void startGame() {
        // Cập nhật trạng thái trò chơi
        observer.onGameStateChanged(gameState);
    }

    /**
     * Người chơi lấy một thẻ bài từ khu vực trung tâm tại vị trí index.
     *
     * @param index Vị trí thẻ bài trong khu vực trung tâm (0-23)
     */
    public void userDrawTile(int index) {
        Tile tile = tileManager.drawTile(index);
        if (tile != null && !tile.isOpened()) {
            tile.setOpened(true); // Đánh dấu thẻ bài đã mở
            user.addTile(tile);
            // Thêm thông báo nếu cần
            JOptionPane.showMessageDialog(null, "Bạn đã chọn thẻ bài thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Thẻ bài đã được chọn trước đó!");
        }
        observer.onGameStateChanged(gameState);
    }

    /**
     * Máy tính lấy 4 thẻ bài ban đầu từ khu vực trung tâm.
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
        JOptionPane.showMessageDialog(null, "Máy tính đã chọn 4 thẻ bài ban đầu.");
        observer.onGameStateChanged(gameState);
    }

    /**
     * Máy tính tiến hành lượt đoán.
     */
    public void computerTurn() {
        // Máy tính chọn một thẻ bài của người chơi để đoán
        Tile userTile = user.getRandomUnopenedTile();
        if (userTile != null) {
            int guessedNumber = computer.guessNumber(userTile);
            if (guessedNumber == userTile.getNumber()) {
                JOptionPane.showMessageDialog(null, "Máy tính đã đoán đúng số trên thẻ bài!");
                userTile.setOpened(true);
                computer.increaseScore();
            } else {
                JOptionPane.showMessageDialog(null, "Máy tính đã đoán sai số trên thẻ bài!");
                // Thêm logic xử lý khi máy tính đoán sai, ví dụ: chuyển lượt
            }
        }
        observer.onGameStateChanged(gameState);
    }

    /**
     * Kiểm tra điều kiện kết thúc trò chơi.
     */
    public void checkGameOver() {
        if (user.getScore() >= 8) {
            gameState.setGameOver(true);
            observer.onGameStateChanged(gameState);
        } else if (computer.getScore() >= 8) {
            gameState.setGameOver(true);
            observer.onGameStateChanged(gameState);
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
