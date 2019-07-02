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

        drawRotated(batch, texture, x, y, container.getRotation().getDegrees(), container.getWidth());
    }

    public static void drawRotated(SpriteBatch b, Texture t, int x, int y, int rotation, int containerWidth){
        int width = t.getWidth();
        int height = t.getHeight();
        boolean flip = false;

        if(rotation == 270){
            flip = true;
            rotation = 90;
        }

        //Hack to deal with rotation (shifts the 90/270 degree
        // blocks to the right cause the rotation pushes them to the left)
        if(rotation == 90)
            x += containerWidth * Tile.TILE_SIZE;

        if(rotation == 180){
            flip = true;
            rotation = 0;
        }


        b.draw(t, x, y, 0, 0, width, height, 1, 1, rotation,
                0, 0, width, height, flip, flip);
    }

    protected BlockContainer getContainer(Tile tile) {
        return tile.getContainer().orElseThrow(() -> new GdxRuntimeException("Blocks must have containers!"));
    }
}
