package com.example.project.controllers;

import com.example.project.views.DefeatScreen;
import com.example.project.views.VictoryScreen;
import com.example.project.controllers.GameState;

public interface GameObserver {
    void showVictoryScreen(VictoryScreen victoryScreen);
    void showDefeatScreen(DefeatScreen defeatScreen);
    void onGameStateChanged(GameState state);
}
