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
    public String getName() {
        return "Blocks";
    }
}
