package com.example.project.game.player;


import com.example.project.controller.Controller;
import com.example.project.game.popup.SelectTile;
import com.example.project.game.popup.SwingPopUpInput;
import com.example.project.game.tile.Tile;
import com.example.project.utils.RoundedPanel;
import com.example.project.views.PlayGameWithPC;

import java.awt.*;

public class User extends Player {

    public User(String name, int rank, int score) {
        super(name, rank, score);
    }

    @Override
    public SelectTile getSelectedTile() {
        Component[] components = PlayGameWithPC.getComputerCards().getComponents();
        addPopUpListener(components);

        System.out.println("상대방 카드를 클릭하세요.");
        String inputNumber = "";
        while (inputNumber.equals("")) {
            inputNumber = SwingPopUpInput.getInputNumber(); //예상 숫자 or 조커
        }

        int tileIndex = findTileIndex(SwingPopUpInput.getClickedTile(), components);
        Tile tile = Controller.getSecondPlayerTileAt(tileIndex);

        removePopUpListener(components);
        return new SelectTile(tile, inputNumber);
    }

    private void addPopUpListener(Component[] components) {
        for (Component component : components) {
            component.addMouseListener(new SwingPopUpInput((RoundedPanel) component));
        }
    }

    private int findTileIndex(Component clickedTile, Component[] components) {
        for (int i = 0; i < components.length; i++) {
            if (components[i].equals(clickedTile)) {
                return i;
            }
        }
        return -1;
    }

    private void removePopUpListener(Component[] components) {
        for (Component component : components) {
            component.removeMouseListener(new SwingPopUpInput((RoundedPanel) component));
        }
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
