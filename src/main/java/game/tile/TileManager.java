package game.tile;


import game.player.Player;

import java.util.*;
import java.util.stream.Stream;

import static game.GameManager.*;


/**
 * 타일 덱을 관리하는 클래스다.
 */
public class TileManager {

    private final List<Tile> deck = new ArrayList<>();
    private final Player firstPlayer; //선공
    private final Player secondPlayer; //후공

    public TileManager(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    /**
     * 게임 시작 전 초기화를 하는 메서드다.
     *
     * @param firstPlayer  선공 플레이어를 입력 받는다
     * @param secondPlayer 후공 플레이어를 입력 받는다
     */
    public void initGame(Player firstPlayer, Player secondPlayer) {
        generateTile();
        shuffle();
        distributeTile();
    }

    /**
     * 턴마다 플레이어가 덱에서 타일을 하나씩 가져올 때 사용하는 메서드다.
     *
     * @return 덱에 타일이 남아 있다면 타일을 하나 반환하며 만약 없다면 Optional.empty()를 반환한다
     */
    public Optional<Tile> getTileFromDeck() {
        return deck.isEmpty() ?
                Optional.empty() :
                Optional.of(deck.remove(0));
    }

    /**
     * 초기화 시 타일을 생성하는 메서드다.
     * 숫자 타일, 조커 타일을 생성하여 덱에 저장하는 메서드를 내부적으로 호출한다.
     */
    private void generateTile() {
        generateNumberTile();
        generateJokerTile();
    }

    /**
     * 숫자 타일을 생성하여 덱에 저장하는 메서드다.
     */
    private void generateNumberTile() {
        Arrays.stream(TileColor.values())
                .flatMap(
                        color -> Stream.iterate(MIN_TILE_NUMBER, num -> num <= MAX_TILE_NUMBER, num -> num + 1)
                                .map(num -> NumberTile.of(color, num))
                )
                .forEach(deck::add);
    }

    /**
     * 조커 타일을 생성하여 덱에 저장하는 메서스다.
     */
    private void generateJokerTile() {
        deck.add(JokerTile.of(TileColor.BLACK));
        deck.add(JokerTile.of(TileColor.WHITE));
    }

    /**
     * 덱에 있는 타일을 무작위로 섞는 메서드다.
     */
    private void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * 타일을 각 플레이어에게 분배하는 메서드다.
     * <p>
     * 덱에 생성되어 있는 타일을 NUMBER_OF_INIT_TILE만큼 각 플레이어에게 분배한다.
     * 이후 나눠준 타일은 덱에서 삭제한다.
     */
    private void distributeTile() {
        List<Tile> firstTile = deck.subList(0, NUMBER_OF_INIT_TILE);
        firstPlayer.giveTileToPlayerAtStart(firstTile);
        firstTile.clear();

        List<Tile> secondTile = deck.subList(0, NUMBER_OF_INIT_TILE);
        secondPlayer.giveTileToPlayerAtStart(secondTile);
        secondTile.clear();
    }

    public boolean isEqual(Tile selectedOpponentTile, Tile guessedOpponentTile) {
        return false;
    }
}
