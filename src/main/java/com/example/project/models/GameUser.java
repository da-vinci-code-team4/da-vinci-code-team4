package com.example.project.models;

import com.example.project.config.Tile;
import javax.swing.JOptionPane;

/**
 * GameUser.java
 *
 * 사용자(플레이어)를 나타냅니다.
 */
public class GameUser extends Player {

    public GameUser(String name) {
        super(name);
    }

    @Override
    public int guessNumber(Tile tile) {
        // 사용자가 숫자를 입력하도록 요청
        String input = JOptionPane.showInputDialog(null, "추측할 숫자를 입력하세요:");
        int guessedNumber = -1;
        try {
            guessedNumber = Integer.parseInt(input)*10;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "유효한 숫자를 입력해주세요!");
            return guessNumber(tile); // 다시 시도
        }
        return guessedNumber;
    }

    @Override
    public Tile selectTile() {
        // GUI에서 처리
        return null;
    }
}