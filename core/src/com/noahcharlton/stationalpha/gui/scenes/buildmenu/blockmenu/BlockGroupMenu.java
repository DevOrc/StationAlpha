package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarListMenu;

import java.util.List;

public class BlockGroupMenu extends BuildBarListMenu<Block> {

    public BlockGroupMenu(BlockGroup group) {
        super(group.blocks);

        setVisible(false);
    }

    @Override
    protected void createButtons(List<Block> items) {
        for(int i = 0; i < items.size(); i++){
            Block block = items.get(i);

            MenuButton button = new BlockMenuButton(block);
            button.setX(0);
            button.setY(getY() + i * (65) + 5);

            addGui(button);
        }
    }

    @Override
    protected Runnable createRunnable(Block item) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        setX(BuildBarListMenu.WIDTH);
    }

    @Override
    public String getName() { return null;}

    @Override
    public InGameIcon getIcon() {
        return null;
    }
}
