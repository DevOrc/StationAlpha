package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class DustCollector extends Block {

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new DustCollectorContainer(tile, this, blockRotation);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("collector.png");
    }

    @Override
    public Optional<Item> getRequiredItem() {
        return Optional.of(Item.COPPER);
    }

    @Override
    public String getDisplayName() {
        return "Dust Collector";
    }
}
