package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

public class PowerProducerContainer extends BlockContainer implements PoweredContainer {

    private final int powerPerTick;

    public PowerProducerContainer(Tile tile, Block block, BlockRotation rotation, int powerPerTick) {
        super(tile, block, rotation);

        this.powerPerTick = powerPerTick;
    }

    @Override
    public void onUpdate() {
        getTile().setPower(getTile().getPower() + powerPerTick);
    }
}
