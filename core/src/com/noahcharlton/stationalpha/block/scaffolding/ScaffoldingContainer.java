package com.noahcharlton.stationalpha.block.scaffolding;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

public class ScaffoldingContainer extends BlockContainer {

    private final Block blockToBuild;

    private int tick = 500;

    public ScaffoldingContainer(Tile tile, Block blockToBuild, BlockRotation rotation) {
        super(tile, Blocks.getScaffoldingBlock(), rotation);

        this.blockToBuild = blockToBuild;
    }

    @Override
    public void onUpdate(){
        tick--;

        if(tick < 0){
            finishBuilding();
        }
    }

    void finishBuilding() {
        BlockContainer container = blockToBuild.createContainer(getTile(), getRotation());
        World world = getTile().getWorld();
        int rootX = getTile().getX();
        int rootY = getTile().getY();

        for(int x = rootX; x < rootX + container.getWidth(); x++) {
            for(int y = rootY; y < rootY + container.getHeight(); y++) {
                world.getTileAt(x, y).get().setBlock(blockToBuild, container);
            }
        }
    }

    public int getWidth(){
        if(getRotation() == BlockRotation.NORTH || getRotation() == BlockRotation.SOUTH)
            return blockToBuild.getDimensionedWidth();

        return blockToBuild.getDimensionedHeight();
    }

    public int getHeight(){
        if(getRotation() == BlockRotation.NORTH || getRotation() == BlockRotation.SOUTH)
            return blockToBuild.getDimensionedHeight();

        return blockToBuild.getDimensionedWidth();
    }

    public int getTick() {
        return tick;
    }
}
