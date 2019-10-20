package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.IconButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BuildBar extends Pane {

    private static final int SPACING = 3;
    private static final int MARGIN = 5;
    public static final int HEIGHT = MARGIN + IconButton.SIZE + MARGIN;

    private static final Logger logger = LogManager.getLogger(BuildBar.class);

    private int width = 200;

    public BuildBar(List<BuildBarMenu> menus) {
        addMenus(menus);
        setDrawBorder(true, true, false, true);
    }

    private void addMenus(List<BuildBarMenu> menus) {
        for(BuildBarMenu menu : menus){
            IconButton menuButton = new IconButton(menu.getIcon(), createRunnable(menus, menu));
            addGui(menuButton);
        }
    }

    Runnable createRunnable(List<BuildBarMenu> menus, BuildBarMenu menu) {
        return () -> {
            boolean previouslyVisible = menu.isVisible();

            menus.forEach(m -> m.setVisible(false));
            menu.setVisible(!previouslyVisible);

            InputHandler.getInstance().setBuildAction(null);
            InputHandler.getInstance().setCurrentlySelected(null);
        };
    }

    @Override
    protected void updatePosition() {
        this.setX((Gdx.graphics.getWidth() / 2) - (width / 2));
        this.setY(0);
    }

    @Override
    protected void updateSize() {
        width = getSubGuis().size() * (IconButton.SIZE + SPACING) + (MARGIN * 2);
        layoutItems();

        this.setHeight(HEIGHT);
        this.setWidth(width);
    }

    private void layoutItems() {
        int x = getX() + MARGIN + BORDER_WIDTH;
        for(int i = 0; i < getSubGuis().size(); i++){
            GuiComponent comp = getSubGuis().get(i);

            comp.setX(x);
            comp.setY(MARGIN);

            x += IconButton.SIZE + SPACING;
        }
    }
}
