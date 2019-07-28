package com.noahcharlton.stationalpha.block.mineable;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.mine.MineJob;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class MineableBlockContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private MineableBlockContainer container;

    @BeforeEach
    void setUp() {
        tile.setBlock(Blocks.getIce());
        container = (MineableBlockContainer) tile.getContainer().get();
    }

    @Test
    void createMineJobSetsContainerJobTest() {
        MineJob job = new MineJob(tile, tile.getOpenAdjecent().get(), Collections.emptyList(), 0);

        Assertions.assertSame(job, container.getCurrentJob().get());
    }

    @Test
    void cancelJobIfIceDestroyedBeforeCompleted() {
        MineJob job = new MineJob(tile, tile.getOpenAdjecent().get(), Collections.emptyList(), 0);
        job.start();

        container.onDestroy();

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    void doesNotCancelJobIfIceDestroyedAfterCompleted() {
        MineJob job = new MineJob(tile, tile.getOpenAdjecent().get(), Collections.emptyList(), 0);

        job.start();
        job.finish();

        container.onDestroy();

        Assertions.assertEquals(Job.JobStage.FINISHED, job.getStage());
    }
}
