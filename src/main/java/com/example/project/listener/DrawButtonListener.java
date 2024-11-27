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
        Controller.getTileManager().distributeTile(parentPanel.getSelectedTiles());
        parentPanel.updateTiles(parentPanel.getMainPanel());
    }
}