package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.science.ResearchItem;

import java.util.Optional;

public class ResearchItemInfoBox extends Pane {

    static final int HEIGHT = 200;

    private final ScienceMenu scienceMenu;
    private Optional<ResearchItem> selectedItem;

    public ResearchItemInfoBox(ScienceMenu scienceMenu) {
        this.scienceMenu = scienceMenu;

        setSelectedItem(null);
        setDrawBorder(true, true, true, true);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        selectedItem.ifPresent(item -> {
            setFontData(.75f, Color.WHITE);
            drawCenteredText(b, item.getDisplayName(), getHeight() - 10);

            setFontData(.55f, Color.WHITE);
            drawCenteredText(b, item.getDesc(), getHeight() - 45);
        });
    }

    @Override
    protected void updateSize() {
        setWidth(scienceMenu.getWidth());
        setHeight(HEIGHT);
    }

    @Override
    protected void updatePosition() {
        setX(scienceMenu.getX());
        setY(scienceMenu.getY());
    }

    public void setSelectedItem(ResearchItem item) {
        this.selectedItem = Optional.ofNullable(item);
        this.setVisible(item != null);
    }

    public Optional<ResearchItem> getSelectedItem() {
        return selectedItem;
    }
}
