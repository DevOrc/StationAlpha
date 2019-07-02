package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;

public class DustCollectorContainer extends BlockContainer {

    private static final int TIME_PER_DUST = 7200;

    private int tick = TIME_PER_DUST;

    public DustCollectorContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    @Override
    public void onUpdate() {
        tick--;

        if(tick < 0){
            tick = TIME_PER_DUST;

            getTile().getWorld().getInventory().changeAmountForItem(Item.SPACE_DUST, 1);
        }
    }
}
