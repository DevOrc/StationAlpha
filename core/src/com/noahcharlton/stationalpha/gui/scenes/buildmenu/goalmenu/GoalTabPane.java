package com.noahcharlton.stationalpha.gui.scenes.buildmenu.goalmenu;

import com.noahcharlton.stationalpha.goal.GoalTab;
import com.noahcharlton.stationalpha.gui.components.ComponentGroup;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.layout.HStretchLayout;

public class GoalTabPane extends ComponentGroup {

    private static final int HEIGHT = MenuButton.HEIGHT;

    private final GoalMenu goalMenu;
    private GoalTab selected = GoalTab.BASICS;

    public GoalTabPane(GoalMenu goalMenu) {
        this.goalMenu = goalMenu;

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
        setWidth(goalMenu.getWidth());
        setHeight(HEIGHT);
    }

    @Override
    protected void updatePosition() {
        setX(goalMenu.getX());
        setY(goalMenu.getY() + goalMenu.getHeight() - HEIGHT);
    }

    public GoalTab getSelected() {
        return selected;
    }
}
