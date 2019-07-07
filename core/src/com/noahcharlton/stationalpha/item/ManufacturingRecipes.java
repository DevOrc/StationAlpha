package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.engine.input.mine.MineActions;

import java.util.ArrayList;
import java.util.List;

public class ManufacturingRecipes {

    private static final List<ManufacturingRecipe> recipes = new ArrayList<>();

    private ManufacturingRecipes() {}

    public static void init(){
        addCraft(Item.SPACE_ROCK, 1, Item.STEEL, 3);

        recipes.add(new ManufacturingRecipe(Item.LEAVES, 64, Item.DIRT,
                1, 5400, RecipeType.COMPOST));

        recipes.add(new ManufacturingRecipe(Item.SPACE_DUST, 5, Item.SPACE_ROCK,
                1, 10_000, RecipeType.SYNTHESIZE));

        MineActions.init();
    }

    private static void addCraft(Item input, int inputAmount, Item output, int outputAmount){
        recipes.add(new ManufacturingRecipe(input, inputAmount, output, outputAmount, 180));
    }

    public static List<ManufacturingRecipe> getRecipes() {
        return recipes;
    }
}
