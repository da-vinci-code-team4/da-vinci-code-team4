package game.tile;

import game.player.Computer;
import game.player.Player;
import game.player.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class TileManagerTest {

    @DisplayName("TileManager는 26개의 타일을 만든다(숫자 0~11, 조커 2개, 흑 and 백).")
    @Test
    void initGame() {
        //given
        Player user = new User("userA", 1, 1);
        Player com = new Computer("comA", 1, 1);

        TileManager tileManager = new TileManager(user, com);

        //when
        tileManager.initGame();

        //then
        assertThat(tileManager.getDeck()).hasSize(26);
    }

    @DisplayName("초기화 후 TileManager는 타일을 각 플레이어에게 4개씩 나누어 준다.")
    @Test
    void distributeTile() {
        //given
        Player user = new User("userA", 1, 1);
        Player com = new Computer("comA", 1, 1);

        TileManager tileManager = new TileManager(user, com);
        tileManager.initGame();

        //when
        tileManager.distributeTile();

        //then
        assertThat(user.getMyTileDeck()).hasSize(4);
        assertThat(com.getMyTileDeck()).hasSize(4);
    }

    @DisplayName("플레이어는 TileManager를 통해 매 턴마다 하나의 타일을 가져온다.")
    @Test
    void getTile() {
        //given
        Player user = new User("userA", 1, 1);
        Player com = new Computer("comA", 1, 1);

        TileManager tileManager = new TileManager(user, com);
        tileManager.initGame();

        //when
        Optional<Tile> result = tileManager.getTile();

        //then
        assertThat(result.get()).isInstanceOf(Tile.class);
    }

    @DisplayName("덱이 비어 있다면 Optional.empty()를 반환한다.")
    @Test
    void getTileFromEmptyDeck() {
        //given
        Player user = new User("userA", 1, 1);
        Player com = new Computer("comA", 1, 1);

        TileManager tileManager = new TileManager(user, com);

        //when
        Optional<Tile> result = tileManager.getTile();

        //then
        assertThat(result).isEmpty();
    }
}