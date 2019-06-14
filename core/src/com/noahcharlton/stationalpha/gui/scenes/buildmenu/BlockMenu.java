package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.InputHandler;

public class BlockMenu extends BuildBarMenu<Block> {

    public BlockMenu(){
        super(Blocks.getBlocks());
    }

    protected Runnable createRunnable(Block block) {
        return () -> InputHandler.getInstance().setBuildAction(new BuildBlock(block));
    }

    @Override
    protected void updateSize() {
        super.updateSize();

        this.setHeight(BuildBarMenu.HEIGHT + 300);
    }

    @Override
    public String getName() {
        return "Blocks";
    }
}
