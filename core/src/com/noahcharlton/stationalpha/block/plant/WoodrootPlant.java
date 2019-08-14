package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;

import java.util.Collections;
import java.util.List;

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
    public Item getProduct() {
        return Item.WOODROOT;
    }
}
