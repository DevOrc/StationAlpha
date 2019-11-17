package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.science.ResearchItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WoodrootPlant extends Plant {

    public WoodrootPlant() {
        super(4, 4800);
    }

    @Override
    public String getPlantName() {
        return "woodroot";
    }

    @Override
    public String getDisplayName() {
        return "Woodroot";
    }

    @Override
    public int getAmountPerHarvest() {
        return 10;
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Collections.singletonList(Item.WOODROOT.stack(1));
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.COMPLEX_PLANTS);
    }


    @Override
    public Item getProduct() {
        return Item.WOODROOT;
    }
}
