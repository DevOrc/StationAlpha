package com.noahcharlton.stationalpha.block;

import java.util.Optional;

public class DeadPlant extends Block{

    public DeadPlant() {
        setPassable(true);
        setOpaque(false);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("dead_plant.png");
    }

    @Override
    public String getDisplayName() {
        return "Dead Plant";
    }
}
