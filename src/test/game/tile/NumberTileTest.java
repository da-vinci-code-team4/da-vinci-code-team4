package game.tile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static game.tile.TileColor.*;
import static org.assertj.core.api.Assertions.*;

class NumberTileTest {

    @DisplayName("숫자 타일은 깊은 복사가 가능하다.")
    @Test
    void cloneNumberTile() {
        //given
        int tileNumber = 5;
        TileColor color = WHITE;

        Tile origin = NumberTile.of(color, tileNumber);
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

        assertThat((NumberTile) copy).extracting("number")
                .isEqualTo(tileNumber);
    }
}