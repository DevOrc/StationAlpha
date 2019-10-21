package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.engine.input.BuildFloor;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.world.Floor;

import java.util.Arrays;

public class FloorMenu extends BuildBarListMenu<Floor> {

    public FloorMenu() {
        super(Arrays.asList(Floor.values()));
    }

    @Override
    protected Runnable createRunnable(Floor floor) {
        return () -> {
            InputHandler.getInstance().setBuildAction(new BuildFloor(floor));
            InputHandler.getInstance().setCurrentlySelected(new BuildFloorSelectable(floor));
        };
    }

    @Override
    public String getName() {
        return "Floors";
    }

    @Override
    public InGameIcon getIcon() {
        return InGameIcon.FLOOR;
    }
}
