package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.function.Predicate;

public class BuildBlockGoal extends Goal {

    private final Block block;

    public BuildBlockGoal(Block block) {
        super("Build " + block.getDisplayName(), "");

        this.block = block;
    }

    @Override
    public void update(World world) {
        for(int x = 0; x < World.WORLD_TILE_SIZE; x++){
            for(int y = 0; y < World.WORLD_TILE_SIZE; y++){
                Tile tile = world.getTileAt(x, y).get();

                if(tile.getBlock().filter(Predicate.isEqual(block)).isPresent()){
                    setCompleted(true);
                    return;
                }
            }
        }
    }
}
