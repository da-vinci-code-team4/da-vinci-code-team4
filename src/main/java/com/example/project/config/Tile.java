package com.example.project.config;

import java.awt.Color;
import java.io.Serializable;

/**
 * Tile.java
 *
 * Đại diện cho một thẻ bài trong trò chơi.
 */
public class Tile implements Serializable, Cloneable {
    private TileType tileType;
    private int number; // Số từ 0 đến 11
    private boolean isOpened;
    private boolean isGuessedCorrectly; // Trạng thái đoán đúng
    private boolean isSelected; // Trạng thái được chọn trong giai đoạn chọn ban đầu

    public Tile(TileType tileType, int number) {
        this.tileType = tileType;
        this.number = number;
        this.isOpened = false;
        this.isGuessedCorrectly = false;
        this.isSelected = false;
    }

    // Getter và Setter
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
     * Lấy đường dẫn hình ảnh của thẻ bài.
     *
     * @return Đường dẫn hình ảnh
     */
    public String getImagePath() {
        String typePrefix = (tileType == TileType.BLACK) ? "b" : "w";
        return "/img/Card/tiles/" + typePrefix + number + ".png";
    }

    /**
     * Lấy đường dẫn hình ảnh khi thẻ bài được lật.
     *
     * @return Đường dẫn hình ảnh lật
     */
    public String getReverseImagePath() {
        String typePrefix = (tileType == TileType.BLACK) ? "b" : "w";
        return "/img/Card/reverseTiles/" + typePrefix + number + ".png";
    }

    /**
     * Lấy màu sắc tương ứng với thẻ bài.
     *
     * @return Màu sắc của thẻ bài
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