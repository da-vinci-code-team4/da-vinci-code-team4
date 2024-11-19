package game.player;

import game.tile.JokerTile;
import game.tile.Tile;
import game.tile.TileManager;

import java.util.*;

import static game.tile.TileType.*;


abstract public class Player {

    public static final int INSERTED_TILE_WEIGHT_GAP = 3;

    private final TileManager tileManager;
    private final String name;
    private int rank;
    private int score;
    private TreeSet<Tile> myTileDeck = new TreeSet<>(); //플레이어가 가진 타일덱
    Scanner scanner = new Scanner(System.in);

    public Player(TileManager tileManager, String name, int rank, int score) {
        this.tileManager = tileManager;
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    public boolean turnStart() {
        drawTileFromDeck();

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

    public void giveTileToPlayerAtStart(List<Tile> tiles) {
        //게임 시작 시 타일을 받아서 덱에 추가
        for (Tile tile : tiles) {
            if (tile.isTileType(JOKER)) { //조커 타일이면 사용자로부터 위치 입력 받음
                int position = inputJokerTilePosition();

                tile.defineTileWeightTo(getJokerTileWeight(position));
            }

            myTileDeck.add(tile);
        }
    }

    private void drawTileFromDeck() {
        Optional<Tile> newTile = tileManager.getTileFromDeck();
        if (newTile.isEmpty()) { //덱이 비어있으면 return
            return;
        }

        Tile tile = newTile.get();
        if (tile.isTileType(JOKER)) {
            int position = inputJokerTilePosition(); //조커 타일이면 조커 타일 위치 선택

            tile.defineTileWeightTo(getJokerTileWeight(position));
        }

        myTileDeck.add(tile);
    }

    private int getJokerTileWeight(int position) {
        if (position == 1) {
            return myTileDeck.getFirst().getWeight() - INSERTED_TILE_WEIGHT_GAP;
        }

        return myTileDeck.stream()
                .limit(position - 1)
                .skip(Math.max(0, position - 2))
                .findFirst()
                .get()
                .getWeight() + INSERTED_TILE_WEIGHT_GAP;
    }

    //상대 타일 맞추기
    abstract public Tile selectOpponentPlayerTile();

    //조커타일인 경우 재정렬해주는 타일
    abstract public int inputJokerTilePosition();

    //타일을 맞춘 뒤 계속 맞출것인지
    abstract public boolean chooseToKeepTurn();
}
