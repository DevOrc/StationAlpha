package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.engine.input.mine.MineActions;

import java.util.ArrayList;
import java.util.List;

public class ManufacturingRecipes {

    private static final List<ManufacturingRecipe> recipes = new ArrayList<>();

    private ManufacturingRecipes() {}

    public static void init(){
        add(Item.SPACE_ROCK, 1, Item.STEEL, 3, 180, RecipeType.CRAFT);
        add(Item.SPACE_ROCK, 5, Item.COPPER, 1, 360, RecipeType.CRAFT);
        add(Item.LEAVES, 32, Item.DIRT, 1, 20_000, RecipeType.COMPOST);
        add(Item.SPACE_DUST, 5, Item.UNOBTAINIUM,1, 10_000, RecipeType.SYNTHESIZE);

        MineActions.init();
    }

    private static void add(Item input, int inputAmount, Item output, int outputAmount, int time, RecipeType type){
        recipes.add(new ManufacturingRecipe(input, inputAmount, output, outputAmount, time, type));
    }

    public static List<ManufacturingRecipe> getRecipes() {
        return recipes;
    }
}
