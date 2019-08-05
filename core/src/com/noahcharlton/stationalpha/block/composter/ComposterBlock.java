package com.noahcharlton.stationalpha.block.composter;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ComposterBlock extends Block {

    public ComposterBlock() {
        setOpaque(false);
    }

    @Override
    public String getID() {
        return "composter";
    }

    @Override
    public String getDisplayName() {
        return "Composter";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new ComposterContainer(tile, this, blockRotation);
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Collections.singletonList(Item.WOOD.stack(1));
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("composter.png");
    }

    @Override
    protected BlockRenderer createRenderer() {
        return new ComposterRenderer(this);
    }
}
