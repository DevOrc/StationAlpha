package com.noahcharlton.stationalpha.item;

import java.util.ArrayList;
import java.util.List;

public class ManufacturingRecipes {

    private static final List<ManufacturingRecipe> recipes = new ArrayList<>();

    private ManufacturingRecipes() {}

    public static void init(){
        add(Item.SPACE_ROCK, 1, Item.STEEL, 3);
    }

    private static void add(Item input, int inputAmount, Item output, int outputAmount){
        recipes.add(new ManufacturingRecipe(input, inputAmount, output, outputAmount, 180));
    }

    public static List<ManufacturingRecipe> getRecipes() {
        return recipes;
    }
}
