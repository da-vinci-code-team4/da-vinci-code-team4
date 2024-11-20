package main.game;

import main.game.player.Player;
import main.game.save.Record;
import main.game.save.Recorder;
import main.game.status.Status;
import main.game.tiles.TileManager;

public class DavinCiCode {

    public static final int MIN_TILE_NUMBER = 0;
    public static final int MAX_TILE_NUMBER = 11;
    public static final int NUMBER_OF_INIT_TILE = 4; //게임 시작 시 각 플레이어에게 분배되는 타일의 수

    private final TileManager tileManager;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Recorder recorder = new Recorder();
    private final Status status = new Status();

    private int turn = 0;


    public DavinCiCode(TileManager tileManager, Player firstPlayer, Player secondPlayer) {
        this.tileManager = tileManager;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

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

    private void playGame(Player player) {
        boolean keepTurn = true;
        while (keepTurn) { //모든 카드가 오픈 되었는지 확인하는 코드 나중에 추가
            Record record = Record.of(++turn, player);
            keepTurn = player.turnStart(record);
            recorder.save(record);

            if(status.isAllTileOpened()) return;
        }
    }
}
