package game.tile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static game.tile.TileColor.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

class JokerTileTest {

    @DisplayName("조커 타일은 깊은 복사가 가능하다.")
    @Test
    void cloneJokerTile() {
        //given
        TileColor color = WHITE;

        Tile origin = JokerTile.of(color);
        int weight = origin.getWeight();
        TileType tileType = origin.getTileType();
        TileColor tileColor = origin.getTileColor();
        boolean isOpen = origin.isOpen();

        //when
        Tile copy = origin.clone();

        //then
        assertThat(origin).isNotEqualTo(copy);
        assertThat(copy)
                .extracting("weight", "tileType", "tileColor", "isOpened")
                .containsExactlyInAnyOrder(weight, tileType, tileColor, isOpen);
    }
}