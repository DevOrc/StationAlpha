package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.world.Inventory;

import java.util.Objects;

public class ManufacturingRecipe {

    private final ItemStack input;
    private final ItemStack output;

    private final RecipeType type;
    private final int time;

    public ManufacturingRecipe(ItemStack input, ItemStack output, int time){
        this(input, output, time, RecipeType.CRAFT);
    }

    public ManufacturingRecipe(ItemStack input, ItemStack output, int time, RecipeType type){
        this.input = Objects.requireNonNull(input);
        this.output = Objects.requireNonNull(output);
        this.time = time;
        this.type = type;
    }

    public boolean resourcesAvailable(Inventory inventory) {
        return input.resourcesAvailable(inventory);
    }

    public void removeRequirements(Inventory inventory){
        inventory.changeAmountForItem(input.getItem(), -input.getAmount());
    }

    public void addProducts(Inventory inventory){
        inventory.changeAmountForItem(output.getItem(), output.getAmount());
    }

    public int getTime() {
        return time;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public RecipeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
