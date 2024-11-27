package com.example.project.controllers;

/**
 * GamePhase.java
 *
 * 게임의 각 단계를 정의합니다.
 */
public enum GamePhase {
    INITIAL_SELECTION,           // 중앙에서 초기 4개의 타일 선택
    COMPUTER_INITIAL_SELECTION,  // 컴퓨터가 초기 4개의 타일을 선택
    PLAYER_DRAW_PHASE,           // 플레이어가 중앙에서 타일을 뽑는 단계
    PLAYER_GUESS_PHASE,          // 플레이어가 컴퓨터의 타일을 추측하는 단계
    COMPUTER_TURN,               // 컴퓨터의 턴
    GAME_OVER                    // 게임 종료
}
