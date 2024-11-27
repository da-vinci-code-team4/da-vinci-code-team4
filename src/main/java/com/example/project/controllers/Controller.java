package com.example.project.controllers;

import com.example.project.models.Computer;
import com.example.project.models.GameUser;
import com.example.project.config.Tile;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller.java
 *
 * Quản lý luồng trò chơi, xử lý logic giữa người chơi và máy tính.
 */
public class Controller {
    private TileManager tileManager;
    private GameUser user;
    private Computer computer;
    private GameState gameState;
    private GameObserver observer;

    // Trạng thái hiện tại của trò chơi
    private GamePhase currentPhase;

    public Controller(GameObserver observer) {
        this.observer = observer;
        tileManager = new TileManager();
        tileManager.initializeTiles(); // Khởi tạo 24 thẻ bài (12 đen, 12 trắng) và xáo trộn
        gameState = new GameState();
        gameState.setCentralTiles(tileManager.getCentralTiles());
        user = new GameUser("Người chơi");
        computer = new Computer("Máy tính");
        gameState.setUserTiles(user.getTiles());
        gameState.setComputerTiles(computer.getTiles());
        currentPhase = GamePhase.INITIAL_SELECTION;
    }

    /**
     * Bắt đầu trò chơi.
     */
    public void startGame() {
        // Cập nhật trạng thái trò chơi
        observer.onGameStateChanged(gameState);
    }

    /**
     * Xử lý việc người chơi chọn thẻ bài từ trung tâm trong giai đoạn chọn ban đầu.
     *
     * @param index Vị trí thẻ bài trong khu vực trung tâm (0-23)
     */
    public void playerSelectInitialTile(int index) {
        if (currentPhase != GamePhase.INITIAL_SELECTION) {
            JOptionPane.showMessageDialog(null, "Không phải lượt của bạn để chọn thẻ bài.");
            return;
        }

        Tile tile = tileManager.drawTile(index);
        if (tile != null && !tile.isOpened()) {
            if (!tile.isSelected()) {
                tile.setSelected(true); // Đánh dấu thẻ bài đã được chọn
                JOptionPane.showMessageDialog(null, "Đã chọn thẻ bài.");
            } else {
                tile.setSelected(false); // Bỏ chọn thẻ bài
                JOptionPane.showMessageDialog(null, "Đã bỏ chọn thẻ bài.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Thẻ bài đã được mở hoặc không hợp lệ!");
        }
        observer.onGameStateChanged(gameState);
    }

    /**
     * Xác nhận các thẻ bài được chọn trong giai đoạn chọn ban đầu.
     */
    public void confirmInitialSelection() {
        if (currentPhase != GamePhase.INITIAL_SELECTION) {
            JOptionPane.showMessageDialog(null, "Không phải lượt của bạn để xác nhận.");
            return;
        }

        List<Tile> selectedTiles = new ArrayList<>();
        for (Tile tile : tileManager.getCentralTiles()) {
            if (tile.isSelected()) {
                selectedTiles.add(tile);
            }
        }

        if (selectedTiles.size() != 4) {
            JOptionPane.showMessageDialog(null, "Bạn phải chọn đúng 4 thẻ bài.");
            return;
        }

        for (Tile tile : selectedTiles) {
            tile.setOpened(true);
            tile.setSelected(false);
            user.addTile(tile);
        }
        gameState.setUserTiles(user.getTiles());

        // Loại bỏ các thẻ bài đã chọn khỏi khu vực trung tâm
        observer.onGameStateChanged(gameState);

        // Máy tính chọn 4 thẻ bài
        currentPhase = GamePhase.COMPUTER_INITIAL_SELECTION;
        computerInitialDrawTiles();

        // Chuyển sang giai đoạn người chơi rút thẻ
        currentPhase = GamePhase.PLAYER_DRAW_PHASE;
        observer.onGameStateChanged(gameState); // Thêm dòng này để cập nhật giao diện
        JOptionPane.showMessageDialog(null, "Giai đoạn của bạn: Vui lòng chọn một thẻ bài để rút (1).");
    }

    /**
     * Máy tính rút 4 thẻ bài trong giai đoạn chọn ban đầu.
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
        gameState.setComputerTiles(computer.getTiles());
        JOptionPane.showMessageDialog(null, "Máy tính đã chọn 4 thẻ bài.");
        observer.onGameStateChanged(gameState);
    }

    /**
     * Người chơi rút một thẻ bài từ trung tâm trong giai đoạn rút bài.
     *
     * @param index Vị trí thẻ bài trong khu vực trung tâm (0-23)
     */
    public void playerDrawTile(int index) {
        if (currentPhase != GamePhase.PLAYER_DRAW_PHASE) {
            JOptionPane.showMessageDialog(null, "Không phải lượt của bạn để rút thẻ bài.");
            return;
        }

        Tile tile = tileManager.drawTile(index);
        if (tile != null && !tile.isOpened()) {
            tile.setOpened(true);
            user.addTile(tile);
            gameState.setUserTiles(user.getTiles());
            JOptionPane.showMessageDialog(null, "Đã thêm thẻ bài vào tay bạn.");
            observer.onGameStateChanged(gameState);

            // Chuyển sang giai đoạn người chơi đoán
            currentPhase = GamePhase.PLAYER_GUESS_PHASE;
            observer.onGameStateChanged(gameState); // Thêm dòng này để cập nhật giao diện
            JOptionPane.showMessageDialog(null, "Giai đoạn của bạn: Hãy chọn thẻ bài của đối phương để đoán.");
        } else {
            JOptionPane.showMessageDialog(null, "Lựa chọn thẻ bài không hợp lệ.");
        }
    }

    /**
     * Người chơi đoán thẻ bài của máy tính.
     *
     * @param index Vị trí thẻ bài trong khu vực máy tính (0-11)
     */
    public void playerGuessComputerTile(int index) {
        if (currentPhase != GamePhase.PLAYER_GUESS_PHASE) {
            JOptionPane.showMessageDialog(null, "Không phải lượt của bạn để đoán.");
            return;
        }

        if (index < 0 || index >= computer.getTiles().size()) {
            JOptionPane.showMessageDialog(null, "Lựa chọn thẻ bài không hợp lệ!");
            return;
        }

        Tile computerTile = computer.getTiles().get(index);
        if (computerTile.isGuessedCorrectly()) {
            JOptionPane.showMessageDialog(null, "Thẻ bài này đã được đoán đúng!");
            return;
        }

        // Người chơi nhập số đoán
        int guessedNumber = user.guessNumber(computerTile);
        if (guessedNumber == computerTile.getNumber()) {
            JOptionPane.showMessageDialog(null, "Bạn đã đoán đúng!");
            computerTile.setGuessedCorrectly(true); // Đánh dấu đoán đúng
            user.increaseScore();
            gameState.setTileOpenedInComputer(index); // Đánh dấu thẻ bài đã được mở trong GameState
            observer.onGameStateChanged(gameState);

            // Kiểm tra điều kiện chiến thắng
            checkGameOver();

            // Chuyển sang giai đoạn rút bài
            currentPhase = GamePhase.PLAYER_DRAW_PHASE;
            observer.onGameStateChanged(gameState); // Thêm dòng này để cập nhật giao diện
            JOptionPane.showMessageDialog(null, "Giai đoạn của bạn: Vui lòng chọn một thẻ bài để rút (1).");
        } else {
            JOptionPane.showMessageDialog(null, "Bạn đã đoán sai!");
            // Chuyển sang lượt của máy tính
            currentPhase = GamePhase.COMPUTER_TURN;
            observer.onGameStateChanged(gameState); // Thêm dòng này để cập nhật giao diện
            computerTurn();
            checkGameOver();
        }
    }

    /**
     * Lượt của máy tính để đoán thẻ bài của người chơi.
     */
    public void computerTurn() {
        // Máy tính chọn một thẻ bài để đoán
        Tile userTile = user.getRandomUnopenedTile();
        if (userTile != null) {
            int guessedNumber = computer.guessNumber(userTile);
            JOptionPane.showMessageDialog(null, "Máy tính đoán: " + guessedNumber);
            if (guessedNumber == userTile.getNumber()) {
                JOptionPane.showMessageDialog(null, "Máy tính đã đoán đúng!");
                userTile.setOpened(true);
                userTile.setGuessedCorrectly(true); // Đánh dấu đoán đúng
                computer.increaseScore();
                gameState.setTileOpenedInUser(user.getTiles().indexOf(userTile)); // Đánh dấu thẻ bài đã được mở trong GameState
                observer.onGameStateChanged(gameState);

                // Kiểm tra điều kiện chiến thắng
                checkGameOver();

                // Máy tính rút một thẻ bài từ trung tâm
                computerDrawTileFromCenter();
                checkGameOver();

                // Chuyển sang giai đoạn rút bài
                currentPhase = GamePhase.PLAYER_DRAW_PHASE;
                observer.onGameStateChanged(gameState); // Thêm dòng này để cập nhật giao diện
                JOptionPane.showMessageDialog(null, "Giai đoạn của bạn: Vui lòng chọn một thẻ bài để rút (1).");
            } else {
                JOptionPane.showMessageDialog(null, "Máy tính đã đoán sai!");
                // Máy tính rút một thẻ bài từ trung tâm
                computerDrawTileFromCenter();
                checkGameOver();

                // Chuyển sang giai đoạn rút bài
                currentPhase = GamePhase.PLAYER_DRAW_PHASE;
                observer.onGameStateChanged(gameState); // Thêm dòng này để cập nhật giao diện
                JOptionPane.showMessageDialog(null, "Giai đoạn của bạn: Vui lòng chọn một thẻ bài để rút (1).");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Máy tính không còn thẻ bài để đoán.");
            // Chuyển sang giai đoạn rút bài
            currentPhase = GamePhase.PLAYER_DRAW_PHASE;
            observer.onGameStateChanged(gameState); // Thêm dòng này để cập nhật giao diện
            JOptionPane.showMessageDialog(null, "Giai đoạn của bạn: Vui lòng chọn một thẻ bài để rút (1).");
        }
    }

    /**
     * Máy tính rút một thẻ bài từ trung tâm.
     */
    private void computerDrawTileFromCenter() {
        Tile tile = tileManager.drawRandomCentralTile();
        if (tile != null) {
            computer.addTile(tile);
            gameState.getComputerTiles().add(tile); // Cập nhật GameState
            JOptionPane.showMessageDialog(null, "Máy tính đã rút một thẻ bài từ trung tâm.");
        } else {
            JOptionPane.showMessageDialog(null, "Không còn thẻ bài nào trong trung tâm để máy tính rút!");
        }
        observer.onGameStateChanged(gameState);
    }

    /**
     * Kiểm tra điều kiện kết thúc trò chơi.
     */
    public void checkGameOver() {
        if (user.getScore() >= 12 || computer.getTiles().size() == 0) {
            gameState.setGameOver(true);
            observer.onGameStateChanged(gameState);
            JOptionPane.showMessageDialog(null, "Chúc mừng! Bạn đã thắng!");
        } else if (computer.getScore() >= 12 || user.getTiles().size() == 0) {
            gameState.setGameOver(true);
            observer.onGameStateChanged(gameState);
            JOptionPane.showMessageDialog(null, "Máy tính đã thắng! Bạn thua!");
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

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(GamePhase phase) {
        this.currentPhase = phase;
    }
}
