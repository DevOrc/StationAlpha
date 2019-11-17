package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Arrays;
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
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.SYNTHESIZER);
    }


    @Override
    public List<ItemStack> getRequirements() {
        return Arrays.asList(Item.UNOBTAINIUM.stack(1), Item.COPPER.stack(5), Item.STEEL.stack(15));
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
