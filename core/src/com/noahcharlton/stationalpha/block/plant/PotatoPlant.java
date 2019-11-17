package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.science.ResearchItem;

import java.util.Optional;

public class PotatoPlant extends Plant{

    public PotatoPlant() {
        super(4, 3000);
    }

    @Override
    public Item getProduct() {
        return Item.POTATO;
    }

    @Override
    public String getPlantName() {
        return "potato";
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.BASIC_GARDENING);
    }


    @Override
    public int getAmountPerHarvest() {
        return 5;
    }

    @Override
    public String getDisplayName() {
        return "Potato Plant";
    }
}
