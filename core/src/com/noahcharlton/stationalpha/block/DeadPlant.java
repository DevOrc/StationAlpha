package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.HelpInfo;

import java.util.Optional;

public class DeadPlant extends Block{

    public DeadPlant() {
        setPassable(true);
        setOpaque(false);
    }

    @Override
    public String getID() {
        return "dead_plant";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("dead_plant.png");
    }

    @Override
    public Optional<String> getHelpInfo() {
        return Optional.of(HelpInfo.get("dead_plant_info"));
    }

    @Override
    public String getDisplayName() {
        return "Dead Plant";
    }
}
