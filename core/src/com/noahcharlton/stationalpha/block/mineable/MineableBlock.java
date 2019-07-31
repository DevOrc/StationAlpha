package com.noahcharlton.stationalpha.block.mineable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.block.*;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Tile;

public abstract class MineableBlock extends Block {

    private MineableBlockRenderer renderer;

    public MineableBlock(InGameIcon icon) {
        renderer.setIcon(icon);
    }

    @Override
    protected BlockRenderer createRenderer() {
        renderer = new MineableBlockRenderer(this);

        return renderer;
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new MineableBlockContainer(tile, this, blockRotation);
    }
}
class MineableBlockRenderer extends DefaultBlockRenderer{

    private InGameIcon icon;

    public MineableBlockRenderer(Block block) {
        super(block);

        this.icon = InGameIcon.PICK_AXE;
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        super.renderBlock(batch, tile);

        if(shouldRenderIcon(tile))
            renderIcon(tile, batch);
    }

    boolean shouldRenderIcon(Tile tile){
        BlockContainer container = getContainer(tile);

        if(container.getTile().equals(tile) && container instanceof MineableBlockContainer){
            MineableBlockContainer mineableContainer = (MineableBlockContainer) container;

            if(mineableContainer.getCurrentJob().filter(job -> job.getStage() != Job.JobStage.FINISHED).isPresent()){
                return true;
            }
        }

        return false;
    }

    private void renderIcon(Tile tile, SpriteBatch batch) {
        BlockContainer container = getContainer(tile);

        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;
        x += (container.getWidth() / 2) * Tile.TILE_SIZE;
        y += (container.getHeight() / 2) * Tile.TILE_SIZE;

        batch.setColor(new Color(1f, 1f, 1f, .5f));
        batch.draw(icon.getTexture().get(), x, y);
        batch.setColor(new Color(1f, 1f, 1f, 1f));
    }

    public void setIcon(InGameIcon icon) {
        this.icon = icon;
    }
}