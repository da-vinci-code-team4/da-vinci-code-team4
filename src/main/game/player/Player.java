package main.game.player;

import main.game.status.Status;
import main.game.tile.Tile;
import main.game.tile.TileManager;

import java.util.*;

import static main.game.tile.TileType.JOKER;

/**
 * 각 플레이어의 정보가 저장되어 있다.
 * 플레이어가 턴을 넘겨 받을 때 사용될 메서드가 정의되어 있다.
 */
abstract public class Player {

    /**
     * 조커 타일과 주변 타일과의 가중치 차이다.
     */
    public static final int INSERTED_JOKER_TILE_WEIGHT_GAP = 2;

    private final TileManager tileManager;
    private final String name;
    private int rank;
    private int score;

    /**
     * 플레이어가 가진 타일을 저장한다.
     */
    private TreeSet<Tile> myTileDeck = new TreeSet<>(); //플레이어가 가진 타일덱
    Scanner scanner = new Scanner(System.in);

    /**
     * @param tileManager 전체 덱을 관리하는 타일 메니져를 입력 받는다
     * @param name        플레이어의 이름을 입력 받는다
     * @param rank        플레이어의 랭킹을 입력 받는다
     * @param score       플레이어의 점수를 입력 받는다
     */
    public Player(TileManager tileManager, String name, int rank, int score) {
        this.tileManager = tileManager;
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    /**
     * 게임 시작 시 타일을 메니져에게 분배 받을 때 호출되는 메서드다.
     * 만약 조커 타일이 있다면 사용자가 원하는 위치에 조커 타일을 둔다.
     *
     * @param tiles 분배된 타일을 입력 받는다
     */
    public void giveTileToPlayerAtStart(List<Tile> tiles) {
        List<Tile> jokerTile = new ArrayList<>();
        for (Tile tile : tiles) {
            if (tile.isTileType(JOKER)) { //조커 타일이면 숫자 타일 먼저 배치
                jokerTile.add(tile);
                continue;
            }
            myTileDeck.add(tile);
        }

        for (Tile tile : jokerTile) { //조커 타일 배치
            int position = inputJokerTilePosition();
            tile.defineTileWeightTo(getJokerTileWeight(position));
        }
    }

    /**
     * 각 턴마다 타일을 덱에서 하나씩 가져올 때 사용하는 메서드다.
     * 만약 조커 타일을 뽑았다면 사용자가 원하는 위치에 조커 타일을 둔다.
     */
    public Optional<Tile> drawTile(Status status) {
        Optional<Tile> drawTile = tileManager.getTileFromDeck();
        if (drawTile.isEmpty()) { //덱이 비어있으면 return
            return drawTile;
        }

        Tile tile = drawTile.get();
        if (tile.isTileType(JOKER)) {
            int position = inputJokerTilePosition(); //조커 타일이면 조커 타일 위치 선택

            tile.defineTileWeightTo(getJokerTileWeight(position));
        }

        myTileDeck.add(tile);
        status.saveDrawTile(tile.clone());
        return drawTile;
    }

    /**
     * 조커 타일의 가중치를 결정하는 메서드다.
     * 조커 타일은 왼쪽 타일 + 4만큼의 가중치를 가진다.
     * 만약 오른쪽에 이미 타일이 조커 타일이 있다면 왼쪽 타일 + 2만큼의 가중치를 가진다.
     *
     * @param position 사용자가 원하는 조커 타일의 위치를 입력 받는다(위치는 1부터 시작하며 n을 입력 받는다면 왼쪽에서부터 n번째 타일이라는 의미다)
     * @return position에 정렬 되기 위한 가중치를 계산하여 반환한다
     */
    private int getJokerTileWeight(int position) {
        if (position <= 0 || myTileDeck.size() + 1 < position) {
            throw new IllegalArgumentException("position을 다시 입력해주세요.");
        }

        int index = position - 1;
        if (index == 0) {
            return 0;
        }
        if (index == myTileDeck.size()) {
            return myTileDeck.last().getWeight() + 4;
        }

        ArrayList<Tile> tiles = new ArrayList<>(myTileDeck);
        Tile prevTile = tiles.get(index - 1);
        Tile nextTile = tiles.get(index);
        if (nextTile.isTileType(JOKER)) {
            return prevTile.getWeight() + 2;
        }

        return prevTile.getWeight() + 4;
    }

    //상대 타일 맞추기
    abstract public Tile getSelectedTile();

    /**
     * 조커 타일의 위치를 사용자가 선택한다.
     *
     * @return 사용자가 선택한 조커 타일의 위치를 반환한다
     * @implSpec 이 메서드는 사용자로부터 원하는 조커 타일의 위치를 입력 받고 반환해야 한다(위치는 1부터 시작한다, n이라면 왼쪽에서부터 n번째 타일이라는 의미).
     */
    abstract public int inputJokerTilePosition();

    /**
     * 플레이어가 상대방 타일을 맞췄을 때 턴을 유지할지 선택하는 메서드다.
     *
     * @return 플레이어의 턴 유지 여부를 반환한다
     * @implSpec 이 메서드는 플레이어가 상대방의 타일을 맞췄을 때 턴을 유지할 것인지 여부를 반환해야 한다(유지하면 true, 아니면 false).
     */
    abstract public boolean chooseToKeepTurn();

    public Tile getGuessedTile() {
        return null;
    }
}
