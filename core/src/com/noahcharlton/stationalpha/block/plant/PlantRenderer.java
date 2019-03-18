package com.noahcharlton.stationalpha.block.plant;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.world.Tile;

public class PlantRenderer implements BlockRenderer {

    private final Plant plant;

    public PlantRenderer(Plant plant) {
        this.plant = plant;
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        int srcX = 0;
        int srcY = getContainer(tile).getStage() * 32;

        batch.draw(plant.getTexture().get().get(), x, y, srcX, srcY, 32, 32);
    }

    private PlantContainer getContainer(Tile tile) {
        return (PlantContainer) tile.getContainer().get();
    }
}
