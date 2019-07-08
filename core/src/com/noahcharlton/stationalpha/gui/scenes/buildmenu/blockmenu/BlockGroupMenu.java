package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;

import java.util.Optional;

public class BlockGroupMenu extends BuildBarMenu<Block> {

    public BlockGroupMenu(BlockGroup group) {
        super(group.blocks);

        setVisible(false);
    }

    @Override
    protected Runnable createRunnable(Block block) {
        return () -> {
            BuildBlock buildBlock = new BuildBlock(block);
            BuildBlockSelectable selectable = new BuildBlockSelectable(buildBlock);

            InputHandler.getInstance().setBuildAction(buildBlock);

            InputHandler.getInstance().setCurrentlySelected(Optional.of(selectable));
        };
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        setX(BuildBarMenu.WIDTH);
    }

    @Override
    public String getName() { return null;}
}
