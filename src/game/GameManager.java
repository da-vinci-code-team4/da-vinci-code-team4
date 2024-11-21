package game;

import game.player.Player;
import game.save.Recorder;
import game.status.Status;
import game.tile.NumberTile;
import game.tile.Tile;
import game.tile.TileManager;

import java.util.Optional;

import static game.tile.TileType.*;

/**
 * 전체적인 게임 진행을 담당하는 클래스다.
 */
public class GameManager {

    /**타일 번호 최솟값*/
    public static final int MIN_TILE_NUMBER = 0;

    /**타일 번호 최댓값*/
    public static final int MAX_TILE_NUMBER = 11;

    /** 게임 시작 시 각 플레이어가 나눠가지는 타일의 수*/
    public static final int NUMBER_OF_INIT_TILE = 4;

    private final TileManager tileManager;
    private final Player firstPlayer; //선공
    private final Player secondPlayer; //후공
    private final Recorder recorder = new Recorder(); //게임 기록
    private final Status status = new Status(); //턴 상황 기록

    private int turn = 0; //게임 턴 명시

    /**
     * @param tileManager 타일 덱을 관리하는 객체를 입력 받는다
     * @param firstPlayer 선공 플레이어를 입력 받는다
     * @param secondPlayer 후공 플레이어를 입력 받는다
     * */
    public GameManager(TileManager tileManager, Player firstPlayer, Player secondPlayer) {
        this.tileManager = tileManager;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    /**
     * 게임 진행을 관리하는 메서드다.
     * */
    public void startGame() {
        tileManager.initGame(firstPlayer, secondPlayer);

        while (!status.isAllTileOpened()) {
            while (!status.isAllTileOpened() && playGame(firstPlayer)) {

            }

            while (!status.isAllTileOpened() && playGame(secondPlayer)) {

            }
        }
    }

    /**
     * startGame()으로부터 호출되어 플레이어에게 턴을 시작하도록 메서드를 호출한다.
     * status를 각 턴마다 게임 상황을 기록할 수 있도록 플레이어에게 넘겨준다.
     * 변경된 status를 Record를 생성하여 기록한 뒤 저장한다.
     *
     * @param player 현재 턴을 시작하는 플레이어다
     * */
    private boolean playGame(Player player) {
        Optional<Tile> drawTile = player.drawTile(status);

        Tile selectedTile = player.selectTileFromOpponent();
        status.saveSelectedTile(selectedTile.clone());

        Tile guessedTile = player.getGuessedTile();
        status.saveGuessedTile(guessedTile.clone());

        //타입이 다르면 턴 종료
        if (!selectedTile.getTileType().equals(guessedTile.getTileType())) {
            status.saveResult(false);
            drawTile.ifPresent(tile -> {
                tile.setOpen(true);
                status.saveOpenedTile(drawTile.get());
            });
            return false;
        }

        //둘다 조커 타입
        if (selectedTile.isTileType(JOKER)) {
            status.saveResult(true);
            selectedTile.setOpen(true);
            status.saveOpenedTile(selectedTile);
            return player.chooseToKeepTurn();
        }

        //숫자 타입(숫자까지 맞아야함)
        int selectedTileNumber = ((NumberTile) selectedTile).getNumber();
        int guessedTileNumber = ((NumberTile) selectedTile).getNumber();
        if (selectedTileNumber == guessedTileNumber) {
            status.saveResult(true);
            selectedTile.setOpen(true);
            status.saveOpenedTile(selectedTile);
            return player.chooseToKeepTurn();
        }

        status.saveResult(false);
        drawTile.ifPresent(tile -> {
            tile.setOpen(true);
            status.saveOpenedTile(drawTile.get());
        });
        return false;
    }
}
