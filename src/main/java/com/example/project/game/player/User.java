package com.example.project.game.player;


import com.example.project.controller.Controller;
import com.example.project.game.manager.GameManager;
import com.example.project.game.popup.SwingPopUpInput;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class User extends Player {

    public User(String name, int rank, int score, int winCnt, int lossCnt) {
        super(name, rank, score, winCnt, lossCnt);
    }

    @Override
    public void guessTile() {
        Controller.informToPlayerForSelectTile();
    }

    @Override
    public int inputJokerTilePosition() {
        //사용자가 원하는 조커 타일의 위치를 입력 받음(타일의 위치는 1부터 시작)
        return scanner.nextInt();
    }

    @Override
    public boolean chooseToKeepTurn() {
        //1 입력받으면 true 리턴
        int inp = scanner.nextInt();
        if (inp == 1) {
            return true;
        }
        return false;
    }

}
