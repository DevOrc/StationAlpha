package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MineJobTests {

    private final World world = new World();
    private MineJob job;
    private Tile rockTile;

    @BeforeEach
    void setUp() {
        rockTile = world.getTileAt(4, 5).get();
        rockTile.setBlock(Blocks.getIce());
        Tile adjacent = world.getTileAt(5, 5).get();
        adjacent.setBlock(null);

        job = new MineJob(rockTile, adjacent, world.getInventory());
        job.start();
    }

    @Test
    void onFinishRemoveBlockTest() {
        Assertions.assertTrue(rockTile.getBlock().isPresent());
        job.finish();

        Assertions.assertFalse(rockTile.getBlock().isPresent());
    }

    @Test
    void onFinishAddRockToInventory() {
        job.finish();

        Assertions.assertEquals(3, world.getInventory().getAmountForItem(Item.SPACE_ROCK));
    }
}
