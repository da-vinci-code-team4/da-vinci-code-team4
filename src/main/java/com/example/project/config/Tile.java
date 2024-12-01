package com.example.project.config;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Tile.java
 *
 * 게임에서 카드 타일을 나타냅니다.
 */
public class Tile implements Serializable, Cloneable {
    private TileType tileType;
    private TileColor tileColor;
    private int number; // 0부터 11까지의 숫자
    private boolean isOpened;
    private boolean isGuessedCorrectly; // 정답 여부 상태
    private boolean isSelected; // 초기 선택 단계에서 선택된 상태

    public Tile(TileType tileType, TileColor tileColor, int number) {
        this.tileType = tileType;
        this.tileColor = tileColor;
        this.number = number;
        this.isOpened = false;
        this.isGuessedCorrectly = false;
        this.isSelected = false;
    }

    // Getter 및 Setter
    public TileType getTileType() {
        return tileType;
    }
    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    //조커타일인지 확인 후 입력한 위치에 넣기
    public void isJoker(List<Tile> tiles) {
        int setting;
        if(this.tileType == TileType.JOKER) {
            String inp = JOptionPane.showInputDialog("조커를 넣을 위치를 입력하세요 (0 - " + (tiles.size()) + " )");
            if(Integer.parseInt(inp) > tiles.size() || Integer.parseInt(inp) < 0) {
                while(Integer.parseInt(inp) > tiles.size() || Integer.parseInt(inp) < 0) {
                    JOptionPane.showMessageDialog(null, "유효하지 않은 위치 선택입니다.");
                    inp = JOptionPane.showInputDialog("조커를 넣을 위치를 다시 입력하세요 (0 - " + tiles.size() + " )");
                }
            }

            if(Integer.parseInt(inp) == tiles.size()) {
                int temp = tiles.get(Integer.parseInt(inp)-1).getNumber();
                setting = temp + 1;
            }
            else if(Integer.parseInt(inp) == 0){
                int afterTile = tiles.get(Integer.parseInt(inp)).getNumber();
                setting = afterTile - 1;
            }
            else{
                int afterTile = tiles.get(Integer.parseInt(inp)).getNumber();
                int beforeTile = tiles.get(Integer.parseInt(inp)-1).getNumber();
                if(afterTile == beforeTile) {
                    setting = afterTile - 1;
                    tiles.get(Integer.parseInt(inp)-1).setNumber(setting-1);
                }
                else{
                    setting = afterTile - 1;
                }
            }
            setNumber(setting);
        }
    }

    public void isJoker(int number, List<Tile> selectedTiles) {
        int setting;
        if(this.tileType == TileType.JOKER) {
            String inp = JOptionPane.showInputDialog("조커를 넣을 위치를 입력하세요 (0 - " + (selectedTiles.size()-1) + " )");
            if(Integer.parseInt(inp) > selectedTiles.size() || Integer.parseInt(inp) < 0) {
                while(Integer.parseInt(inp) > selectedTiles.size() || Integer.parseInt(inp) < 0) {
                    JOptionPane.showMessageDialog(null, "유효하지 않은 위치 선택입니다.");
                    inp = JOptionPane.showInputDialog("조커를 넣을 위치를 다시 입력하세요 (0 - " + (selectedTiles.size()-1) + " )");
                }
            }

            if(Integer.parseInt(inp) == selectedTiles.size()-1) {
                int temp = selectedTiles.get(Integer.parseInt(inp)-1).getNumber();
                setting = temp + 1;
            }
            else if(Integer.parseInt(inp) == 0){
                int afterTile = selectedTiles.get(Integer.parseInt(inp)).getNumber();
                setting = afterTile - 1;
            }
            else{
                int afterTile = selectedTiles.get(Integer.parseInt(inp)).getNumber();
                int beforeTile = selectedTiles.get(Integer.parseInt(inp)-1).getNumber();
                if(afterTile == beforeTile) {
                    setting = afterTile - 1;
                    selectedTiles.get(Integer.parseInt(inp)-1).setNumber(setting-1);
                }
                else{
                    setting = afterTile - 1;
                }
            }
            setNumber(setting);
        }
    }
    public void isJoker() {
        if(this.tileType == TileType.JOKER){
            Random rand = new Random();
            number = rand.nextInt(0,13) * 10 - 1;
            setNumber(number);
        }
    }

    private void setNumber(int number) {
        this.number = number;
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
        if(tileType.equals(TileType.JOKER)){
            return getJokerImagePath();
        }
        String typePrefix = (tileColor == TileColor.BLACK) ? "b" : "w";
        return "/img/Card/tiles/" + typePrefix + number/10 + ".png";
    }

    /**
     * 카드 타일이 뒤집혔을 때의 이미지 경로를 가져옵니다.
     *
     * @return 뒤집힌 이미지 경로
     */
    public String getReverseImagePath() {
        if(tileType.equals(TileType.JOKER)){
            return getJokerImagePath();
        }
        String typePrefix = (tileColor == TileColor.BLACK) ? "b" : "w";
        return "/img/Card/reverseTiles/" + typePrefix + number/10 + ".png";
    }

    public String getBackImagePath() {
        String typePrefix = (tileColor == TileColor.BLACK) ? "black" : "white";
        return "/img/Card/tiles/" + typePrefix + ".png";
    }

    public String getJokerImagePath() {
        String typePrefix = (tileColor == TileColor.BLACK) ? "bjoker" : "wjoker";
        return "/img/Card/tiles/" + typePrefix + ".png";
    }

    /**
     * 카드 타일에 해당하는 색상을 가져옵니다.
     *
     * @return 타일의 색상
     */
    public Color getTileColor() {
        return (tileColor == TileColor.BLACK) ? Color.BLACK : Color.WHITE;
    }

    @Override
    public Tile clone() {
        try {
            return (Tile) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Tile(this.tileType,this.tileColor, this.number);
        }
    }
}
