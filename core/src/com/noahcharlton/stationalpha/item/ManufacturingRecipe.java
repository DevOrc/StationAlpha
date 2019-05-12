package com.noahcharlton.stationalpha.item;

import java.util.Objects;

public class ManufacturingRecipe {

    private final Item inputItem;
    private final int inputAmount;

    private final Item outputItem;
    private final int outputAmount;

    private final int time;

    public ManufacturingRecipe(Item inputItem, int inputAmount, Item outputItem, int outputAmount, int time){
        this.inputItem = Objects.requireNonNull(inputItem);
        this.inputAmount = Objects.requireNonNull(inputAmount);
        this.outputItem = Objects.requireNonNull(outputItem);
        this.outputAmount = Objects.requireNonNull(outputAmount);
        this.time = time;
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

    @Override
    public String toString() {
        return outputItem.getDisplayName();
    }
}
