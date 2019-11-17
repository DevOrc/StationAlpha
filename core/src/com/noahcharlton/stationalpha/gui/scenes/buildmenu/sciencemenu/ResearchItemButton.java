package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.science.ResearchItem;

public class ResearchItemButton extends MenuButton {

    private static final int MINIMUM_WIDTH = 200;
    private final ScienceMenu scienceMenu;
    private final Pane buttonPane;
    private final ResearchItem researchItem;

    public ResearchItemButton(ResearchItem item, ScienceMenu menu) {
        super(item.getDisplayName(), () -> menu.getInfoBox().setSelectedItem(item));

        this.researchItem = item;
        this.scienceMenu = menu;
        this.buttonPane = scienceMenu.getButtonPane();

        setWidth(calculateWidth(item));
    }

    @Override
    protected void update() {
        super.update();

        Color border = Color.RED;

        if(researchItem.isCompleted()){
            border = Color.FOREST;
        }else if(researchItem.isRequirementCompleted()){
            border = Color.ORANGE;
        }

        setBorderColor(border);
    }

    @Override
    protected void updateSize() {
        setWidth(calculateWidth(researchItem));
    }

    @Override
    protected void updatePosition() {
        setX(buttonPane.getX() + researchItem.getPosX());
        setY(buttonPane.getY() + researchItem.getPosY());
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        super.drawForeground(b);

        drawRequirements(b);
    }

    private void drawRequirements(SpriteBatch b) {
        for(ResearchItem requirement: researchItem.getRequirements()){
            drawRequirementLine(b, requirement);
        }
    }

    private void drawRequirementLine(SpriteBatch b, ResearchItem requirement) {
        int x1 = requirement.getPosX() + buttonPane.getX() + calculateWidth(requirement);
        int y1 = requirement.getPosY() + buttonPane.getY() + (MenuButton.HEIGHT / 2);
        int x2 = getX();
        int y2 = getY() + (MenuButton.HEIGHT  / 2);

        ShapeUtil.drawLine(x1, y1, x2, y2, Color.WHITE, b);
    }

    public int calculateWidth(ResearchItem item){
        if(GuiComponent.getFont() == null)
            return 0;

        GlyphLayout layout = new GlyphLayout(GuiComponent.getFont(), item.getDisplayName());

        return (int) Math.max(layout.width + 50, MINIMUM_WIDTH);
    }
}
