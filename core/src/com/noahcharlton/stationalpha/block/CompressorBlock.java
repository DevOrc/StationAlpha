package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class CompressorBlock extends Block {

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("compressor.png");
    }

    @Override
    public BlockContainer createContainer(Tile tile) {
        return new CompressorContainer(tile);
    }
}
class CompressorContainer extends BlockContainer{

    public CompressorContainer(Tile tile) {
        super(tile, Blocks.getCompressor());
    }

    @Override
    public void onUpdate() {
        getTile().changeOxygenLevel(3f);
    }
}
