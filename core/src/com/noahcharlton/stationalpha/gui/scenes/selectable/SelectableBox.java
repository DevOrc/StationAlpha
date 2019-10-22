package com.noahcharlton.stationalpha.gui.scenes.selectable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.scenes.SpeedButton;

public class SelectableBox extends Pane {

    private static final int WIDTH = 350;
    private static final int Y_POS = SpeedButton.HEIGHT + 32;

    private final SelectableHelpButton helpButton = new SelectableHelpButton(this, this::onHelpButtonClicked);
    private final HelpMenu helpMenu = new HelpMenu();
    private final CloseSelectableMenuButton closeButton = new CloseSelectableMenuButton(this);

    private int height = 250;

    public SelectableBox() {
        setDrawBorder(true, true, true, true);

        addAllGui(helpButton, helpMenu, closeButton);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        InputHandler.getInstance().getCurrentlySelected().ifPresent(selectable -> {
            renderSelectable(b, selectable);

            helpButton.setVisible(selectable.getHelpInfo().isPresent());
        });
    }

    private void renderSelectable(SpriteBatch b, Selectable selectable) {
        int y = getHeight() - 25;
        int spacing = 20;

        setFontData(.85f, Color.WHITE);
        y -= drawCenteredText(b, selectable.getTitle(), y).height;
        y -= spacing * 2;

        if(!selectable.getDesc().isEmpty()){
            setFontData(.65f, Color.WHITE);
            y -= drawCenteredText(b, selectable.getDesc(), y).height + spacing;
        }

        setFontData(.55f, Color.WHITE);

        for(String info : selectable.getDebugInfo()) {
            y -= drawCenteredText(b, info, y).height + (spacing * .75);
        }

        height = getHeight() - y;
    }

    @Override
    protected void updatePosition() {
        setX(Gdx.graphics.getWidth() - WIDTH);
        setY(Y_POS);
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(height);
    }

    private void onHelpButtonClicked() {
        helpMenu.setVisible(!helpMenu.isVisible());
    }
}

class CloseSelectableMenuButton extends Pane {

    private static final int SIZE = 24;
    private final SelectableBox box;

    public CloseSelectableMenuButton(SelectableBox box) {
        this.box = box;

        setDrawBorder(true, true, true, true);
    }

    @Override
    protected void onClick() {
        InputHandler.getInstance().setCurrentlySelected(null);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, isHovering() ? Color.RED : Color.FIREBRICK);
        drawCenteredText(b, "X", SIZE * 9 / 10);
    }

    @Override
    protected void updateSize() {
        setWidth(SIZE);
        setHeight(SIZE);
    }

    @Override
    protected void updatePosition() {
        setX(box.getX() + box.getWidth() - SIZE);
        setY(box.getY() + box.getHeight() - SIZE);
    }
}
