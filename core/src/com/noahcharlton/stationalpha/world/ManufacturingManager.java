package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.item.ManufacturingRecipe;

import java.util.ArrayDeque;
import java.util.Optional;

public class ManufacturingManager {

    private final ArrayDeque<ManufacturingRecipe> recipeQueue = new ArrayDeque<>();

    public void addRecipeToQueue(ManufacturingRecipe recipe){
        recipeQueue.add(recipe);
    }

    public Optional<ManufacturingRecipe> getNextRecipe(){
        return Optional.ofNullable(recipeQueue.poll());
    }

}
