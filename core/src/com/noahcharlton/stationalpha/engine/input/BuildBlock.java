package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Objects;

public class BuildBlock implements BuildAction{

    private final Block block;

    public BuildBlock(Block block) {
        Objects.requireNonNull(block, "block cannot be null!");

        this.block = block;
    }

    @Override
    public void onClick(Tile tile, int button) {
        if(button == Input.Buttons.LEFT){
            tile.setBlock(block);
        }

        if(button == Input.Buttons.RIGHT){
            if(tile.getBlock().isPresent())
                tile.setBlock(null);
            else
                InputHandler.getInstance().setBuildAction(null);
        }
    }

    @Override
    public String getName() {
        return "Building Block!";
    }
}
