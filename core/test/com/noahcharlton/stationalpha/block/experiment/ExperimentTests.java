package com.noahcharlton.stationalpha.block.experiment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExperimentTests {

    private final Experiment experiment = new Experiment(1);

    @Test
    void cannotHaveProgressHigherThanLengthTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Experiment("", 5, 3));
    }

    @Test
    void experimentDefaultStageTest() {
        Assertions.assertEquals(Experiment.Stage.PRE_START, experiment.getStage());
    }

    @Test
    void experimentDefaultProgressTest() {
        Assertions.assertEquals(experiment.getProgress(), 0);
    }

    @Test
    void experimentStartBasicTest() {
        experiment.start();

        Assertions.assertEquals(Experiment.Stage.IN_PROGRESS, experiment.getStage());
    }

    @Test
    void cannotStartExperimentMoreThanOnceTest() {
        experiment.start();

        Assertions.assertThrows(UnsupportedOperationException.class, experiment::start);
    }

    @Test
    void onTickBasicTest() {
        experiment.start();
        experiment.onTick();

        Assertions.assertEquals(1, experiment.getProgress());
    }

    @Test
    void onTickDoesNotTickBeforeStart() {
        experiment.onTick();

        Assertions.assertEquals(0, experiment.getProgress());
    }

    @Test
    void onTickDoesNotTickPastLength() {
        experiment.start();

        for(int i = 0; i < experiment.getLength() + 3; i++){
            experiment.onTick();
        }

        Assertions.assertEquals(experiment.getLength(), experiment.getProgress());
    }

    @Test
    void automaticallyFinishesAfterReachingLengthTest() {
        experiment.start();

        for(int i = 0; i < experiment.getLength(); i++){
            experiment.onTick();
        }

        Assertions.assertEquals(Experiment.Stage.FINISHED, experiment.getStage());
    }
}
