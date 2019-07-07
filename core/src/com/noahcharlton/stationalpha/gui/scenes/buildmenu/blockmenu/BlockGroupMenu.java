package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;

public class BlockGroupMenu extends BuildBarMenu<Block> {

    public BlockGroupMenu(BlockGroup group) {
        super(group.blocks);

        setVisible(false);
    }

    @Override
    protected Runnable createRunnable(Block block) {
        return () -> InputHandler.getInstance().setBuildAction(new BuildBlock(block));
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        setX(BuildBarMenu.WIDTH);
    }

    @Override
    public String getName() { return null;}
}
