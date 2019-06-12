package com.noahcharlton.stationalpha.block.tree;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Random;

public class TreeContainer extends BlockContainer {

    private static final int BASE_GROWTH_TIME = 6000;
    private static final int RANDOM_GROWTH_BOUND = 4000;

    private static final Random random = new Random();

    private int tick;

    public TreeContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);

        tick = BASE_GROWTH_TIME + random.nextInt(RANDOM_GROWTH_BOUND);
    }

    @Override
    public void onUpdate() {
        if(tick > 0)
            tick--;

        if(DebugKeys.isDebugPressed(DebugKeys.MAGICAL_GROWTH)){
            tick = 0;
        }
    }

    public boolean isSapling(){
        return tick > 0;
    }

    void setTick(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }
}
