package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Floor;

import java.util.Optional;

public class BuildFloorSelectable implements Selectable {

    private final Floor floor;

    public BuildFloorSelectable(Floor floor) {
        this.floor = floor;
    }

    @Override
    public String getTitle() {
        return "Build: " + floor.toString() + " Floor";
    }

    @Override
    public String getDesc() {
        return "";
    }

    @Override
    public String[] getDebugInfo() {
            return new String[]{
                    "Requirement: " + floor.getRequiredItem().map(Item::getDisplayName).orElse("None"),
            };
    }

    @Override
    public Optional<String> getHelpInfo() {
        return Optional.empty();
    }
}

