package game.player;

import game.save.Record;
import game.tile.Tile;
import game.tile.TileManager;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;

import static game.tile.TileType.JOKER;

/**
 * 각 플레이어의 정보가 저장되어 있다.
 * 플레이어가 턴을 넘겨 받을 때 사용될 메서드가 정의되어 있다.
 */
abstract public class Player {

    /**
     * 조커 타일과 숫자 타일의 가중치 차이다.
     * 조커 타일의 위치를 플레이어가 정하면 해당 위치 주변의 숫자 타일과 가중치 차이를 3만큼 벌린다.
     * */
    public static final int INSERTED_JOKER_TILE_WEIGHT_GAP = 3;

    private final TileManager tileManager;
    private final String name;
    private int rank;
    private int score;

    /**플레이어가 가진 타일을 저장한다.*/
    private TreeSet<Tile> myTileDeck = new TreeSet<>(); //플레이어가 가진 타일덱
    Scanner scanner = new Scanner(System.in);

    /**
     * @param tileManager 전체 덱을 관리하는 타일 메니져를 입력 받는다
     * @param name 플레이어의 이름을 입력 받는다
     * @param rank 플레이어의 랭킹을 입력 받는다
     * @param score 플레이어의 점수를 입력 받는다
     */
    public Player(TileManager tileManager, String name, int rank, int score) {
        this.tileManager = tileManager;
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    /**
     * 플레이어가 턴을 시작하면 호출되는 메서드다.
     *
     * @param status 턴을 시작하고 게임 내용을 기록할 status 객체의 참조를 입력 받는다
     * @return 플레이어의 턴이 계속 유지 된다면 true, 턴이 종료되면 false를 반환한다
     */
    public boolean turnStart(Status status) {
        /*
         * 턴 진행 중 기록해야 하는 것
         * 1. 플레이어가 뽑은 타일
         * 2. 고른 상대방 타일 정보와 플레이어가 예측한 타일 정보
         * 3. 결과(맞췄는지)
         * 4. 타일 현황(Status, 오픈된 타일, 가진 개수, 덱에 남은 타일 등)
         * */
        drawTileFromDeck(status);

        Tile selectedTile = getSelectedTile();

        //상대 타일과 맞는지 확인하는 메소드를 호출해야함
        /*if (selectedTile.equals()) {
            //만약 같으면 chooseToKeepTurn() 호출
            if(chooseToKeepTurn()){
                return true;
            }
        }*/
        return false;
    }

    /**
     * 게임 시작 시 타일을 메니져에게 분배 받을 때 호출되는 메서드다.
     * 만약 조커 타일이 있다면 사용자가 원하는 위치에 조커 타일을 둔다.
     *
     * @param tiles 분배된 타일을 입력 받는다
     */
    public void giveTileToPlayerAtStart(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.isTileType(JOKER)) { //조커 타일이면 사용자로부터 위치 입력 받음
                int position = inputJokerTilePosition();

                tile.defineTileWeightTo(getJokerTileWeight(position));
            }

            myTileDeck.add(tile);
        }
    }

    /**
     * 각 턴마다 타일을 덱에서 하나씩 가져올 때 사용하는 메서드다.
     * 만약 조커 타일을 뽑았다면 사용자가 원하는 위치에 조커 타일을 둔다.
     */
    private void drawTileFromDeck(Status status) {
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

    /**
     * 조커 타일의 가중치를 결정하는 메서드다.
     *
     * @param position 사용자가 원하는 조커 타일의 위치를 입력 받는다(위치는 1부터 시작하며 n을 입력 받는다면 왼쪽에서부터 n번째 타일이라는 의미다)
     * @return position에 정렬 되기 위한 가중치를 계산하여 반환한다
     */
    private int getJokerTileWeight(int position) {
        if (position == 1) {
            return myTileDeck.getFirst().getWeight() - INSERTED_JOKER_TILE_WEIGHT_GAP;
        }

        return myTileDeck.stream()
                .limit(position - 1)
                .skip(Math.max(0, position - 2))
                .findFirst()
                .get()
                .getWeight() + INSERTED_JOKER_TILE_WEIGHT_GAP;
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
     * @return 플레이어의 턴 유지 여부를 반환한다
     * @implSpec 이 메서드는 플레이어가 상대방의 타일을 맞췄을 때 턴을 유지할 것인지 여부를 반환해야 한다(유지하면 true, 아니면 false).
     */
    abstract public boolean chooseToKeepTurn();
}