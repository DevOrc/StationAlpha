package com.noahcharlton.stationalpha.block.mineable;

import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.mine.MineJob;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Collections;

public class MineableBlockContainerTests extends LibGdxTest {

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

    @Test
    void onSaveHasJobTest() {
        StringWriter writer = new StringWriter();
        MineJob job = new MineJob(tile, tile.getOpenAdjecent().get(), Collections.emptyList(), 0);
        container.setCurrentJob(job);

        container.onSave(new QuietXmlWriter(writer));

        String expected = "<MarkedForMining>true</MarkedForMining>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void onSaveHasNoJobTest() {
        StringWriter writer = new StringWriter();

        container.onSave(new QuietXmlWriter(writer));

        String expected = "<MarkedForMining>false</MarkedForMining>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void onLoadAddJobTest() {
        JobQueue.getInstance().getJobQueue(WorkerRole.GENERAL).clear();
        String xml = "<MarkedForMining>true</MarkedForMining>";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(1, JobQueue.getInstance().getJobQueue(WorkerRole.GENERAL).size());
    }

    @Test
    void onLoadNoJobTest() {
        JobQueue.getInstance().getJobQueue(WorkerRole.GENERAL).clear();
        String xml = "<MarkedForMining>false</MarkedForMining>";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(0, JobQueue.getInstance().getJobQueue(WorkerRole.GENERAL).size());
    }
}
