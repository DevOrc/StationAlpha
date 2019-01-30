package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.world.Tile;

public class DefaultBlockRenderer implements BlockRenderer{

    private final Block block;

    public DefaultBlockRenderer(Block block) {
        this.block = block;
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        batch.draw(block.getTexture().get().get(), x, y);
    }
}
