package com.noahcharlton.stationalpha.block.tree;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreeContainerTests {

    private final World world = new World();
    private final TreeContainer container = new TreeContainer(world.getTileAt(0,0).get(),
            Blocks.getTreeBlock(), BlockRotation.NORTH);

    @Test
    void isSaplingTickIsOneTest() {
        container.setTick(1);

        Assertions.assertTrue(container.isSapling());
    }

    @Test
    void isSaplingTickGreaterThanOneTest(){
        container.setTick(14);

        Assertions.assertTrue(container.isSapling());
    }

    @Test
    void isSaplingTickIsZeroTest(){
        container.setTick(0);

        Assertions.assertFalse(container.isSapling());
    }

    @Test
    void updateWillNotSetTickBelowZeroTest(){
        container.setTick(0);
        container.onUpdate();

        Assertions.assertEquals(0, container.getTick());
    }
}
