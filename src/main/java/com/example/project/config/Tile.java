package com.example.project.config;

import java.awt.Color;
import java.io.Serializable;

/**
 * Tile.java
 *
 * 게임에서 카드 타일을 나타냅니다.
 */
public class Tile implements Serializable, Cloneable {
    private TileType tileType;
    private int number; // 0부터 11까지의 숫자
    private boolean isOpened;
    private boolean isGuessedCorrectly; // 정답 여부 상태
    private boolean isSelected; // 초기 선택 단계에서 선택된 상태

    public Tile(TileType tileType, int number) {
        this.tileType = tileType;
        this.number = number;
        this.isOpened = false;
        this.isGuessedCorrectly = false;
        this.isSelected = false;
    }

    // Getter 및 Setter
    public TileType getTileType() {
        return tileType;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public boolean isGuessedCorrectly() {
        return isGuessedCorrectly;
    }

    public void setGuessedCorrectly(boolean guessedCorrectly) {
        isGuessedCorrectly = guessedCorrectly;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * 카드 타일의 이미지 경로를 가져옵니다.
     *
     * @return 이미지 경로
     */
    public String getImagePath() {
        String typePrefix = (tileType == TileType.BLACK) ? "b" : "w";
        return "/img/Card/tiles/" + typePrefix + number + ".png";
    }

    /**
     * 카드 타일이 뒤집혔을 때의 이미지 경로를 가져옵니다.
     *
     * @return 뒤집힌 이미지 경로
     */
    public String getReverseImagePath() {
        String typePrefix = (tileType == TileType.BLACK) ? "b" : "w";
        return "/img/Card/reverseTiles/" + typePrefix + number + ".png";
    }

    /**
     * 카드 타일에 해당하는 색상을 가져옵니다.
     *
     * @return 타일의 색상
     */
    public Color getTileColor() {
        return (tileType == TileType.BLACK) ? Color.BLACK : Color.WHITE;
    }

    @Override
    public Tile clone() {
        try {
            return (Tile) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Tile(this.tileType, this.number);
        }
    }
}
