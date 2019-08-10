package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PowerProducerContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(1, 1).get();

    @Test
    void addsPowerOnUpdateTest() {
        PowerProducerContainer container = new PowerProducerContainer(tile, Blocks.getSolarPanel(),
                BlockRotation.NORTH, 1);
        tile.setBlock(Blocks.getSolarPanel(), container);

        container.onUpdate();
        container.onUpdate();

        Assertions.assertEquals(2, tile.getPower());
    }
}
