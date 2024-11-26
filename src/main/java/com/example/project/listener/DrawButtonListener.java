package com.example.project.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.project.controller.Controller;
import com.example.project.views.PlayGameWithPC;

public class DrawButtonListener implements ActionListener {
    private PlayGameWithPC parentPanel;

    public DrawButtonListener(PlayGameWithPC parentPanel) {
        this.parentPanel = parentPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Draw button clicked"+parentPanel.getSelectedTiles());
        // System.out.println("Before tiles distributed"+Controller.getTileManager().getFirstPlayer().getDeckSize());
        Controller.getTileManager().distributeTile(parentPanel.getSelectedTiles());
        // System.out.println("After tiles distributed"+Controller.getTileManager().getFirstPlayer().getDeckSize());
        parentPanel.updateTiles(parentPanel.getMainPanel());
        // System.out.println("parent panel : "+parentPanel.getMainPanel().getName());
    }
}