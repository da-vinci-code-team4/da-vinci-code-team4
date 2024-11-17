package game.player;

import game.tile.JokerTile;
import game.tile.NumberTile;
import game.tile.Tile;
import game.tile.TileColor;
import game.tile.TileManager;
import game.tile.TileType;

import static game.tile.TileColor.*;
import static game.tile.TileType.*;

public class User extends Player {

    public User(TileManager tileManager, String name, int rank, int score) {
        super(tileManager, name, rank, score);
    }

    @Override
    public Tile selectOpponentPlayerTile() {
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
            return new JokerTile(JOKER, color);
        }
        if(typeInp.equals("숫자")) {
            return new NumberTile(NUMBER, color, typeNum);
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
