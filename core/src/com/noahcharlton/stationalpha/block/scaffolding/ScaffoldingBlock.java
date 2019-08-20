package com.noahcharlton.stationalpha.block.scaffolding;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class ScaffoldingBlock extends Block implements BlockRenderer {

    public ScaffoldingBlock() {
        setOpaque(false);
        setPassable(true);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("scaffolding.png");
    }

    @Override
    protected BlockRenderer createRenderer() {
        return this;
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        batch.draw(this.getTexture().get().get(), x, y);
    }

    @Override
    public String getDisplayName() {
        return "Scaffolding";
    }

    @Override
    public String getID() {
        return "scaffolding";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new ScaffoldingContainer(tile, null, blockRotation);
    }
}
