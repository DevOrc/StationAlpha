package com.noahcharlton.stationalpha.worker.job;

import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TickBasedJobTests extends JobTests{

    @Override
    public Job getJob() {
        return new TickBasedJob(new Tile(0,0, new World()), 5);
    }

    @Test
    void basicTickBasedJobTest() {
        TickBasedJob job = new TickBasedJob(new Tile(0,0, new World()), 5);
        job.start();

        for(int i = 0; i < 5; i++){
            job.update();
        }

        Assertions.assertEquals(Job.JobStage.FINISHED, job.getStage());
    }

    @Test
    void jobCancelResetsTick() {
        TickBasedJob job = new TickBasedJob(new Tile(0,0, new World()), 5);
        job.start();
        job.update();

        Assertions.assertEquals(1, job.getTick());

        job.cancel();
        job.start();

        Assertions.assertEquals(0, job.getTick());
    }
}
