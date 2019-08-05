package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Synthesizer extends Block {

    @Override
    public String getID() {
        return "synthesizer";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new SynthesizerContainer(tile, this, blockRotation);
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Collections.singletonList(Item.UNOBTAINIUM.stack(1));
    }

    @Override
    public int getDimensionedHeight() {
        return 3;
    }

    @Override
    public int getDimensionedWidth() {
        return 3;
    }

    @Override
    public String getDisplayName() {
        return "Synthesizer";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("synthesizer.png");
    }
}
