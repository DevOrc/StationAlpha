package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Multiblock;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Objects;
import java.util.Optional;

public class BuildBlock implements BuildAction {

    private final Block block;

    public BuildBlock(Block block) {
        Objects.requireNonNull(block, "block cannot be null!");

        this.block = block;
    }

    @Override
    public void onClick(Tile tile, int button) {
        if(button == Input.Buttons.LEFT) {
            build(tile, block);
        }

        if(button == Input.Buttons.RIGHT) {
            destroy(tile);
        }
    }

    private void destroy(Tile tile) {
        if(!tile.getBlock().isPresent()) {
            InputHandler.getInstance().setBuildAction(null);
        } else if(tile.getBlock().get() instanceof Multiblock) {
            destroyMultiblock(tile, (Multiblock) tile.getBlock().get());
        } else {
            tile.setBlock(null);
        }
    }

    private void destroyMultiblock(Tile tile, Multiblock block) {
        if(!tile.getContainer().isPresent())
            throw new GdxRuntimeException("Multiblocks must have a container!!");

        Tile root = tile.getContainer().get().getTile();

        int rootX = root.getX();
        int rootY = root.getY();

        for(int x = rootX; x < rootX + block.getWidth(); x++) {
            for(int y = rootY; y < rootY + block.getHeight(); y++) {
                tile.getWorld().getTileAt(x, y).get().setBlock(null);
            }
        }
    }

    private void build(Tile tile, Block block) {
        if(checkBlock(tile, block)) return;

        BlockContainer container = block.createContainer(tile);

        if(block instanceof Multiblock)
            buildMultiblock(tile, (Multiblock) block, container);
        else
            tile.setBlock(block, container);
    }

    private void buildMultiblock(Tile tile, Multiblock block, BlockContainer container) {
        World world = tile.getWorld();
        int rootX = tile.getX();
        int rootY = tile.getY();

        for(int x = rootX; x < rootX + block.getWidth(); x++) {
            for(int y = rootY; y < rootY + block.getHeight(); y++) {
                world.getTileAt(x, y).get().setBlock((Block) block, container);
            }
        }
    }

    private boolean checkBlock(Tile tile, Block block) {
        if(block instanceof Multiblock)
            if(!checkMultiblock(tile, (Multiblock) block))
                return true;
        return false;
    }

    static boolean checkMultiblock(Tile tile, Multiblock block) {
        World world = tile.getWorld();
        int rootX = tile.getX();
        int rootY = tile.getY();

        for(int x = rootX; x < rootX + block.getWidth(); x++) {
            for(int y = rootY; y < rootY + block.getHeight(); y++) {
                Optional<Tile> optionalTile = world.getTileAt(x, y);

                if(!optionalTile.isPresent())
                    return false;

                if(optionalTile.get().getBlock().isPresent())
                    return false;
            }
        }

        return true;
    }

    @Override
    public String getName() {
        return "Building Block!";
    }
}
