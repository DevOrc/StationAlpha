package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

public class PowerProducerContainer extends PoweredBlockContainer {

    private final int powerPerTick;

    public PowerProducerContainer(Tile tile, Block block, BlockRotation rotation, int powerPerTick) {
        super(tile, block, rotation);

        this.powerPerTick = powerPerTick;
    }

    @Override
    public int getPowerPerTick() {
        return 0;
    }

    @Override
    public void onUpdate() {
        getTile().getWorld().getPowerNetwork().changePower(powerPerTick);
    }
}
