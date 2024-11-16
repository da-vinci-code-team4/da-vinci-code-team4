package game;

import game.player.Player;
import game.tile.TileManager;

public class DavinCiCode {

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
