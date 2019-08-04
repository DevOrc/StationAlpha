package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.engine.input.mine.MineActions;

import java.util.ArrayList;
import java.util.List;

public class ManufacturingRecipes {

    private static final List<ManufacturingRecipe> recipes = new ArrayList<>();

    private ManufacturingRecipes() {}

    public static void init(){
        add(Item.SPACE_ROCK.stack(1), Item.STEEL.stack(3), 180, RecipeType.CRAFT);
        add(Item.SPACE_ROCK.stack(8), Item.COPPER.stack(1), 360, RecipeType.CRAFT);
        add(Item.LEAVES.stack(32), Item.DIRT.stack(1), 20_000, RecipeType.COMPOST);
        add(Item.SPACE_DUST.stack(15), Item.UNOBTAINIUM.stack(1), 10_000, RecipeType.SYNTHESIZE);

        MineActions.init();
    }

    private static void add(ItemStack input, ItemStack output, int time, RecipeType type){
        recipes.add(new ManufacturingRecipe(input, output, time, type));
    }

    public static List<ManufacturingRecipe> getRecipes() {
        return recipes;
    }
}
