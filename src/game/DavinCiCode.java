package game;

import game.player.Player;
import game.tile.TileManager;

public class DavinCiCode {

    public static final int MIN_TILE_NUMBER = 0;
    public static final int MAX_TILE_NUMBER = 11;
    public static final int NUMBER_OF_INIT_TILE = 4; //게임 시작 시 각 플레이어에게 분배되는 타일의 수

    private final TileManager tileManager;
    private final Player firstPlayer;
    private final Player secondPlayer;

    public DavinCiCode(TileManager tileManager, Player firstPlayer, Player secondPlayer) {
        this.tileManager = tileManager;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void startGame() {
        tileManager.initGame(firstPlayer, secondPlayer);

        boolean keepTurn = true;
        while (keepTurn) {
            keepTurn = firstPlayer.turnStart();
        }

        keepTurn = true;
        while (keepTurn) {
            keepTurn = secondPlayer.turnStart();
        }
    }
}
