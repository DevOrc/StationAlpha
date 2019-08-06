package com.noahcharlton.stationalpha.world.load;

import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerLoaderTests {

    @Test
    void parseRolesClearsPreviousRolesTests() {
        TestWorker worker = new TestWorker();
        worker.getRoles().add(WorkerRole.ENGINEER);

        WorkerLoader.parseRoles(worker, LoadTestUtils.asChild(""));

        Assertions.assertEquals(0, worker.getRoles().size());
    }

    @Test
    void parseRolesBasicTest() {
        TestWorker worker = new TestWorker();

        WorkerLoader.parseRoles(worker, LoadTestUtils.asChild("<WorkerRole>GARDENER</WorkerRole>"));

        Assertions.assertEquals(WorkerRole.GARDENER, worker.getRoles().iterator().next());
    }

    @Test
    void parseRolesMultipleTest() {
        TestWorker worker = new TestWorker();

        String xml = "<WorkerRole>GARDENER</WorkerRole><WorkerRole>ENGINEER</WorkerRole>";
        WorkerLoader.parseRoles(worker, LoadTestUtils.asChild(xml));

        Assertions.assertEquals(2, worker.getRoles().size());
    }

    @Test
    void parseNeedsSleepTickTest() {
        TestWorker worker = new TestWorker();

        String xml = "<Needs sleep=\"4695\" food=\"105\"/>";
        WorkerLoader.parseNeeds(worker, LoadTestUtils.asChild(xml));

        Assertions.assertEquals(4695, worker.getAi().getNeedsManager().getSleepTick());
    }

    @Test
    void parseNeedsFoodTickTest() {
        TestWorker worker = new TestWorker();

        String xml = "<Needs sleep=\"234\" food=\"765\"/>";
        WorkerLoader.parseNeeds(worker, LoadTestUtils.asChild(xml));

        Assertions.assertEquals(765, worker.getAi().getNeedsManager().getFoodTick());
    }

    @Test
    void createWorkerNameTest() {
        World world = new World();

        String xml = "<Worker name=\"Olivia Bell\" pixelX=\"2304\" pixelY=\"2272\">"
                + "<Needs sleep=\"6342\" food=\"53\"/></Worker>";
        Worker worker = WorkerLoader.createWorker(LoadTestUtils.asElement(xml), world);

        Assertions.assertEquals("Olivia Bell", worker.getName());
    }

    @Test
    void createWorkerPixelXTest() {
        World world = new World();

        String xml = "<Worker name=\"Olivia  Bell\" pixelX=\"543\" pixelY=\"3421\">"
                + "<Needs sleep=\"1245\" food=\"8654\"/></Worker>";
        Worker worker = WorkerLoader.createWorker(LoadTestUtils.asElement(xml), world);

        Assertions.assertEquals(543, worker.getPixelX());
    }

    @Test
    void createWorkerPixelYTest() {
        World world = new World();

        String xml = "<Worker name=\"Olivia  Bell\" pixelX=\"5423\" pixelY=\"4532\">"
                + "<Needs sleep=\"6353\" food=\"7332\"/></Worker>";
        Worker worker = WorkerLoader.createWorker(LoadTestUtils.asElement(xml), world);

        Assertions.assertEquals(4532, worker.getPixelY());
    }
}
