package com.noahcharlton.stationalpha.gui.scenes;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarListMenu;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu.BlockMenu;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu.BuildBlockSelectable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class BuildBarTests {

    private final BuildBar buildBar = new BuildBar(Collections.emptyList());

    @Test
    void removeInputHandlerActionOnMenuButtonClickedTest() {
        BuildBarListMenu buildBarMenu = new BlockMenu();
        Runnable runnable = buildBar.createRunnable(Collections.emptyList(), buildBarMenu);
        InputHandler.getInstance().setBuildAction(new BuildBlock(Blocks.getWall()));

        runnable.run();

        Assertions.assertFalse(InputHandler.getInstance().getBuildManager().getAction().isPresent());
    }

    @Test
    void removeSelectableOnTestSelected() {
        BuildBarListMenu buildBarMenu = new BlockMenu();
        Runnable runnable = buildBar.createRunnable(Collections.emptyList(), buildBarMenu);
        BuildBlock buildBlock = new BuildBlock(Blocks.getBedBlock());
        InputHandler.getInstance().setCurrentlySelected(new BuildBlockSelectable(buildBlock));

        runnable.run();

        Assertions.assertFalse(InputHandler.getInstance().getCurrentlySelected().isPresent());
    }
}
