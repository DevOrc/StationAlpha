package com.noahcharlton.stationalpha.block.tree;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.DefaultBlockRenderer;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.world.Tile;

public class TreeRenderer extends DefaultBlockRenderer {

    public TreeRenderer(Block block) {
        super(block);
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        TreeContainer container = (TreeContainer) getContainer(tile);
        TreeBlock block = (TreeBlock) Blocks.getTreeBlock();

        if(!container.getTile().equals(tile))
            return;

        ManagedTexture texture = container.isSapling() ? block.saplingTexture : block.fullTreeTexture;
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        DefaultBlockRenderer.drawRotated(batch, texture.get(), x, y,
                container.getRotation().getDegrees(), container.getWidth());
    }
}
