package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Arrays;
import java.util.Optional;

public class BlockContainer implements Selectable {

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

    public void onDestroy(){}


    @Override
    public String getTitle() {
        return block.getDisplayName();
    }

    @Override
    public String getDesc() {
        return "";
    }

    @Override
    public Optional<String> getHelpInfo() {
        return block.getHelpInfo();
    }

    @Override
    public String[] getDebugInfo() {
        return combineDebugInfo();
    }

    protected String[] combineDebugInfo(String... data){
        String[] original = tile.getDebugInfo();
        String[] array = Arrays.copyOf(original, original.length + data.length);

        for(int i = 0; i < data.length; i++){
            array[i + original.length] = data[i];
        }

        return array;
    }

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
