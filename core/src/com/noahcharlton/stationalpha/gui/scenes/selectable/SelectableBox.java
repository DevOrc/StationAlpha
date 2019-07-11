package com.noahcharlton.stationalpha.gui.scenes.selectable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.SpeedButton;

public class SelectableBox extends Pane {

    private static final int WIDTH = 350;
    private static final int Y_POS = BuildBar.HEIGHT + SpeedButton.HEIGHT + 32;

    private final SelectableHelpButton helpButton = new SelectableHelpButton(this, this::onHelpButtonClicked);
    private final HelpMenu helpMenu = new HelpMenu();

    private int height = 250;

    public SelectableBox() {
        setDrawBorder(true, true, true, true);

        addAllGui(helpButton, helpMenu);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        InputHandler.getInstance().getCurrentlySelected().ifPresent(selectable -> {
            renderSelectable(b, selectable);

            helpButton.setVisible(selectable.getHelpInfo().isPresent());
        });
    }

    private void renderSelectable(SpriteBatch b, Selectable selectable) {
        int y = 35;
        int spacing = 20;

        setFontData(.55f, Color.WHITE);

        for(String info : selectable.getDebugInfo()){
            y += drawCenteredText(b, info, y).height + (spacing * .75);
        }

        setFontData(.65f, Color.WHITE);
        y += spacing;
        y += drawCenteredText(b, selectable.getDesc(), y).height + spacing;


        setFontData(.85f, Color.WHITE);
        y += drawCenteredText(b, selectable.getTitle(), y).height;

        height = y;
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
