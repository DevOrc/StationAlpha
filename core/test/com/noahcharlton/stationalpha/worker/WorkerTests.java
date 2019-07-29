package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerTests {

    private final TestWorker worker = new TestWorker();

    @Test
    void setPixelXBelowZeroTrimTest() {
        worker.setPixelX(-10);

        Assertions.assertEquals(0, worker.getPixelX());
    }

    @Test
    void setPixelXBiggerThanWorldTrimTest() {
        worker.setPixelX(123456789);

        Assertions.assertEquals(4799, worker.getPixelX());
    }

    @Test
    void setPixelYBelowZeroTrimTest() {
        worker.setPixelY(-553);

        Assertions.assertEquals(0, worker.getPixelY());
    }

    @Test
    void setPixelYBiggerThanWorldTrimTest() {
        worker.setPixelY(987654321);

        Assertions.assertEquals(4799, worker.getPixelY());
    }

    @Test
    void getTileOnBasicTest() {
        worker.setPixelX(10);
        worker.setPixelY(30);

        Assertions.assertEquals(new Tile(0, 0, new World()), worker.getTileOn());
    }

    @Test
    void getTileOnEdgeTest() {
        worker.setPixelX(31);
        worker.setPixelY(31);

        Assertions.assertEquals(new Tile(0, 0, new World()), worker.getTileOn());
    }

    @Test
    void getTileOnHighTest() {
        worker.setPixelX(270);
        worker.setPixelY(577);

        Assertions.assertEquals(new Tile(8, 18, new World()), worker.getTileOn());
    }

    @Test
    void getTileOnOutsideWorldThrowsTest() {
        Assertions.assertThrows(GdxRuntimeException.class,  () -> {
            worker.setPixelXNoCheck(-100);
            worker.setPixelYNoCheck(-100);

            worker.getTileOn();
        });
    }

    @Test
    void workerDieBasicTest() {
        worker.die("");

        Assertions.assertTrue(worker.isDead());
    }

    @Test
    void workerDefaultRoleTest() {
        Assertions.assertArrayEquals(new WorkerRole[]{WorkerRole.GENERAL}, worker.getRoles().toArray());
    }

    @Test
    void cannotAddRoleMoreThanOnceTest() {
        worker.getRoles().clear();

        worker.addRole(WorkerRole.GENERAL);
        worker.addRole(WorkerRole.GENERAL);

        Assertions.assertEquals(1, worker.getRoles().size());
    }

    @Test
    void removeRoleTest() {
        worker.getRoles().clear();

        worker.addRole(WorkerRole.GENERAL);
        worker.removeRole(WorkerRole.GENERAL);

        Assertions.assertEquals(0, worker.getRoles().size());
    }

    @Test
    void jobIsCancelledOnDeath() {
        TestJob job = new TestJob();
        worker.getAi().getJobManager().setCurrentJob(job);

        job.start();
        worker.die("Test");

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    void workerHasNoJobOnDeath() {
        TestJob job = new TestJob();
        worker.getAi().getJobManager().setCurrentJob(job);

        worker.die("Test");

        Assertions.assertFalse(worker.getAi().getJobManager().getCurrentJob().isPresent());
    }
}