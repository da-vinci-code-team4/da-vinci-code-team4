package com.example.project.game.manager;

import static com.example.project.game.status.TurnResult.FAIL;
import static com.example.project.game.status.TurnResult.MATCH;
import static com.example.project.game.tile.TileType.JOKER;

import com.example.project.game.player.Player;
import com.example.project.game.player.User;
import com.example.project.game.popup.SelectTile;
import com.example.project.game.status.Status;
import com.example.project.game.status.TurnResult;
import com.example.project.game.tile.NumberTile;
import com.example.project.game.tile.Tile;

import javax.swing.*;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

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
    private final Status status = new Status(); //턴 상황 기록

    private int turn;
    private Player playerInTurn;
    private static TurnResult turnResult = null;
    public static CountDownLatch latch;

    /**
     * @param tileManager 타일 덱을 관리하는 객체를 입력 받는다
     * @param firstPlayer 선공 플레이어를 입력 받는다
     * @param secondPlayer 후공 플레이어를 입력 받는다
     * */
    public GameManager(TileManager tileManager, Player firstPlayer, Player secondPlayer) {
        this.tileManager = tileManager;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.playerInTurn = firstPlayer;
    }

    /**
     * 게임 진행을 관리하는 메서드다.
     * */
    public void startGame() {
        tileManager.initGame();
        firstPlayer.makeTileManager(tileManager); //user
        secondPlayer.makeTileManager(tileManager); //pc
        for (turn = 1; turn <= 26; turn++) {
            playGame(playerInTurn);
            if (turnResult.equals(FAIL)) {
                changePlayer();
            } else {
                playerInTurn.chooseToKeepTurn();
            }
            turnResult = null;

            if (status.isAllTileOpen()) {
                break;
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
    private void playGame(Player player) {
        if (turn != 1) { //첫번째 턴이 아닐 경우 타일 한장 가져오기
            Optional<Tile> drawTile = player.drawTile(status);
        }

        latch = new CountDownLatch(1); // 대기 객체 생성
        player.guessTile();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        latch = null;
        System.out.println("턴 종료");
    }

    private void changePlayer() {
        if (this.playerInTurn.equals(firstPlayer)) {
            playerInTurn = secondPlayer;
        }
        this.playerInTurn = firstPlayer;
    }


    public boolean isTurnOf(User user) {
        return playerInTurn.equals(user);
    }

    public static void setTurnResult(TurnResult turnResult) {
        GameManager.turnResult = turnResult;
    }
}
