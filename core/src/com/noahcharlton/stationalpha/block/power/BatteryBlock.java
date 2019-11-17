package com.noahcharlton.stationalpha.block.power;

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

public class BatteryBlock extends Block {

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("battery.png");
    }

    @Override
    public String getID() {
        return "battery";
    }

    @Override
    public String getDisplayName() {
        return "Battery";
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Arrays.asList(Item.COPPER.stack(5), Item.STEEL.stack(15));
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.BATTERIES);
    }


    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new BatteryContainer(tile, this, blockRotation, 1500);
    }

    @Override
    protected BlockRenderer createRenderer() {
        return new BatteryRenderer(this);
    }
}
