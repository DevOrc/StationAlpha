package com.noahcharlton.stationalpha.block.experiment;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ExperimentContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(3, 3).get();
    private final ExperimentContainer container = new ExperimentContainer(tile,
            Blocks.getAntiGravityExperiment(), BlockRotation.NORTH);

    @Test
    void containerDefaultsToNoExperimentTest() {
        Assertions.assertEquals(Optional.empty(), container.getExperiment());
    }

    @Test
    void noExperimentDebugInfoTest() {
        String lastInfo = container.getDebugInfo()[2];

        Assertions.assertEquals("Experiment: None", lastInfo);
    }

    @Test
    void experimentDebugInfoTest() {
        container.createExperiment();
        container.startExperiment();
        container.onUpdate();

        String lastInfo = container.getDebugInfo()[2];

        Assertions.assertEquals("Experiment: ExpName (1 / 250)", lastInfo);
    }

    @Test
    void createExperimentBasicTest() {
        container.createExperiment();

        Assertions.assertTrue(container.getExperiment().isPresent());
    }

    @Test
    void startExperimentBasicTest() {
        container.createExperiment();
        container.startExperiment();

        Assertions.assertEquals(Experiment.Stage.IN_PROGRESS, container.getExperiment().get().getStage());
    }

    @Test
    void onUpdateExperimentTickTest() {
        container.createExperiment();
        container.startExperiment();
        container.onUpdate();

        Assertions.assertEquals(1, container.getExperiment().get().getProgress());
    }

    @Test
    void onUpdateExperimentGetsDeletedTest() {
        container.createExperiment();
        container.startExperiment();
        int length = container.getExperiment().get().getLength() + 10;

        for(int i = 0; i < length; i++){
            container.onUpdate();
        }

        Assertions.assertEquals(Optional.empty(), container.getExperiment());
    }
}
