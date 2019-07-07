package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class CompressorBlock extends Block {

    public CompressorBlock() {
        setOpaque(false);
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
