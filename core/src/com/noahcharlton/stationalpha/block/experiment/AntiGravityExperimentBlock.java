package com.noahcharlton.stationalpha.block.experiment;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class AntiGravityExperimentBlock extends Block {

    @Override
    public String getID() {
        return "anti_gravity_experiment";
    }

    @Override
    public String getDisplayName() {
        return "Anti-Gravity Experiment";
    }

    @Override
    public int getDimensionedWidth() {
        return 2;
    }

    @Override
    public int getDimensionedHeight() {
        return 2;
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new AntiGravityExperimentContainer(tile, this, blockRotation);
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.BASIC_MATERIALS);
    }


    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("anti_gravity.png");
    }
}
