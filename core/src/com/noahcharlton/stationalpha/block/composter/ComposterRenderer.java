package com.noahcharlton.stationalpha.block.composter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.DefaultBlockRenderer;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.world.Tile;

public class ComposterRenderer extends DefaultBlockRenderer {

    private static final int OFFSET_X = 4;
    private static final int OFFSET_Y = 10;
    private static final int WIDTH = 24;
    private static final int HEIGHT = 20;

    public ComposterRenderer(Block block) {
        super(block);
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        super.renderBlock(batch, tile);

        ComposterContainer container = (ComposterContainer) tile.getContainer().get();

        if(container.getTick().isPresent() && container.getCurrentRecipe().isPresent()){
            float percent = (float) container.getTick().get() / container.getCurrentRecipe().get().getTime();

            renderCompost(percent, batch, tile);
        }
    }

    private void renderCompost(float percentComplete, SpriteBatch batch, Tile tile) {
        Color color = new Color(.35f, percentComplete  + .25f, .2f, 1f);

        int x = tile.getX() * Tile.TILE_SIZE + OFFSET_X;
        int y = tile.getY() * Tile.TILE_SIZE + OFFSET_Y;

        ShapeUtil.drawRect(x, y, WIDTH, HEIGHT, color, batch);
    }
}
