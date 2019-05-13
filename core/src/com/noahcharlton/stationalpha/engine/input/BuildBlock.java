package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Objects;
import java.util.Optional;

public class BuildBlock implements BuildAction {

    private final Block block;
    private BlockRotation rotation = BlockRotation.NORTH;

    public BuildBlock(Block block) {
        Objects.requireNonNull(block, "block cannot be null!");

        this.block = block;
    }

    @Override
    public void onKeyPressed(int keycode) {
        if(keycode == Input.Keys.R){
            rotation = rotation.getNext();
        }
    }

    @Override
    public void onClick(Tile tile, int button) {
        if(button == Input.Buttons.LEFT) {
            build(tile, block.createContainer(tile, rotation));
        }

        if(button == Input.Buttons.RIGHT) {
            onRightClick(tile);
        }
    }

    private void onRightClick(Tile tile) {
        if(!tile.getBlock().isPresent()) {
            InputHandler.getInstance().setBuildAction(null);
        } else {
            destroyBlock(tile);
        }
    }

    private void destroyBlock(Tile tile) {
        Tile root = tile.getContainer().get().getTile();
        BlockContainer container = tile.getContainer().get();

        int rootX = root.getX();
        int rootY = root.getY();

        for(int x = rootX; x < rootX + container.getWidth(); x++) {
            for(int y = rootY; y < rootY + container.getHeight(); y++) {
                tile.getWorld().getTileAt(x, y).get().setBlock(null);
            }
        }
    }

    private void build(Tile tile, BlockContainer container) {
        if(!checkBlock(tile, container) || !hasResourcesToBuild(block, tile.getWorld())) return;

        World world = tile.getWorld();
        removeRequiredResources(world);

        int rootX = tile.getX();
        int rootY = tile.getY();

        for(int x = rootX; x < rootX + container.getWidth(); x++) {
            for(int y = rootY; y < rootY + container.getHeight(); y++) {
                world.getTileAt(x, y).get().setBlock(block, container);
            }
        }
    }

    private void removeRequiredResources(World world) {
        block.getRequiredItem().ifPresent(item -> world.getInventory().changeAmountForItem(item, -1));
    }

    boolean hasResourcesToBuild(Block block, World world) {
        if(block.getRequiredItem().isPresent()){
            return world.getInventory().getAmountForItem(block.getRequiredItem().get()) > 0;
        }

        return true;
    }

    static boolean checkBlock(Tile tile, BlockContainer block) {
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
