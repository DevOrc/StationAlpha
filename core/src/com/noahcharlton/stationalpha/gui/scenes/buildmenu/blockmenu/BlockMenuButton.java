package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.badlogic.gdx.graphics.Color;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.science.ResearchItem;
import org.jetbrains.annotations.NotNull;

public class BlockMenuButton extends MenuButton {

    private static final Color disabledColor = new Color(0x202020ff);
    private final Block block;

    public BlockMenuButton(Block block) {
        super(block.getDisplayName(), () -> onButtonClick(block));

        this.block = block;
    }

    @Override
    protected void update() {
        boolean researchDone = isResearchDone(block);
        Color hoverColor =  researchDone ? MenuButton.hoverColor : disabledColor;
        Color backgroundColor = researchDone ? MenuButton.backgroundColor : disabledColor;
        Color textColor = researchDone ? Color.WHITE : Color.GRAY;

        setHoverColor(hoverColor);
        setBackgroundColor(backgroundColor);
        setTextColor(textColor);
    }

    @NotNull
    private static Boolean isResearchDone(Block block) {
        return block.getRequiredResearch().map(ResearchItem::isCompleted).orElse(true);
    }

    private static void onButtonClick(Block block) {
        BuildBlock buildBlock = new BuildBlock(block);
        buildBlock.setUseScaffolding(true);
        BuildBlockSelectable selectable = new BuildBlockSelectable(buildBlock);

        InputHandler.getInstance().setBuildAction(buildBlock);

        InputHandler.getInstance().setCurrentlySelected(selectable);
    }
}
