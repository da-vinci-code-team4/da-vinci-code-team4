// src/main/java/com/example/project/controllers/GameObserver.java
package com.example.project.controllers;

/**
 * GameObserver.java
 *
 * Giao diện để nhận thông báo thay đổi trạng thái trò chơi.
 */
public interface GameObserver {
    void onGameStateChanged(GameState state);
}
