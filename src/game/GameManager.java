package game;

import game.player.Player;
import game.save.Record;
import game.save.Recorder;
import game.status.Status;
import game.tile.TileManager;

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

        while (true) {
            playGame(firstPlayer);
            if (status.isAllTileOpened()) {

                break;
            }

            playGame(secondPlayer);
            if (status.isAllTileOpened()) {

                break;
            }
        }
    }

    /**
     * startGame()으로부터 호출되어 플레이어에게 턴을 시작하도록 메서드를 호출한다.
     * Record 인스턴스를 각 턴 시작 전에 생성하여 게임 상황을 기록할 수 있도록 플레이어에게 넘겨준다.
     *
     * @param player 현재 턴을 시작하는 플레이어다
     * */
    private void playGame(Player player) {
        boolean keepTurn = true;
        while (keepTurn) { //모든 카드가 오픈 되었는지 확인하는 코드 나중에 추가
            Record record = Record.of(++turn, player);
            keepTurn = player.turnStart(record);
            recorder.save(record);

            if (status.isAllTileOpened()) return;
        }
    }
}
