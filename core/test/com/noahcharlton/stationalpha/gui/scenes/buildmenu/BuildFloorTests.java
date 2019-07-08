package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.world.Floor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuildFloorTests {

    private final FloorMenu menu = new FloorMenu();

    @Test
    void setSelectableOnClickTest() {
        Runnable runnable = menu.createRunnable(Floor.METAL);

        runnable.run();

        Assertions.assertTrue(InputHandler.getInstance().getCurrentlySelected().get() instanceof BuildFloorSelectable);
    }
}
