package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.world.Tile;

public class MultiblockRenderer extends DefaultBlockRenderer{

    private final Multiblock block;

    public MultiblockRenderer(Multiblock block) {
        super((Block) block);
        this.block = block;
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        BlockContainer container = getContainer(tile);

        if(!container.getTile().equals(tile))
            return;

        super.renderBlock(batch, tile);
    }

    BlockContainer getContainer(Tile tile) {
        return tile.getContainer().orElseThrow(() -> new GdxRuntimeException("Multiblocks must have containers!"));
    }
}
