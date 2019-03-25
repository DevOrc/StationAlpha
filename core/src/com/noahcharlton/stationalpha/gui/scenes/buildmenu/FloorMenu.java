package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.input.BuildFloor;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.world.Floor;

import java.util.Arrays;

public class FloorMenu extends BuildBarMenu<Floor> {

    public FloorMenu() {
        super(Arrays.asList(Floor.values()));
    }

    @Override
    protected Runnable createRunnable(Floor item) {
        return () -> InputHandler.getInstance().setBuildAction(new BuildFloor(item));
    }

    @Override
    public String getName() {
        return "Floors";
    }
}
