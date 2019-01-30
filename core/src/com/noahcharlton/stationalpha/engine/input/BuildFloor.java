package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Objects;

public class BuildFloor implements BuildAction{

    private final Floor floor;

    public BuildFloor(Floor floor) {
        Objects.requireNonNull(floor, "floor cannot be null!");

        this.floor = floor;
    }

    @Override
    public void onClick(Tile tile, int button) {
        if(button == Input.Buttons.LEFT){
            tile.setFloor(floor);
        }

        if(button == Input.Buttons.RIGHT){
            if(tile.getFloor().isPresent())
                tile.setFloor(null);
            else
                InputHandler.getInstance().setBuildAction(null);
        }
    }

    @Override
    public String getName() {
        return "Building Floors!";
    }
}
