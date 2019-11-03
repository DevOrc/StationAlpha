package com.noahcharlton.stationalpha.block.experiment;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.BasicPane;
import com.noahcharlton.stationalpha.world.Tile;

public abstract class ExperimentContainer extends BlockContainer implements Selectable.GuiSelectable {

    public ExperimentContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    @Override
    public GuiComponent createGui() {
        GuiComponent comp = new BasicPane();

        comp.setX(200);
        comp.setY(200);
        comp.setWidth(350);
        comp.setHeight(350);

        return comp;
    }
}
