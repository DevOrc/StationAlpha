package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.HelpInfo;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DustCollector extends Block {

    @Override
    public String getID() {
        return "dust_collector";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new DustCollectorContainer(tile, this, blockRotation);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("collector.png");
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Collections.singletonList(Item.COPPER.stack(1));
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.DUST_COLLECTION);
    }


    @Override
    public String getDisplayName() {
        return "Dust Collector";
    }

    @Override
    public Optional<String> getHelpInfo() {
        return Optional.of(HelpInfo.get("dust_collector_info"));
    }
}
