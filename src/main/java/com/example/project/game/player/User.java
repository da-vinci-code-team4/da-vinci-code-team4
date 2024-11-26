package com.example.project.game.player;


import static com.example.project.game.tile.TileColor.BLACK;
import static com.example.project.game.tile.TileColor.WHITE;

import com.example.project.game.tile.JokerTile;
import com.example.project.game.tile.NumberTile;
import com.example.project.game.tile.Tile;
import com.example.project.game.tile.TileColor;

public class User extends Player {

    public User(String name, int rank, int score, int winCnt, int lossCnt) {
        super(name, rank, score, winCnt, lossCnt);
    }

    @Override
    public Tile getSelectedTile() {
        TileColor color;

        String colorInp = scanner.next();
        String typeInp = scanner.next();
        int typeNum = scanner.nextInt();

        if (colorInp.equals("Black")){
            color = BLACK;
        }
        else {
            color = WHITE;
        }

        if(typeInp.equals("조커")) {
            return JokerTile.of(color);
        }
        if(typeInp.equals("숫자")) {
            return NumberTile.of(color, typeNum);
        }

        return null;
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
