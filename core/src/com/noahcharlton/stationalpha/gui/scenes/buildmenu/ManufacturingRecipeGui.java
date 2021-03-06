package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class ManufacturingRecipeGui extends Pane {

    private static final int SPACING = 10;
    private static final int WIDTH = (MenuButton.WIDTH * 3) + (SPACING * 3);
    private static final int X = ManufacturingMenu.WIDTH;

    private Optional<ManufacturingRecipe> currentRecipe = Optional.empty();
    private int height = 300;

    private final MenuButton makeOneButton;
    private final MenuButton makeFiveButton;
    private final MenuButton makeTwentyButton;

    public ManufacturingRecipeGui() {
        setDrawBorder(true, true, false, false);

        makeOneButton = new MenuButton("Make 1", () -> makeRecipe(1));
        makeFiveButton = new MenuButton("Make 5", () -> makeRecipe(5));
        makeTwentyButton = new MenuButton("Make 20", () -> makeRecipe(20));

        addAllGui(makeOneButton, makeFiveButton, makeTwentyButton);
    }

    void makeRecipe(int times){
        World.getInstance().ifPresent(world -> makeRecipe(times, world));
    }

    void makeRecipe(int times, World world) {
        if(!currentRecipe.isPresent())
            return;

        for(int i = 0; i < times; i++){
            world.getManufacturingManager().addRecipeToQueue(currentRecipe.get());
        }
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, Color.WHITE);
        drawCenteredText(b, "Manufacture", getHeight() - 20);

        currentRecipe.ifPresent(recipe -> this.drawRecipe(b, recipe));
    }

    private void drawRecipe(SpriteBatch b, ManufacturingRecipe recipe) {
        int y = getY() + SPACING + MenuButton.HEIGHT + (SPACING * 3);

        ItemStack input = recipe.getInput();
        ItemStack output = recipe.getOutput();

        String inputText = input.getItem().getDisplayName() + ":  " + input.getAmount();
        String outputText = output.getItem().getDisplayName() + ":  " + output.getAmount();
        String timeText = "Time: " + recipe.getTime() + " ticks";
        String typeText = "Type: " + recipe.getType().name().toLowerCase();

        setFontData(.5f, Color.GOLDENROD);
        y += font.draw(b, typeText, getX() + 10, y).height + SPACING;
        setFontData(.5f, Color.WHITE);
        y += font.draw(b, timeText, getX() + 10, y).height + SPACING;
        y += SPACING * 3;

        y += font.draw(b, outputText, getX() + 10, y).height + SPACING + SPACING;
        setFontData(.7f, Color.WHITE);
        y += font.draw(b, "Output ", getX() + 10, y).height + SPACING;
        y += SPACING * 3;

        setFontData(.5f, Color.WHITE);
        y += font.draw(b, inputText, getX() + 10, y).height + SPACING + SPACING;
        setFontData(.7f, Color.WHITE);
        y += font.draw(b, "Input", getX() + 10, y).height;
        y += SPACING * 3;

        height = y;
    }

    @Override
    protected void updatePosition() {
        setX(X);
        setY(0);

        layoutButtons();
    }

    private void layoutButtons() {
        int x = getX() + SPACING;
        int y = getY() + SPACING;

        x = layoutButton(x, y, makeOneButton);
        x = layoutButton(x, y, makeFiveButton);
        layoutButton(x, y, makeTwentyButton);
    }

    private int layoutButton(int x, int y, MenuButton button) {
        button.setX(x);
        button.setY(y);

        x += SPACING + MenuButton.WIDTH;

        return x;
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(height);
    }

    public void setRecipe(ManufacturingRecipe newRecipe) {
        if(currentRecipe.isPresent()){
            newRecipe = currentRecipe.get() == newRecipe ? null : newRecipe;
        }

        currentRecipe = Optional.ofNullable(newRecipe);

        setVisible(currentRecipe.isPresent());
    }

    public Optional<ManufacturingRecipe> getCurrentRecipe() {
        return currentRecipe;
    }
}
