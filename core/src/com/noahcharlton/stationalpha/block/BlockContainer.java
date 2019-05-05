package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Tile;

public class BlockContainer {

    private final Tile tile;
    private final Block block;
    private final BlockRotation rotation;

    public BlockContainer(Tile tile, Block block) {
        this(tile, block, BlockRotation.NORTH);
    }

    public BlockContainer(Tile tile, Block block, BlockRotation rotation) {
        this.tile = tile;
        this.block = block;
        this.rotation = rotation;
    }

    public void onUpdate(){}

    public void onBlockUpdate(){}

    public BlockRotation getRotation() {
        return rotation;
    }

    public final int getWidth(){
        if(rotation == BlockRotation.NORTH || rotation == BlockRotation.SOUTH) return block.getDimensionedWidth();

        return block.getDimensionedHeight();
    }

    public final int getHeight(){
        if(rotation == BlockRotation.NORTH || rotation == BlockRotation.SOUTH) return block.getDimensionedHeight();

        return block.getDimensionedWidth();
    }

    public Block getBlock() {
        return block;
    }

    public Tile getTile() {
        return tile;
    }
}
