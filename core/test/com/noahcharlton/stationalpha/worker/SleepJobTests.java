package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class SleepJobTests extends JobTests {

    private World world;
    private Worker worker;
    private SleepJob sleepJob;

    @Test
    void finishesAfter360TicksTest() {
        for(int i = 0; i < 360; i++){
            sleepJob.update();
        }

        Assertions.assertEquals(Job.JobStage.FINISHED, sleepJob.getStage());
    }

    @Test
    void onFinishResetsSleepTick() {
        worker.getAi().getNeedsManager().update();

        Assumptions.assumeTrue(worker.getAi().getNeedsManager().getSleepTick()
                != WorkerNeedsManager.SLEEP_RESET);

        sleepJob.finish();

        Assertions.assertEquals(WorkerNeedsManager.SLEEP_RESET, worker.getAi().getNeedsManager().getSleepTick());
    }

    @Override
    public Job getJob() {
        world = new World();
        new BuildBlock(Blocks.getBedBlock()).onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);

        worker = new TestWorker();
        sleepJob = new SleepJob(world.getTileAt(0, 0).get().getOpenAdjecent().get(), worker);

        return sleepJob;
    }
}
