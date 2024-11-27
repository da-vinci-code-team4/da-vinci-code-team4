package com.example.project.game.manager;

import static com.example.project.game.status.TurnResult.FAIL;
import static com.example.project.game.status.TurnResult.MATCH;
import static com.example.project.game.tile.TileType.JOKER;

import com.example.project.game.player.Player;
import com.example.project.game.save.Recorder;
import com.example.project.game.status.Status;
import com.example.project.game.tile.NumberTile;
import com.example.project.game.tile.Tile;
import java.util.Optional;

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
        tileManager.initGame();
        firstPlayer.makeTileManager(tileManager);
        secondPlayer.makeTileManager(tileManager);
        while (!status.isAllTileOpen()) {
            while (!status.isAllTileOpen() && playGame(firstPlayer)) {
//                recorder.save(Record.of(++turn, firstPlayer, status)); //status는 나중에 스냅샷으로 깊은 복사 저장
            }

            while (!status.isAllTileOpen() && playGame(secondPlayer)) {
//                recorder.save(Record.of(++turn, secondPlayer, status));
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
//        Optional<Tile> drawTile = player.drawTile(status);

        Tile selectedTile = player.getSelectedTile();
        status.saveSelectTile(selectedTile.clone());

        Tile guessedTile = player.getGuessedTile();
        status.saveGuessTile(guessedTile.clone());

        //타입이 다르면 턴 종료
        if (!selectedTile.getTileType().equals(guessedTile.getTileType())) {
//            saveFail(drawTile, player);
            return false;
        }

        //둘다 조커 타입
        if (selectedTile.isTileType(JOKER)) {
            saveMatch(selectedTile, player);
            return player.chooseToKeepTurn();
        }

        //숫자 타입(숫자까지 맞아야함)
        int selectedTileNumber = getTileNumber(selectedTile);
        int guessedTileNumber = getTileNumber(guessedTile);
        if (selectedTileNumber == guessedTileNumber) {
            saveMatch(selectedTile, player);
            return player.chooseToKeepTurn();
        }

//        saveFail(drawTile, player);
        return false;
    }

    private void saveMatch(Tile selectedTile, Player player) {
        status.saveResult(MATCH, player);
        selectedTile.setOpen(true);
        status.saveOpenTile(selectedTile);
    }

    private void saveFail(Optional<Tile> drawTile, Player player) {
        status.saveResult(FAIL, player);
        drawTile.ifPresent(tile -> {
            tile.setOpen(true);
            status.saveOpenTile(drawTile.get());
        });
    }

    private int getTileNumber(Tile tile) {
        return ((NumberTile) tile).getNumber();
    }

    public Status getStatus() {
        return status;
    }
}
