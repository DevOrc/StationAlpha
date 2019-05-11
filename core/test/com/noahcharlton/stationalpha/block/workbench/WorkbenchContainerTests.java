package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkbenchContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(1, 1).get();
    private final WorkbenchContainer container = new WorkbenchContainer(tile, Blocks.getWorkbench(), BlockRotation.NORTH);

    @Test
    void onDestroyCancelJobTest() {
        world.getInventory().changeAmountForItem(Item.SPACE_ROCK, 1);
        container.createJob();
        container.onDestroy();

        Assertions.assertTrue(container.getJob().get().getStage() == Job.JobStage.PRE_START);
        Assertions.assertFalse(container.getJob().get().getAssignedWorker().isPresent());
    }

    @Test
    void createJobNoRockReturnsFalse() {
        Assertions.assertFalse(container.createJob());
    }

    @Test
    void createJobNoSpaceReturnsFalse() {
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        Assertions.assertFalse(container.createJob());
    }

    @Test
    void createJobBasicTest() {
        world.getInventory().setAmountForItem(Item.SPACE_ROCK, 1);

        Assertions.assertTrue(container.createJob());
    }

    @Test
    void createJobDecreaseRockTest() {
        world.getInventory().setAmountForItem(Item.SPACE_ROCK, 1);
        container.createJob();

        Assertions.assertEquals(0, world.getInventory().getAmountForItem(Item.SPACE_ROCK));
    }

    @Test
    void emptyJobWhenFinished() {
        world.getInventory().setAmountForItem(Item.SPACE_ROCK, 1);
        container.createJob();

        container.getJob().get().finish();
        container.onUpdate();

        Assertions.assertFalse(container.getJob().isPresent());
    }
}
