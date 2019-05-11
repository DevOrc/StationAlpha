package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkbenchJobTests extends JobTests {

    private final World world;
    private final WorkbenchJob job;

    public WorkbenchJobTests() {
        world = new World();

        Tile tile = world.getTileAt(2, 2).get();
        BlockContainer container = new WorkbenchContainer(tile, Blocks.getWorkbench(), BlockRotation.NORTH);
        tile.setBlock(Blocks.getWorkbench(), container);

        job = new WorkbenchJob(tile.getOpenAdjecent().get());
    }

    @Test
    void onJobFinishAddInventoryTest() {
        job.start();
        job.finish();

        Assertions.assertEquals(1, world.getInventory().getAmountForItem(Item.TEST_ITEM));
    }

    @Override
    public Job getJob() {
        return job;
    }
}
