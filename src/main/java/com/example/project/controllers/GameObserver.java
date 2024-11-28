// src/main/java/com/example/project/controllers/GameObserver.java
package com.example.project.controllers;

import com.example.project.views.DefeatScreen;
import com.example.project.views.VictoryScreen;

/**
 * GameObserver.java
 *
 * 게임 상태 변경 알림을 받기 위한 인터페이스.
 */
public interface GameObserver {
    // 게임 상태가 변경되었을 때 호출되는 메서드
    void onGameStateChanged(GameState state);

    // 승리 화면을 표시하는 메서드
    void showVictoryScreen(VictoryScreen victoryScreen);

    // 패배 화면을 표시하는 메서드
    void showDefeatScreen(DefeatScreen defeatScreen);
}
