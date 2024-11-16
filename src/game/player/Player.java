package game.player;

import game.tile.Tile;
import game.tile.TileManager;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import static game.tile.TileType.*;


abstract public class Player {
    Scanner scanner = new Scanner(System.in);

    //플레이어가 가진 타일덱
    private TreeMap<Integer, Tile> myTileDeck = new TreeMap<>();

    private final TileManager tileManager;
    private final String name;
    private int rank;
    private int score;

    private static int tileNo;

    public Player(TileManager tileManager,String name, int rank, int score) {
        this.tileManager = tileManager;
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    public boolean turnStart() {
        if(tileManager.deck != null){
            Tile newTile = getTileFromDeque();
            if (newTile.isTileType(JOKER)) {
                insertJokerTile(); //조커타일이면 조커타일 정렬 메소드 호출
            }
            else {
                addTileToDeck(newTile, 1); //일반타일이면 그냥 덱에 추가하는 메소드
            }
        }

        Tile selectedTile = selectOpponentPlayerTile();

        //상대 타일과 맞는지 확인하는 메소드를 호출해야함
        if (selectedTile.equals()) {
            //만약 같으면 chooseToKeepTurn() 호출
            if(chooseToKeepTurn()){
                return true;
            }
        }
        return false;
    }

    public Tile getTileFromDeque() {
        //턴마다 (남아 있다면)타일을 하나씩 가져옴
        return //랜덤으로 하나 받는 메소드를 tileManager 에서 호출 해서 리턴함;
    }

    public void addTileToDeck(Tile tile, int opt) {
        //타일을 받아서 덱에 추가
        myTileDeck.put(++tileNo, tile);
    }

    public void addTileToDeck(List<Tile> tiles) {
        //타일을 받아서 덱에 추가
        for (Tile tile : tiles) {
            myTileDeck.put(++tileNo, tile);
        }
    }


    //상대 타일 맞추기
    abstract public Tile selectOpponentPlayerTile();

    //조커타일인 경우 재정렬해주는 타일
    abstract public int insertJokerTile();

    //타일을 맞춘 뒤 계속 맞출것인지
    abstract public boolean chooseToKeepTurn();
}
