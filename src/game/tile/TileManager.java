package game.tile;

import game.DavinCiCode;
import game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static game.DavinCiCode.*;
import static game.DavinCiCode.MAX_TILE_NUMBER;
import static game.DavinCiCode.MIN_TILE_NUMBER;

public class TileManager {

    private final List<Tile> deck = new ArrayList<>();
    private final Player firstPlayer; //선공
    private final Player secondPlayer; //후공

    public TileManager(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

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
        Collections.shuffle(deck);
    }

    private void distributeTile() {
        List<Tile> firstTile = deck.subList(0, NUMBER_OF_INIT_TILE);
        firstPlayer.addTileToDeck(firstTile);
        firstTile.clear();

        List<Tile> secondTile = deck.subList(0, NUMBER_OF_INIT_TILE);
        secondPlayer.addTileToDeck(secondTile);
        secondTile.clear();
    }

    public boolean isNotEqual(Tile selectedOpponentTile, Tile guessedOpponentTile) {
        return false;
    }
}
