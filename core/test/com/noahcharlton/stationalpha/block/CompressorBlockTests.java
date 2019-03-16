package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompressorBlockTests {

    private final World world = new World();
    private Tile tile = world.getTileAt(0, 0).get();

    @Test
    void basicCompressorTest() {
        tile.setFloor(Floor.WOOD);
        tile.setBlock(Blocks.getCompressor());
        tile.getContainer().get().onUpdate();

        Assertions.assertEquals(3, tile.getOxygenLevel());
    }
}
