package com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu;

import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkerMenuTests {

    @Test
    void addButtonsTest() {
        WorkerMenu workerMenu = new WorkerMenu();
        World world = new World();

        List<Worker> workers = Arrays.asList(Worker.create(world), Worker.create(world));
        workerMenu.createButtons(workers);

        Assertions.assertEquals(3, workerMenu.getSubGuis().size());
    }

    @Test
    void noWorkersOneGuiTest() {
        WorkerMenu menu = new WorkerMenu();

        menu.createButtons(Collections.emptyList());

        Assertions.assertEquals(1, menu.getSubGuis().size());
    }
}
