package com.noahcharlton.stationalpha.block.bed;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
