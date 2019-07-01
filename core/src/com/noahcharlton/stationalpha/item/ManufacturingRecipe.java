package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.world.Inventory;

import java.util.Objects;

public class ManufacturingRecipe {

    private final Item inputItem;
    private final int inputAmount;

    private final Item outputItem;
    private final int outputAmount;

    private final RecipeType type;
    private final int time;

    public ManufacturingRecipe(Item inputItem, int inputAmount, Item outputItem, int outputAmount, int time){
        this(inputItem, inputAmount, outputItem, outputAmount, time, RecipeType.CRAFT);
    }

    public ManufacturingRecipe(Item inputItem, int inputAmount, Item outputItem, int outputAmount,
                               int time, RecipeType type){
        this.inputItem = Objects.requireNonNull(inputItem);
        this.inputAmount = Objects.requireNonNull(inputAmount);
        this.outputItem = Objects.requireNonNull(outputItem);
        this.outputAmount = Objects.requireNonNull(outputAmount);
        this.time = time;
        this.type = type;
    }


    public boolean resourcesAvailable(Inventory inventory) {
        return inventory.getAmountForItem(inputItem) >= inputAmount;
    }

    public void removeRequirements(Inventory inventory){
        inventory.changeAmountForItem(inputItem, -inputAmount);
    }

    public void addProducts(Inventory inventory){
        inventory.changeAmountForItem(outputItem, outputAmount);
    }

    public int getTime() {
        return time;
    }

    public int getInputAmount() {
        return inputAmount;
    }

    public Item getInputItem() {
        return inputItem;
    }

    public int getOutputAmount() {
        return outputAmount;
    }

    public Item getOutputItem() {
        return outputItem;
    }

    public RecipeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return outputItem.getDisplayName();
    }
}
