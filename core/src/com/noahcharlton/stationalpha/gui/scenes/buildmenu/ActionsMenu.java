package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.engine.input.BuildAction;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.PlayerActions;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.IconButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;

public class ActionsMenu extends Pane implements BuildMenu {

    private static final int SPACING = 3;
    private static final int MARGIN = 5;
    public static final int HEIGHT = MARGIN + IconButton.SIZE + MARGIN +BORDER_WIDTH;

    public ActionsMenu() {
        for(BuildAction action : PlayerActions.getActions()){
            IconButton button = new IconButton(action.getIcon(), createRunnable(action));

            addGui(button);
        }

        setDrawBorder(true, true, false, true);
    }

    private Runnable createRunnable(BuildAction item) {
        return () -> InputHandler.getInstance().setBuildAction(item);
    }

    @Override
    public InGameIcon getIcon() {
        return InGameIcon.BLACK_WHITE_PICK_AXE;
    }

    @Override
    public GuiComponent getComponent() {
        return this;
    }

    @Override
    protected void updatePosition() {
        int x = (Gdx.graphics.getWidth() / 2) - (getWidth() / 2);
        int y = BuildBar.HEIGHT;

        setX(x);
        setY(y);

        layoutChildren();
    }

    private void layoutChildren() {
        int x = getX() + MARGIN + BORDER_WIDTH;

        for(int i = 0; i < getSubGuis().size(); i++){
            GuiComponent component = getSubGuis().get(i);

            component.setX(x);
            component.setY(getY() + BORDER_WIDTH + MARGIN);

            x += component.getWidth() + SPACING;
        }
    }

    @Override
    protected void updateSize() {
        int width = getSubGuis().size() * (IconButton.SIZE + SPACING) + (MARGIN * 2);

        setHeight(HEIGHT);
        setWidth(width);
    }
}
