package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.power.PoweredBlockContainer;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.world.Tile;

public class DefaultBlockRenderer implements BlockRenderer {

    private final Block block;
    private long iconTime = 0;

    public DefaultBlockRenderer(Block block) {
        this.block = block;
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        BlockContainer container = getContainer(tile);

        if(!container.getTile().equals(tile))
            return;

        renderBlockTexture(batch, tile, container);
        renderExtras(batch, tile, container);
    }

    private void renderBlockTexture(SpriteBatch batch, Tile tile, BlockContainer container) {
        Texture texture = block.getTexture().get().get();

        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;
        int width = texture.getWidth();
        int height = texture.getHeight();
        int containerWidth = container.getWidth();
        int rotation = container.getRotation().getDegrees();

        drawRotated(batch, texture, x, y, rotation, containerWidth, width, height, 0, 0, 0, 0);
    }

    public static void drawRotated(SpriteBatch b, Texture t, int x, int y, int rotation, int containerWidth,
                                   int width, int height, int srcX, int srcY, int orgX, int orgY) {
        boolean flip = false;

        if(rotation == 270) {
            flip = true;
            rotation = 90;
        }

        //Hack to deal with rotation (shifts the 90/270 degree
        // blocks to the right cause the rotation pushes them to the left)
        if(rotation == 90)
            x += containerWidth * Tile.TILE_SIZE;

        if(rotation == 180) {
            flip = true;
            rotation = 0;
        }


        b.draw(t, x, y, orgX, orgY, width, height, 1, 1, rotation,
                srcX, srcY, width, height, flip, flip);
    }

    private void renderExtras(SpriteBatch batch, Tile tile, BlockContainer container) {
        updateIconTime();

        if(iconTime - System.currentTimeMillis() > 1000)
            return;

        if(container instanceof PoweredBlockContainer){
            PoweredBlockContainer poweredContainer = (PoweredBlockContainer) container;

            if(!poweredContainer.hasPower())
                renderIcon(tile, batch, InGameIcon.NO_POWER);
        }
    }

    private void updateIconTime() {
        if(System.currentTimeMillis() > iconTime)
            iconTime = System.currentTimeMillis() + 2000;
    }

    protected void renderIcon(Tile tile, SpriteBatch batch, InGameIcon icon) {
        BlockContainer container = getContainer(tile);

        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;
        x += (container.getWidth() / 2) * Tile.TILE_SIZE;
        y += (container.getHeight() / 2) * Tile.TILE_SIZE;

        batch.draw(icon.getTexture().get(), x, y);
    }

    protected BlockContainer getContainer(Tile tile) {
        return tile.getContainer().orElseThrow(() -> new GdxRuntimeException("Blocks must have containers!"));
    }
}
