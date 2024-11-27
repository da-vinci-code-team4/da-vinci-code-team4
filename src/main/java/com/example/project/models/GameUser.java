package com.example.project.models;

import com.example.project.config.Tile;
import javax.swing.JOptionPane;

/**
 * GameUser.java
 *
 * Đại diện cho người chơi (bạn).
 */
public class GameUser extends Player {

    public GameUser(String name) {
        super(name);
    }

    @Override
    public int guessNumber(Tile tile) {
        // Yêu cầu người chơi nhập số
        String input = JOptionPane.showInputDialog(null, "Nhập số bạn đoán:");
        int guessedNumber = -1;
        try {
            guessedNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập một số hợp lệ!");
            return guessNumber(tile); // Thử lại
        }
        return guessedNumber;
    }

    @Override
    public Tile selectTile() {
        // Xử lý trong GUI
        return null;
    }
}