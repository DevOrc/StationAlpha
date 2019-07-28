package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.components.ComponentGroup;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.layout.HStretchLayout;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BuildBar extends ComponentGroup {

    public static final int HEIGHT = 65;
    private static final Logger logger = LogManager.getLogger(BuildBar.class);

    private final HStretchLayout layoutManager;

    public BuildBar(List<BuildBarMenu> menus) {
        layoutManager = new HStretchLayout();
        layoutManager.setHGap(8);
        layoutManager.setPadding(5);

        addMenus(menus);
        setLayoutManager(layoutManager);
        setDrawBorder(true, true, false, false);
    }

    private void addMenus(List<BuildBarMenu> menus) {
        for(BuildBarMenu menu : menus){
            MenuButton menuButton = new MenuButton(menu.getName(), createRunnable(menus, menu));
            addGui(menuButton);
        }
    }

    Runnable createRunnable(List<BuildBarMenu> menus, BuildBarMenu menu) {
        return () -> {
            boolean previouslyVisible = menu.isVisible();

            menus.forEach(m -> m.setVisible(false));
            menu.setVisible(!previouslyVisible);

            InputHandler.getInstance().setBuildAction(null);
        };
    }

    @Override
    protected void updatePosition() {
        this.setX(0);
        this.setY(0);
    }

    @Override
    protected void updateSize() {
        this.setHeight(HEIGHT);
        this.setWidth(Gdx.graphics.getWidth());
    }
}
