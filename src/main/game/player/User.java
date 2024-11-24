package main.game.player;

import main.game.tile.JokerTile;
import main.game.tile.NumberTile;
import main.game.tile.Tile;
import main.game.tile.TileColor;
import main.game.tile.TileManager;

import static main.game.tile.TileColor.*;
import static main.game.tile.TileType.*;

public class User extends Player {

    public User(TileManager tileManager, String name, int rank, int score) {
        super(tileManager, name, rank, score);
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
