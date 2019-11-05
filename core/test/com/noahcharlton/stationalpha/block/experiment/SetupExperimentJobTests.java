package com.noahcharlton.stationalpha.block.experiment;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetupExperimentJobTests extends JobTests {

    private World world;
    private Tile tile;
    private ExperimentContainer container;

    @Test
    void jobWithoutExperimentThrowsExceptionTest() {
        world = new World();
        tile = world.getTileAt(1, 1).get();
        container = new ExperimentContainer(tile,
                Blocks.getAntiGravityExperiment(), BlockRotation.NORTH);

        Assertions.assertThrows(IllegalStateException.class,
                () -> new SetupExperimentJob(tile, container));
    }

    @Test
    void onFinishStartsExperimentTest() {
        job.finish();

        Assertions.assertEquals(Experiment.Stage.IN_PROGRESS, container.getExperiment().get().getStage());
    }

    @Override
    public Job getJob() {
        world = new World();
        tile = world.getTileAt(1, 1).get();
        container = new ExperimentContainer(tile,
                Blocks.getAntiGravityExperiment(), BlockRotation.NORTH);
        container.createExperiment();

        return new SetupExperimentJob(tile, container);
    }
}
