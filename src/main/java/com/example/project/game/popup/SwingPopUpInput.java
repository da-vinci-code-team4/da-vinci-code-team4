package com.example.project.game.popup;

import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingPopUpInput extends MouseAdapter {

    private static String inputNumber = "";
    private static Component clickedTile;

    private final RoundedPanel card;

    public SwingPopUpInput(RoundedPanel card) {
        this.card = card;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickedTile = e.getComponent();
        inputNumber = JOptionPane.showInputDialog(card, "Enter a number for " + card);
    }

    public static String getInputNumber() {
        return inputNumber;
    }

    public static Component getClickedTile() {
        return clickedTile;
    }
}
