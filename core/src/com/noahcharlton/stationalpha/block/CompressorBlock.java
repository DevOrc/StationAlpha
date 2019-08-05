package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.HelpInfo;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CompressorBlock extends Block {

    public CompressorBlock() {
        setOpaque(false);
    }

    @Override
    public String getID() {
        return "compressor";
    }

    @Override
    public String getDisplayName() {
        return "Compressor";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("compressor.png");
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation rotation) {
        return new CompressorContainer(tile, rotation);
    }

    @Override
    public Optional<String> getHelpInfo() {
        return Optional.of(HelpInfo.COMPRESSOR_INFO);
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Collections.singletonList(Item.COPPER.stack(1));
    }
}
class CompressorContainer extends BlockContainer{

    public CompressorContainer(Tile tile, BlockRotation rotation) {
        super(tile, Blocks.getCompressor(), rotation);
    }

    @Override
    public void onUpdate() {
        getTile().changeOxygenLevel(3f);
    }
}
