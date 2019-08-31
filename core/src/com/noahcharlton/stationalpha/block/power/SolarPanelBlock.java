package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SolarPanelBlock extends Block {

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("solar_panel.png");
    }

    @Override
    public String getDisplayName() {
        return "Solar Panel";
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Arrays.asList(Item.STEEL.stack(10), Item.COPPER.stack(2));
    }

    @Override
    public String getID() {
        return "solar_panel";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new PowerProducerContainer(tile, this, blockRotation, 1);
    }
}

