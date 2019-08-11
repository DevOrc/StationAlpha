package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.DefaultBlockRenderer;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.world.Tile;

public class BatteryRenderer extends DefaultBlockRenderer {

    private static final Color chargingColor = Color.GOLDENROD;
    private static final Color fullColor = new Color(.2f, .8f, .2f, 1f);
    private static final int BOX_SIZE = 22;

    public BatteryRenderer(Block block) {
        super(block);
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        BlockContainer container = getContainer(tile);

        if(container.getTile().equals(tile))
            renderBatteryLevel(tile, (BatteryContainer) container, batch);


        super.renderBlock(batch, tile);
    }

    private void renderBatteryLevel(Tile tile, BatteryContainer container, SpriteBatch b) {
        int borderSize = (Tile.TILE_SIZE - BOX_SIZE) / 2;
        int pixelX = (tile.getX() * Tile.TILE_SIZE) + borderSize;
        int pixelY = (tile.getY() * Tile.TILE_SIZE) + borderSize;

        ShapeUtil.drawRect(pixelX, pixelY, BOX_SIZE, BOX_SIZE, Color.WHITE, b);

        double percent = (double) container.getAmount() / container.getCapacity();

        renderBatteryLevel(pixelX, pixelY, percent, container.getRotation(), b);
    }

    private void renderBatteryLevel(int pixelX, int pixelY, double percent, BlockRotation rotation, SpriteBatch b) {
        Color color = percent > .99 ? fullColor : chargingColor;
        int barSize = (int) (percent * BOX_SIZE);

        switch(rotation) {
            case NORTH:
                ShapeUtil.drawRect(pixelX, pixelY, BOX_SIZE, barSize, color, b);
                break;
            case SOUTH:
                ShapeUtil.drawRect(pixelX, pixelY + BOX_SIZE - barSize, BOX_SIZE, barSize, color, b);
                break;
            case EAST:
                ShapeUtil.drawRect(pixelX, pixelY, barSize, BOX_SIZE, color, b);
                break;
            case WEST:
                ShapeUtil.drawRect(pixelX + BOX_SIZE - barSize, pixelY, barSize, BOX_SIZE, color, b);
                break;
            default:
                throw new GdxRuntimeException("");
        }
    }
}
