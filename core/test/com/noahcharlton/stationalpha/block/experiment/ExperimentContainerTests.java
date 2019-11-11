package com.noahcharlton.stationalpha.block.experiment;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueueTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
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

    @Test
    void createExperimentCreatesJobTest() {
        container.createExperiment();

        Assertions.assertTrue(container.getJob().isPresent());
    }

    @Test
    void onDestroyJobNotInQueueTest() {
        container.createExperiment();
        container.onDestroy();

        JobQueueTests.assertNotInJobQueue(container.getJob().get());
    }

    @Test
    void onDestroyJobCancelledTest() {
        container.createExperiment();
        container.startExperiment();
        container.onDestroy();

        Assertions.assertEquals(Job.JobStage.PRE_START, container.getJob().get().getStage());
    }

    @Test
    void onFinishAddScienceTest() {
        container.createExperiment();
        container.startExperiment();
        Experiment experiment = container.getExperiment().get();

        while(experiment.getProgress() != experiment.getLength()){
            container.onUpdate();
        }

        Assertions.assertEquals(experiment.getScienceEarned(), world.getScienceManager().getSciencePoints());
    }

    @Test
    void onFinishExperimentRemovedTest() {
        container.createExperiment();
        container.startExperiment();
        Experiment experiment = container.getExperiment().get();

        while(experiment.getProgress() != experiment.getLength()){
            container.onUpdate();
        }

        Assertions.assertFalse(container.getExperiment().isPresent());
    }

    @Test
    void onSaveNoExperimentTest() {
        StringWriter stringWriter = new StringWriter();
        QuietXmlWriter writer = new QuietXmlWriter(stringWriter);

        container.onSave(writer);

        Assertions.assertEquals("", stringWriter.toString());
    }

    @Test
    void onSaveExperimentBasicTest() {
        StringWriter stringWriter = new StringWriter();
        QuietXmlWriter writer = new QuietXmlWriter(stringWriter);

        container.createExperiment();
        container.onSave(writer);

        String xml = "<Experiment Name=\"ExpName\" Stage=\"PRE_START\" Progress=\"0\" Length=\"250\"/>\n";
        Assertions.assertEquals(xml, stringWriter.toString());
    }

    @Test
    void onSaveExperimentInProgressTest() {
        StringWriter stringWriter = new StringWriter();
        QuietXmlWriter writer = new QuietXmlWriter(stringWriter);

        container.createExperiment();
        container.startExperiment();
        container.onUpdate();

        container.onSave(writer);
        String xml = "<Experiment Name=\"ExpName\" Stage=\"IN_PROGRESS\" Progress=\"1\" Length=\"250\"/>\n";
        Assertions.assertEquals(xml, stringWriter.toString());
    }

    @Test
    void onLoadNoExperimentTest() {
        XmlReader.Element element = LoadTestUtils.asChild("");

        container.onLoad(element);

        Assertions.assertEquals(Optional.empty(), container.getExperiment());
    }

    @Test
    void onLoadExperimentNameTest() {
        String xml = "<Experiment Name=\"FooBar\" Stage=\"IN_PROGRESS\" Progress=\"0\" Length=\"73\"/>\n";
        Experiment actual = onLoadExperimentBase(xml);

        Assertions.assertEquals("FooBar", actual.getName());
    }

    @Test
    void onLoadExperimentProgressTest() {
        String xml = "<Experiment Name=\"Name\" Stage=\"PRE_START\" Progress=\"12\" Length=\"23\"/>\n";
        Experiment actual = onLoadExperimentBase(xml);

        Assertions.assertEquals(12, actual.getProgress());
    }

    @Test
    void onLoadExperimentLengthTest() {
        String xml = "<Experiment Name=\"Name\" Stage=\"PRE_START\" Progress=\"0\" Length=\"125\"/>\n";
        Experiment actual = onLoadExperimentBase(xml);

        Assertions.assertEquals(125, actual.getLength());
    }

    @Test
    void onLoadExperimentPreStartCreatesJobTest() {
        String xml = "<Experiment Name=\"Name\" Stage=\"PRE_START\" Progress=\"0\" Length=\"125\"/>\n";
        onLoadExperimentBase(xml);

        Assertions.assertTrue(container.getJob().isPresent());
    }

    @Test
    void onLoadExperimentInProgressStartsExperiment() {
        String xml = "<Experiment Name=\"Name\" Stage=\"IN_PROGRESS\" Progress=\"0\" Length=\"125\"/>\n";
        onLoadExperimentBase(xml);

        Assertions.assertEquals(Experiment.Stage.IN_PROGRESS, container.getExperiment().get().getStage());
    }

    private Experiment onLoadExperimentBase(String input){
        XmlReader.Element element = LoadTestUtils.asChild(input);

        container.onLoad(element);

        return container.getExperiment().orElse(null);
    }
}
