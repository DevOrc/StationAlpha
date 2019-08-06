package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Collections;

public class WorkerSaverTests {

    private final World world = new World();
    private final StringWriter stringWriter = new StringWriter();
    private final QuietXmlWriter xmlWriter = new QuietXmlWriter(new XmlWriter(stringWriter));
    private final WorkerSaver saver = new WorkerSaver(world);

    @Test
    void saveWorkerRoleTest() {
        TestWorker worker = new TestWorker();
        worker.getRoles().clear();
        worker.addRole(WorkerRole.ENGINEER);

        saver.saveWorkerRoles(worker, xmlWriter);

        String expected = "<WorkerRole>ENGINEER</WorkerRole>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void saveWorkerHasAllRolesTest() {
        TestWorker worker = new TestWorker();
        Collections.addAll(worker.getRoles(), WorkerRole.values());

        saver.saveWorkerRoles(worker, xmlWriter);
        String output = stringWriter.toString();
        XmlReader.Element element = LoadTestUtils.asChild(output);

        Assertions.assertEquals(WorkerRole.values().length, element.getChildrenByName("WorkerRole").size);
    }

    @Test
    void saveWorkerNeedsBasicTest() {
        TestWorker worker = new TestWorker();
        worker.getAi().getNeedsManager().setSleepTick(1234);
        worker.getAi().getNeedsManager().setFoodTick(5678);

        saver.saveWorkerNeeds(worker, xmlWriter);

        String expected = "<Needs sleep=\"1234\" food=\"5678\"/>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void saveWorkerBasicTest() {
        TestWorker worker = new TestWorker();
        worker.setPixelX(654);
        worker.setPixelY(345);

        saver.saveWorker(xmlWriter, worker);

        String expected = "<Worker name=\"Test Worker\" pixelX=\"654\" pixelY=\"345\">";
        Assertions.assertTrue(stringWriter.toString().startsWith(expected));
    }

    @Test
    void saveHasAllWorkersTest() {
        world.getWorkers().clear();
        Collections.addAll(world.getWorkers(), new TestWorker(), new TestWorker(), new TestWorker());

        saver.save(xmlWriter);
        String output = stringWriter.toString();
        XmlReader.Element element = LoadTestUtils.asElement(output);

        Assertions.assertEquals(3, element.getChildrenByName("Worker").size);
    }
}
