package com.noahcharlton.stationalpha.block.bed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.DefaultBlockRenderer;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.world.Tile;

public class BedRenderer extends DefaultBlockRenderer {

    private final BitmapFont font = GuiComponent.getFont();

    public BedRenderer(Block block) {
        super(block);
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        super.renderBlock(batch, tile);

        if(!getContainer(tile).getTile().equals(tile)){
            return;
        }

        BedContainer bedContainer = (BedContainer) getContainer(tile);

        if(bedContainer.getWorker().isPresent()){
            drawName(batch, tile, bedContainer);
        }
    }

    private void drawName(SpriteBatch batch, Tile tile, BedContainer bedContainer) {
        String name = bedContainer.getWorker().get().getName();
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;
        int pillowSize = 20;

        font.setColor(Color.WHITE);
        font.getData().setScale(.22f);

        switch(bedContainer.getRotation()){
            case NORTH:
                font.draw(batch, name, x, y + Tile.TILE_SIZE, Tile.TILE_SIZE, Align.center, true);
                break;
            case WEST:
                font.draw(batch, name, x - Tile.TILE_SIZE, y + (Tile.TILE_SIZE * 2 / 3),
                        (Tile.TILE_SIZE * 2) - pillowSize, Align.center, true);
                break;
            case SOUTH:
                font.draw(batch, name, x, y + (Tile.TILE_SIZE * 3 / 2), Tile.TILE_SIZE, Align.center, true);
                break;
            case EAST:
                font.draw(batch, name, x - Tile.TILE_SIZE + pillowSize, y + (Tile.TILE_SIZE * 2 / 3),
                        (Tile.TILE_SIZE * 2) - pillowSize, Align.center, true);
                break;
        }

    }
}
