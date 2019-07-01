package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;

import java.util.*;

public class ManufacturingManager {

    private final Map<RecipeType, ArrayDeque<ManufacturingRecipe>> queues;

    public ManufacturingManager() {
        Map<RecipeType, ArrayDeque<ManufacturingRecipe>> localQueues = new HashMap<>();

        for(RecipeType type: RecipeType.values()){
            localQueues.put(type, new ArrayDeque<>());
        }

        queues = Collections.unmodifiableMap(localQueues);
    }

    public void addRecipeToQueue(ManufacturingRecipe recipe){
        queues.get(recipe.getType()).add(recipe);
    }

    public Optional<ManufacturingRecipe> getNextRecipe(RecipeType type){
        return Optional.ofNullable(queues.get(type)).map(queue -> queue.poll());
    }

    public ArrayDeque<ManufacturingRecipe> getQueueForType(RecipeType type){
        return queues.get(type);
    }

    public Map<RecipeType, ArrayDeque<ManufacturingRecipe>> getQueues() {
        return queues;
    }
}
