package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

public abstract class PoweredBlockContainer extends BlockContainer implements PoweredContainer{

    public PoweredBlockContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    public abstract int getPowerPerTick();

    public boolean hasPower(){
        throw new UnsupportedOperationException();
    }

    public boolean usePower(){
        throw new UnsupportedOperationException();
    }

    int removePower(int amount, Tile tile){
       return 0;
    }
}
