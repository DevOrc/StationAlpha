package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.noahcharlton.stationalpha.science.GoalTab;
import com.noahcharlton.stationalpha.gui.components.ComponentGroup;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.layout.HStretchLayout;

public class SienceTabPane extends ComponentGroup {

    private static final int HEIGHT = MenuButton.HEIGHT;

    private final ScienceMenu scienceMenu;
    private GoalTab selected = GoalTab.TECH;

    public SienceTabPane(ScienceMenu scienceMenu) {
        this.scienceMenu = scienceMenu;

        HStretchLayout layout = new HStretchLayout();
        layout.setPadding(0);
        layout.setHGap(0);
        setLayoutManager(layout);
        setDrawBorder(true, true, false, true);

        addButtons();
    }

    private void addButtons() {
        for(GoalTab tab: GoalTab.values()){
            MenuButton button = new MenuButton(tab.getDisplayName(), () -> selected = tab);

            addGui(button);
        }
    }

    @Override
    protected void updateSize() {
        setWidth(scienceMenu.getWidth());
        setHeight(HEIGHT);
    }

    @Override
    protected void updatePosition() {
        setX(scienceMenu.getX());
        setY(scienceMenu.getY() + scienceMenu.getHeight() - HEIGHT);
    }

    public GoalTab getSelected() {
        return selected;
    }
}
