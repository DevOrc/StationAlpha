package com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu;

import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerRoleBoxTests {

    private final Worker worker = Worker.create(new World());

    @Test
    void basicAddRoleTest() {
        worker.getRoles().clear();

        WorkerRoleBox box = new WorkerRoleBox(worker, WorkerRole.ENGINEER);
        box.onClick();

        Assertions.assertEquals(WorkerRole.ENGINEER, worker.getRoles().iterator().next());
    }

    @Test
    void basicRemoveRoleTest() {
        worker.getRoles().clear();
        worker.addRole(WorkerRole.GARDENER);

        WorkerRoleBox box = new WorkerRoleBox(worker, WorkerRole.GARDENER);
        box.onClick();

        Assertions.assertEquals(0, worker.getRoles().size());
    }
}
