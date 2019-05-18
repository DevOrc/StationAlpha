package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
