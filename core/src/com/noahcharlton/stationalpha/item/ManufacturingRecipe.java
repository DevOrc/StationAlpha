package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.world.Inventory;

import java.util.Objects;

public class ManufacturingRecipe {

    private final ItemStack input;
    private final ItemStack output;

    private final RecipeType type;
    private final int time;


    @Deprecated
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

    public static Builder createBuilder(){
        return new Builder();
    }

    static class Builder{

        private ItemStack input;
        private ItemStack output;
        private RecipeType type;
        private int time;

        private Builder(){}

        public ManufacturingRecipe build(){
            Objects.requireNonNull(type, "Recipe type must be set!");
            Objects.requireNonNull(input, "Input must be set!");
            Objects.requireNonNull(output, "Output must be set!");

            if(time < 1){
                throw new IllegalArgumentException("Time must be greater than zero!");
            }

            return new ManufacturingRecipe(input, output, time, type);
        }

        public Builder setInput(ItemStack input) {
            this.input = input;
            return this;
        }

        public Builder setOutput(ItemStack output) {
            this.output = output;
            return this;
        }

        public Builder setType(RecipeType type) {
            this.type = type;
            return this;
        }

        public Builder setTime(int time) {
            this.time = time;
            return this;
        }
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
