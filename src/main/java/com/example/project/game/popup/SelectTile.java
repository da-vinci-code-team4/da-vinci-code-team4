package com.example.project.game.popup;

import com.example.project.game.tile.Tile;

public class SelectTile {
    private final Tile tile;
    private final String inputNumber;

    public SelectTile(Tile tile, String inputNumber) {
        this.tile = tile;
        this.inputNumber = inputNumber;
    }

    public Tile getTile() {
        return tile;
    }

    public String getInputNumber() {
        return inputNumber;
    }
}
