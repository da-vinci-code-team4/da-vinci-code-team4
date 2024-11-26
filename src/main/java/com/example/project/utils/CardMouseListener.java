package com.example.project.utils;

import com.example.project.views.PlayGameWithPC;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardMouseListener extends MouseAdapter {
    private PlayGameWithPC parentFrame;
    private RoundedPanel card;
    private String imageName;

    public CardMouseListener(PlayGameWithPC parentFrame, RoundedPanel card, String imageName) {
        this.parentFrame = parentFrame;
        this.card = card;
        this.imageName = imageName;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Gọi phương thức addCardToMyCards trong PlayGameWithPC
        parentFrame.addTileToMyTiles(card, imageName);
    }
}
