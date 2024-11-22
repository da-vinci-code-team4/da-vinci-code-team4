package org.example;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {
    private List<Tile> deck; // Bộ bài chính
    private List<Tile> playerHand; // Bài của người chơi
    private List<Tile> opponentHand; // Bài của đối thủ
    private List<Tile> sharedHand; // Bài chung trên bàn

    public GameManager() {
        deck = new ArrayList<>();
        playerHand = new ArrayList<>();
        opponentHand = new ArrayList<>();
        sharedHand = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
        dealInitialTiles();
    }

    // Khởi tạo bộ bài với 24 tile
    private void initializeDeck() {
        // Thêm tile số từ 0 đến 11 với màu đen và trắng
        for (int i = 0; i < 12; i++) {
            deck.add(new Tile(i, "-", Color.BLACK));
            deck.add(new Tile(i, "-", Color.WHITE));
        }
        // Thêm các tile Joker nếu cần (giả sử có 2 Joker)
        deck.add(new Tile(-1, "J", Color.GRAY));
        deck.add(new Tile(-1, "J", Color.GRAY));
    }

    // Trộn bộ bài
    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    // Phân chia 4 tile cho mỗi người chơi
    private void dealInitialTiles() {
        for (int i = 0; i < 4; i++) {
            playerHand.add(deck.remove(0));
            opponentHand.add(deck.remove(0));
        }
    }

    // Lấy bộ bài của người chơi
    public List<Tile> getPlayerHand() {
        return playerHand;
    }

    // Lấy bộ bài của đối thủ
    public List<Tile> getOpponentHand() {
        return opponentHand;
    }

    // Lấy bộ bài chung
    public List<Tile> getSharedHand() {
        return sharedHand;
    }

    // Lấy tile từ bộ bài chung (nếu cần)
    public Tile drawSharedTile() {
        if (!deck.isEmpty()) {
            Tile tile = deck.remove(0);
            sharedHand.add(tile);
            return tile;
        }
        return null;
    }

    // Kiểm tra điều kiện thắng
    public boolean checkWinCondition() {
        // Người chơi thắng nếu đã mở hết tile của đối thủ
        boolean playerWins = opponentHand.stream().allMatch(Tile::isRevealed);
        // Đối thủ thắng nếu đã mở hết tile của người chơi
        boolean opponentWins = playerHand.stream().allMatch(Tile::isRevealed);
        return playerWins || opponentWins;
    }

    // Xác định người thắng
    public String getWinner() {
        boolean playerWins = opponentHand.stream().allMatch(Tile::isRevealed);
        boolean opponentWins = playerHand.stream().allMatch(Tile::isRevealed);
        if (playerWins && opponentWins) {
            return "Draw";
        } else if (playerWins) {
            return "Player";
        } else if (opponentWins) {
            return "Opponent";
        }
        return "None";
    }
}
