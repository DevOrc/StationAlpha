package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.layout.VStretchLayout;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;

import java.util.*;

public class BlockMenu extends BuildBarMenu<BlockGroup> {

    private final Map<BlockGroup, BlockGroupMenu> blockMenus;

    public BlockMenu() {
        super(Arrays.asList(BlockGroup.values()));

        blockMenus = createBlockMenus();

        setLayoutManager(new BlockMenuLayout());
    }

    private Map<BlockGroup, BlockGroupMenu> createBlockMenus() {
        Map<BlockGroup, BlockGroupMenu> menus = new HashMap<>();

        for(BlockGroup group : BlockGroup.values()) {
            BlockGroupMenu menu = new BlockGroupMenu(group);
            menus.put(group, menu);

            this.addGui(menu);
        }

        return Collections.unmodifiableMap(menus);
    }

    protected Runnable createRunnable(BlockGroup group) {
        return () -> {
            this.blockMenus.forEach((g, menu) -> {
                if(g.equals(group)) {
                    menu.setVisible(true);
                } else
                    menu.setVisible(false);
            });
        };
    }

    @Override
    public String getName() {
        return "Blocks";
    }
}

class BlockMenuLayout extends VStretchLayout {

    @Override
    public void layout(GuiComponent parent, ArrayList<GuiComponent> children) {
        super.setContainerDimensions(parent);

        ArrayList<GuiComponent> components = new ArrayList<>();

        children.forEach(child -> {
            if(!(child instanceof BlockGroupMenu))
                components.add(child);
        });

        super.layout(parent, components);
    }
}