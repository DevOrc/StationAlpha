package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.engine.input.mine.MineActions;

import java.util.ArrayList;
import java.util.List;

public class ManufacturingRecipes {

    private static final List<ManufacturingRecipe> recipes = new ArrayList<>();

    private ManufacturingRecipes() {
    }

    public static void init() {
        recipes.add(ManufacturingRecipe.createBuilder()
                .setInput(Item.SPACE_ROCK.stack(1))
                .setOutput(Item.STEEL.stack(3))
                .setTime(180)
                .setType(RecipeType.CRAFT)
                .build()
        );

        recipes.add(ManufacturingRecipe.createBuilder()
                .setInput(Item.SPACE_ROCK.stack(8))
                .setOutput(Item.COPPER.stack(1))
                .setTime(360)
                .setType(RecipeType.CRAFT)
                .build()
        );

        recipes.add(ManufacturingRecipe.createBuilder()
                .setInput(Item.LEAVES.stack(32))
                .setOutput(Item.DIRT.stack(1))
                .setTime(20_000)
                .setType(RecipeType.COMPOST)
                .build()
        );

        recipes.add(ManufacturingRecipe.createBuilder()
                .setInput(Item.SPACE_DUST.stack(15))
                .setOutput(Item.UNOBTAINIUM.stack(1))
                .setTime(15_000)
                .setType(RecipeType.SYNTHESIZE)
                .build()
        );

        MineActions.init();
    }

    public static List<ManufacturingRecipe> getRecipes() {
        return recipes;
    }
}
