package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.PowerNetwork;
import com.noahcharlton.stationalpha.world.Tile;

public abstract class PoweredBlockContainer extends BlockContainer {

    public PoweredBlockContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    public boolean hasPower(){
        return getTile().getWorld().getPowerNetwork().getPower() >= getPowerPerTick();
    }

    @Override
    public String[] getDebugInfo() {
        PowerNetwork network = getTile().getWorld().getPowerNetwork();

        return super.combineDebugInfo(
                "Network: " + (network.getPower()) + " / " + (network.getCapacity()));
    }

    public abstract int getPowerPerTick();

    public boolean usePower(){
        return getTile().getWorld().getPowerNetwork().usePowerIfAvailable(getPowerPerTick());
    }
}
