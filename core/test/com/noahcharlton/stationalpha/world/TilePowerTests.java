package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.power.PoweredContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TilePowerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(3, 3).get();

    @Test
    void poweredContainerAddsConduit() {
        tile.setBlock(Blocks.getWall(), new TestPowerContainer(tile));

        tile.setConduit(false);

        Assertions.assertTrue(tile.hasConduit());
    }

    public static class TestPowerContainer extends BlockContainer implements PoweredContainer{

        public TestPowerContainer(Tile tile) {
            super(tile, Blocks.getWall());
        }
    }
}

