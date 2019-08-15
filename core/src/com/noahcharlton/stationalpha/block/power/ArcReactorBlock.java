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

public class ArcReactorBlock extends Block{

    private static final int POWER_PER_TICK = 50;

    @Override
    public List<ItemStack> getRequirements() {
        return Arrays.asList(Item.STEEL.stack(15), Item.UNOBTAINIUM.stack(5), Item.POWER_INGOT.stack(5));
    }

    @Override
    public String getDisplayName() {
        return "Arc Reactor";
    }

    @Override
    public int getDimensionedWidth() {
        return 2;
    }

    @Override
    public int getDimensionedHeight() {
        return 2;
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("arc_reactor.png");
    }

    @Override
    public String getID() {
        return "arc_reactor";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new PowerProducerContainer(tile, this, blockRotation, POWER_PER_TICK);
    }
}
