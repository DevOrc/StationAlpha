package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.worker.job.JobRenderer;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class WorkerRendererTests {

    @Test
    void assertWorkerRolesLoadOnInit() {
        WorkerRole.loadTextures();

        // Will throw exception if not loaded
        WorkerRenderer.checkRendererInitialized();
    }

    @Test
    void getRenderableRoleNoRolesTest() {
        Worker worker = Worker.create(new World());

        worker.getRoles().clear();

        Assertions.assertEquals(WorkerRole.GENERAL, WorkerRenderer.getRenderableRole(worker));
    }

    @Test
    void getRenderableRoleSingleRoleTest() {
        Worker worker = Worker.create(new World());

        worker.getRoles().clear();
        worker.addRole(WorkerRole.ENGINEER);

        Assertions.assertEquals(WorkerRole.ENGINEER, WorkerRenderer.getRenderableRole(worker));
    }


    @Test
    void getRenderableRoleMultipleRoleTest() {
        Worker worker = Worker.create(new World());

        worker.getRoles().clear();
        worker.addRole(WorkerRole.GENERAL);
        worker.addRole(WorkerRole.GARDENER);

        Assertions.assertEquals(WorkerRole.GENERAL, WorkerRenderer.getRenderableRole(worker));
    }

    @Test
    void usesCustomRendererWhenAvailableTest() {
        CustomRendererTestJob job = new CustomRendererTestJob();
        Worker worker = new TestWorker();
        worker.getAi().getJobManager().setCurrentJob(job);

        WorkerRenderer.render(null, worker);

        Assertions.assertTrue(job.hasBeenRendered());
    }
}
class CustomRendererTestJob extends TestJob {

    private boolean rendered = false;

    @Override
    public Optional<JobRenderer> createRenderer() {
        return Optional.of((batch, job) -> rendered = true);
    }

    public boolean hasBeenRendered() {
        return rendered;
    }
}
