package com.example.project.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.example.project.game.tile.Tile;
import com.example.project.utils.RoundedPanel;
import com.example.project.views.PlayGameWithPC;

public class CardMouseListener extends MouseAdapter {
    private PlayGameWithPC parentFrame;
    private RoundedPanel card;
    private String color;
    private String index;

    public CardMouseListener(PlayGameWithPC parentFrame, RoundedPanel card, String color, String index) {
        this.parentFrame = parentFrame;
        this.card = card;
        this.color = color;
        this.index = index;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("타일이 클릭되었습니다: " + color + " " + index);
        parentFrame.selectTile(color, index);
    }
}
