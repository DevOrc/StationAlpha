package com.noahcharlton.stationalpha.engine.input.mine;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MineJobTests extends JobTests {

    private World world;
    private MineJob job;
    private Tile rockTile;

    @Test
    void throwsExceptionIfContainerNotMineable() {
        rockTile.setBlock(Blocks.getIce(), new BlockContainer(rockTile, Blocks.getWall(), BlockRotation.NORTH));

        Assertions.assertThrows(GdxRuntimeException.class, () -> job.setBlockMineState(rockTile));
    }

    @Test
    void setStateIfNoContainerFailsTest() {
        Tile randomTile = world.getTileAt(5, 5).get();

        Assertions.assertThrows(GdxRuntimeException.class, () -> job.setBlockMineState(randomTile));
    }

    @Test
    void onFinishRemoveBlockTest() {
        job.start();
        rockTile.setBlock(Blocks.getIce());
        Assertions.assertTrue(rockTile.getBlock().isPresent());
        job.finish();

        Assertions.assertFalse(rockTile.getBlock().isPresent());
    }

    @Test
    void onFinishAddItemsToInventory() {
        rockTile.setBlock(Blocks.getIce());
        job.start();
        job.finish();

        Assertions.assertEquals(3, world.getInventory().getAmountForItem(Item.SPACE_ROCK));
        Assertions.assertEquals(3, world.getInventory().getAmountForItem(Item.STEEL));
    }

    @Override
    public Job getJob() {
        world = new World();
        rockTile = world.getTileAt(4, 5).get();
        rockTile.setBlock(Blocks.getIce());
        Tile adjacent = world.getTileAt(5, 5).get();
        adjacent.setBlock(null);

        job = new MineJob(rockTile, adjacent, Arrays.asList(Item.SPACE_ROCK, Item.STEEL), 3, WorkerRole.GENERAL);

        return job;
    }
}
