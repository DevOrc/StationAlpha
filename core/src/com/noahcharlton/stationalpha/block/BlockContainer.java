package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Tile;

public class BlockContainer {

    private final Tile tile;
    private final Block block;

    public BlockContainer(Tile tile, Block block) {
        this.tile = tile;
        this.block = block;
    }

    public void onBlockUpdate(){}

    public Block getBlock() {
        return block;
    }

    public Tile getTile() {
        return tile;
    }
}
