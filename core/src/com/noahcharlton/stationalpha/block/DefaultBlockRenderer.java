package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.world.Tile;

public class DefaultBlockRenderer implements BlockRenderer{

    private final Block block;

    public DefaultBlockRenderer(Block block) {
        this.block = block;
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        BlockContainer container = getContainer(tile);

        if(!container.getTile().equals(tile))
            return;

        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        batch.draw(block.getTexture().get().get(), x, y);
    }

    private BlockContainer getContainer(Tile tile) {
        return tile.getContainer().orElseThrow(() -> new GdxRuntimeException("Multiblocks must have containers!"));
    }
}
