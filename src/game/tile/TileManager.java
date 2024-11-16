package game.tile;

import game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TileManager {

    private final List<Tile> deck = new ArrayList<>();

    public void initGame(Player firstPlayer, Player secondPlayer) {
        generateTile();
        shuffle();
        distributeTile();
    }

    private void generateTile() {

    }

    private void shuffle() {

    }

    private void distributeTile() {

    }

    public boolean isNotEqual(Tile selectedOpponentTile, Tile guessedOpponentTile) {
        return false;
    }
}
