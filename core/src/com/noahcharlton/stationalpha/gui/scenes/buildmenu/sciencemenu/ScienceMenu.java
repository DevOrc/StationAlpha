package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.BasicPane;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.components.ScrollPane;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildMenu;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.World;

public class ScienceMenu extends Pane implements BuildMenu {

    private static final int WINDOW_OFFSET = 50;

    private final ResearchItemInfoBox infoBox = new ResearchItemInfoBox(this);
    private final BasicPane buttonPane = new BasicPane();
    private final ScrollPane scrollPane = new ScrollPane(buttonPane);

    public ScienceMenu() {
        setDrawBorder(true, true, true, true);

        scrollPane.setDrawBorder(true, true, false, true);
        addButtons();
        addAllGui(infoBox, scrollPane);
    }

    private void addButtons() {
        for(ResearchItem item: ResearchItem.values()){
            ResearchItemButton button = new ResearchItemButton(item, this);

            buttonPane.addGui(button);
        }
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        String text = "Science: " + World.getInstance().get().getScienceManager().getSciencePoints();
        setFontData(.65f, Color.WHITE);
        font.draw(b, text, getX() + 10, getY() + getHeight() - 20);
    }

    @Override
    public GuiComponent getComponent() {
        return this;
    }

    @Override
    public String getName() {
        return "Science";
    }

    @Override
    public InGameIcon getIcon() {
        return InGameIcon.SCIENCE;
    }

    @Override
    protected void updatePosition() {
        if(Gdx.graphics == null)
            return;

        setX((Gdx.graphics.getWidth() / 2) - (getWidth() / 2));
        setY((Gdx.graphics.getHeight() / 2) - (getHeight() / 2) + (BuildBar.HEIGHT / 2));

        buttonPane.setX(getX() - scrollPane.getScrollX() + BORDER_WIDTH);
        buttonPane.setY(getY() + scrollPane.getScrollY() + BORDER_WIDTH + infoBox.getHeight());

        scrollPane.setY(buttonPane.getY() - scrollPane.getScrollY());
        scrollPane.setX(buttonPane.getX() + scrollPane.getScrollX());
    }

    @Override
    protected void updateSize() {
        if(Gdx.graphics == null)
            return;

        setWidth(Gdx.graphics.getWidth() - (WINDOW_OFFSET * 2));

        int height = Gdx.graphics.getHeight() - BuildBar.HEIGHT - (WINDOW_OFFSET * 2);
        setHeight(height);

        scrollPane.setWidth(getWidth() - (BORDER_WIDTH * 2));
        scrollPane.setHeight(getHeight() - infoBox.getHeight());

        buttonPane.setWidth(calcResearchTreeWidth());
        buttonPane.setHeight(calcResearchTreeHeight());
    }

    private int calcResearchTreeWidth() {
        int maxX = 0;

        for(ResearchItem item: ResearchItem.values()){
            if(item.getPosX() > maxX){
                maxX = item.getPosX();
            }
        }

        return maxX + ResearchItemButton.WIDTH + 50;
    }

    private int calcResearchTreeHeight() {
        int maxY = 0;

        for(ResearchItem item: ResearchItem.values()){
            if(item.getPosY() > maxY){
                maxY = item.getPosY();
            }
        }

        return maxY + ResearchItemButton.HEIGHT + 50;
    }

    public ResearchItemInfoBox getInfoBox() {
        return infoBox;
    }

    public BasicPane getButtonPane() {
        return buttonPane;
    }
}
