package com.noahcharlton.stationalpha.item;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

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

    public void writeRecipe(QuietXmlWriter recipeWriter) {
        recipeWriter.element("Input")
                .attribute("item", input.getItem().name())
                .attribute("amount", input.getAmount())
                .pop();

        recipeWriter.element("Output")
                .attribute("item", output.getItem().name())
                .attribute("amount", output.getAmount())
                .pop();

        recipeWriter.element("Type", type.toString());
        recipeWriter.element("Time", time);
    }

    public static ManufacturingRecipe loadRecipe(XmlReader.Element recipe) {
        XmlReader.Element input = Objects.requireNonNull(recipe.getChildByName("Input"));
        XmlReader.Element output = Objects.requireNonNull(recipe.getChildByName("Output"));
        String type = recipe.get("Type");
        int time = recipe.getInt("Time");

        return ManufacturingRecipe.createBuilder()
                .setType(RecipeType.valueOf(type))
                .setTime(time)
                .setInput(parseItemStack(input))
                .setOutput(parseItemStack(output))
                .build();
    }

    private static ItemStack parseItemStack(XmlReader.Element input) {
        return Item.valueOf(input.getAttribute("item")).stack(input.getIntAttribute("amount"));
    }

    public static Builder createBuilder(){
        return new Builder();
    }

    public static class Builder{

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
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ManufacturingRecipe)) return false;
        ManufacturingRecipe recipe = (ManufacturingRecipe) o;
        return getTime() == recipe.getTime() &&
                Objects.equals(getInput(), recipe.getInput()) &&
                Objects.equals(getOutput(), recipe.getOutput()) &&
                getType() == recipe.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInput(), getOutput(), getType(), getTime());
    }

    @Override
    public String toString() {
        return output.toString();
    }

}
