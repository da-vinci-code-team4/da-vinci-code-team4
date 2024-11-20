package game.tile;

import game.player.Player;

import java.util.*;
import java.util.stream.Stream;

import static game.DavinCiCode.*;

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

    public Optional<Tile> getTileFromDeck() {
        return deck.isEmpty() ?
                Optional.empty() :
                Optional.of(deck.remove(0));
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
        firstPlayer.giveTileToPlayerAtStart(firstTile);
        firstTile.clear();

        List<Tile> secondTile = deck.subList(0, NUMBER_OF_INIT_TILE);
        secondPlayer.giveTileToPlayerAtStart(secondTile);
        secondTile.clear();
    }

    public boolean isNotEqual(Tile selectedOpponentTile, Tile guessedOpponentTile) {
        return false;
    }

    //Open된 상태의 타일을 제공합니다.
    public List<Tile> getOpenTiles(){
        List<Tile> tileList = new ArrayList<>();

        for(Tile t : deck)
            if (t.getState()){
                tileList.add(t);
            }

        return tileList;
    }
}
