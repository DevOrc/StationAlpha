package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.Texture;
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

        Texture texture = block.getTexture().get().get();
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        drawRotated(batch, texture, x, y, Tile.TILE_SIZE / 2, Tile.TILE_SIZE / 2,
                container.getRotation().getDegrees());
    }

    private static void drawRotated(SpriteBatch b, Texture t, int x, int y, int rotX, int rotY, int rotation){
        int width = t.getWidth();
        int height = t.getHeight();
        boolean flipY = false;

        if(rotation == 270){
            flipY = true;
            rotation = 90;
        }

        if(rotation == 180){
            flipY = true;
            rotation = 0;
        }


        b.draw(t, x, y, rotX, rotY, width, height, 1, 1, rotation,
                0, 0, width, height, false, flipY);
    }

    private BlockContainer getContainer(Tile tile) {
        return tile.getContainer().orElseThrow(() -> new GdxRuntimeException("Multiblocks must have containers!"));
    }
}
