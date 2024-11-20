package main.game.tiles;

import main.game.player.Player;
import main.game.tiles.Tile.*;

import java.util.*;
import java.util.stream.Stream;

// import static main.game.DavinCiCode.*;

public class TileManager {

    private final List<Tile> deck = new ArrayList<>();
    private final Player firstPlayer; //선공
    private final Player secondPlayer; //후공

    public TileManager(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    //게임 시작시 호출, 타일 생성, 섞기, 분배
    public void initGame(Player firstPlayer, Player secondPlayer) {
        generateTile();
        shuffle();
        distributeTile();
    }

    //덱에서 타일 가져오기
    public Optional<Tile> getTileFromDeck() {
        return deck.isEmpty() ?
                Optional.empty() :
                Optional.of(deck.remove(0));
    }

    ///타일 생성
    private void generateTile() {
        generateNumberTile();
        generateJokerTile();
    }

    private void generateNumberTile() {
        Arrays.stream(TileColor.values())
                .flatMap(
                        color -> Stream.iterate(Tile.MIN_TILE_NUMBER, num -> num <= Tile.MAX_TILE_NUMBER, num -> num + 1)
                                .map(num -> new NumberTile(TileType.NUMBER, color, num))
                )
                .forEach(deck::add);
    }

    private void generateJokerTile() {
        deck.add(JokerTile.of(TileColor.BLACK));
        deck.add(JokerTile.of(TileColor.WHITE));
    }
    ///

    //타일 섞기
    private void shuffle() {
        Collections.shuffle(deck);
    }

    //타일 분배
    private void distributeTile() {
        List<Tile> firstTile = deck.subList(0, Tile.NUMBER_OF_INIT_TILE);
        firstPlayer.giveTileToPlayerAtStart(firstTile);
        firstTile.clear();

        List<Tile> secondTile = deck.subList(0, Tile.NUMBER_OF_INIT_TILE);
        secondPlayer.giveTileToPlayerAtStart(secondTile);
        secondTile.clear();
    }

    //타일 비교
    public boolean isNotEqual(Tile selectedOpponentTile, Tile guessedOpponentTile) {
        return false;
    }
}
