package com.noahcharlton.stationalpha.block.scaffolding;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScaffoldingJobTests extends JobTests {

    private World world;
    private Tile tile;
    private ScaffoldingContainer container;

    @Test
    void jobTargetBlockResetsJobTest() {
        Job job = getJob();
        job.getTarget().setBlock(Blocks.getWall());

        container.onUpdate();

        Assertions.assertFalse(container.getCurrentJob().isPresent());
    }

    @Test
    void onFinishPlacesBlockTest() {
        getJob().finish();

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Override
    public Job getJob() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        buildBlock.setUseScaffolding(true);

        world = new World();
        world.getInventory().fillAllItems();
        tile = world.getTileAt(0, 0).get();
        buildBlock.onClick(tile, Input.Buttons.LEFT);

        container = (ScaffoldingContainer) tile.getContainer().get();
        container.onUpdate();

        return container.getCurrentJob().get();
    }
}
