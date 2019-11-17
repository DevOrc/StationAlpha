package com.noahcharlton.stationalpha.block.bed;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BedBlock extends Block {

    public BedBlock() {
        setOpaque(false);
    }

    @Override
    public String getID() {
        return "bed";
    }

    @Override
    public String getDisplayName() {
        return "Bed";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("bed.png");
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Arrays.asList(Item.STEEL.stack(4), Item.WOOD.stack(4));
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.BASIC_MATERIALS);
    }


    @Override
    public int getDimensionedHeight() {
        return 1;
    }

    @Override
    public int getDimensionedWidth() {
        return 2;
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new BedContainer(tile, this, blockRotation);
    }

    @Override
    protected BlockRenderer createRenderer() {
        return new BedRenderer(this);
    }
}
