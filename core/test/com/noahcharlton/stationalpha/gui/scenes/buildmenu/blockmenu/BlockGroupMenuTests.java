package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlockGroupMenuTests {

    private final BlockGroup blockGroup = BlockGroup.STRUCTURE;
    private final BlockGroupMenu menu = new BlockGroupMenu(blockGroup);

    @Test
    void setSelectableOnClickTest() {
        Runnable runnable = menu.createRunnable(Blocks.getWall());

        runnable.run();

        Assertions.assertTrue(InputHandler.getInstance().getCurrentlySelected().get() instanceof BuildBlockSelectable);
    }
}
