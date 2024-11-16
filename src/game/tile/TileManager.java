package game.tile;

import game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static game.DavinCiCode.MAX_TILE_NUMBER;
import static game.DavinCiCode.MIN_TILE_NUMBER;

public class TileManager {

    private final List<Tile> deck = new ArrayList<>();

    public void initGame(Player firstPlayer, Player secondPlayer) {
        generateTile();
        shuffle();
        distributeTile();
    }

    private void generateTile() {
        generateNumberTile();
        generateJokerTile();
    }

    private void generateNumberTile() {
        Arrays.stream(TileColor.values())
                .flatMap(
                        color -> Stream.iterate(MIN_TILE_NUMBER, num -> num <= MAX_TILE_NUMBER, num -> num + 1)
                                .map(num -> new NumberTile(TileType.NUMBER, color, num))
                )
                .forEach(deck::add);
    }

    private void generateJokerTile() {
        deck.add(JokerTile.of(TileColor.BLACK));
        deck.add(JokerTile.of(TileColor.WHITE));
    }

    private void shuffle() {

    }

    private void distributeTile() {

    }

    public boolean isNotEqual(Tile selectedOpponentTile, Tile guessedOpponentTile) {
        return false;
    }
}
