package com.noahcharlton.stationalpha.block.bed;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class BedContainerTests {

    private final World world = new World();
    private BedContainer container;

    @BeforeEach
    void setUp() {
        container = new BedContainer(world.getTileAt(0, 0).get(), Blocks.getBedBlock(), BlockRotation.NORTH);
    }

    @Test
    void defaultsWorkerToEmptyTest() {
        Assertions.assertFalse(container.getWorker().isPresent());
    }

    @Test
    void getsFirstWorkerTest() {
        Worker worker = Worker.create(world);
        world.getWorkers().clear();
        world.getWorkers().add(worker);

        container.onUpdate();

        Assertions.assertSame(worker, container.getWorker().get());
    }

    @Test
    void multipleWorkerOnlyFirstSelectedTest() {
        Worker one = Worker.create(world);
        Worker two = Worker.create(world);

        world.getWorkers().clear();
        world.getWorkers().add(one);
        world.getWorkers().add(two);

        container.onUpdate();

        Assertions.assertFalse(two.getBedroom().isPresent());
    }


    @Test
    void workerHasNoBedAfterDestroy() {
        Worker worker = Worker.create(world);
        world.getWorkers().clear();
        world.getWorkers().add(worker);

        container.onUpdate();
        Assumptions.assumeTrue(worker.getBedroom().isPresent());
        container.onDestroy();

        Assertions.assertFalse(worker.getBedroom().isPresent());
    }

    @Test
    void setWorkerBasicTest() {
        Worker worker = new TestWorker();

        container.setWorker(worker);

        Assertions.assertSame(container, worker.getBedroom().get());
    }

    @Test
    void onSaveNoWorkerTest() {
        StringWriter writer = new StringWriter();
        QuietXmlWriter xmlWriter = new QuietXmlWriter(writer);

        container.onSave(xmlWriter);

        Assertions.assertEquals("", writer.toString());
    }

    @Test
    void onSaveBasicWorkerTest() {
        StringWriter writer = new StringWriter();
        QuietXmlWriter xmlWriter = new QuietXmlWriter(writer);

        container.setWorker(new TestWorker());
        container.onSave(xmlWriter);

        Assertions.assertEquals("<Owner>Test Worker</Owner>\n", writer.toString());
    }

    @Test
    void onLoadNoOwnerSavedTest() {
        container.onLoad(LoadTestUtils.asChild(""));

        Assertions.assertFalse(container.getWorker().isPresent());
    }

    @Test
    void onLoadBasicTest() {
        Worker worker = new Worker("Adam Smith", world);
        world.getWorkers().add(worker);

        container.onLoad(LoadTestUtils.asChild("<Owner>Adam Smith</Owner>"));

        Assertions.assertTrue(container.getWorker().isPresent());
    }

    @Test
    void onLoadWorkerAlreadyHasBedroomTest() {
        Worker worker = new Worker("Adam Smith", world);
        worker.setBedroom(new BedContainer(world.getTileAt(5, 5).get(), Blocks.getBedBlock(), BlockRotation.EAST));
        world.getWorkers().add(worker);

        container.onLoad(LoadTestUtils.asChild("<Owner>Adam Smith</Owner>"));

        Assertions.assertFalse(container.getWorker().isPresent());
    }
}
