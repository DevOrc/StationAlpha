package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.layout.LayoutManager;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.ManufacturingRecipes;

import java.util.ArrayList;

public class ManufacturingMenu extends BuildBarListMenu<ManufacturingRecipe> {

    private static final int HEIGHT = 500;
    static final int WIDTH = ManufacturingLayoutManager.BUTTON_WIDTH + (2 * ManufacturingLayoutManager.PADDING);

    private final ManufacturingRecipeGui recipeGui;

    public ManufacturingMenu() {
        super(ManufacturingRecipes.getRecipes());

        this.recipeGui = new ManufacturingRecipeGui();
        this.addGui(recipeGui);

        setLayoutManager(new ManufacturingLayoutManager());
    }

    @Override
    protected Runnable createRunnable(ManufacturingRecipe item) {
        return () -> recipeGui.setRecipe(item);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if(!visible)
            recipeGui.setRecipe(null);
    }

    @Override
    protected void updateSize() {
        setHeight(HEIGHT);
        setWidth(WIDTH);
    }

    @Override
    public String getName() {
        return "Manufacture";
    }

    @Override
    public InGameIcon getIcon() {
        return InGameIcon.GEAR;
    }

    ManufacturingRecipeGui getRecipeGui() {
        return recipeGui;
    }
}
class ManufacturingLayoutManager implements LayoutManager {

    static final int BUTTON_WIDTH = 230;
    static final int BUTTON_HEIGHT = 40;
    static final int PADDING = 5;

    @Override
    public void layout(GuiComponent parent, ArrayList<GuiComponent> children) {
        int x = parent.getX() + PADDING;
        int y = parent.getY() + PADDING;

        for(GuiComponent child : children) {
            //We do not need to layout the recipe information menu
            if(child instanceof ManufacturingRecipeGui)
                continue;

            child.setX(x);
            child.setY(y);
            child.setWidth(BUTTON_WIDTH);
            child.setHeight(BUTTON_HEIGHT);

            y += BUTTON_HEIGHT;
            y += PADDING;
        }
    }
}