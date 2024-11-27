package com.example.project.controllers;

/**
 * GamePhase.java
 *
 * Định nghĩa các giai đoạn của trò chơi.
 */
public enum GamePhase {
    INITIAL_SELECTION,           // Chọn 4 thẻ bài ban đầu từ trung tâm
    COMPUTER_INITIAL_SELECTION,  // Máy tính chọn 4 thẻ bài
    PLAYER_DRAW_PHASE,           // Người chơi rút thẻ bài từ trung tâm
    PLAYER_GUESS_PHASE,          // Người chơi đoán thẻ bài của máy tính
    COMPUTER_TURN,               // Lượt của máy tính
    GAME_OVER                    // Trò chơi kết thúc
}
