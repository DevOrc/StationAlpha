package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Tile;

public class BlockContainer {

    private final Tile tile;
    private final Block block;

    public BlockContainer(Tile tile, Block block) {
        this.tile = tile;
        this.block = block;
    }

    public void onUpdate(){}

    public void onBlockUpdate(){}

    public final int getWidth(){
        return block.getDimensionedWidth();
    }

    public final int getHeight(){
        return block.getDimensionedHeight();
    }

    public Block getBlock() {
        return block;
    }

    public Tile getTile() {
        return tile;
    }
}
