package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public abstract class Plant extends Block {

    private final int stageCount;
    private final int ticksPerStage;

    public Plant(int stageCount, int ticksPerStage) {
        this.stageCount = stageCount;
        this.ticksPerStage = ticksPerStage;
    }

    @Override
    protected BlockRenderer createRenderer() {
        return new PlantRenderer(this);
    }

    @Override
    public Optional<BlockContainer> createContainer(Tile tile) {
        return Optional.of(new PlantContainer(tile, this));
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("plant/" + getPlantName() + ".png");
    }

    public abstract Item getProduct();

    public abstract String getPlantName();

    public int getTicksPerStage() {
        return ticksPerStage;
    }

    public int getStageCount() {
        return stageCount;
    }

    @Override
    public Optional<ManagedTexture> getTexture() {
        return super.getTexture();
    }
}
