package com.noahcharlton.stationalpha.world.save;

import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.world.ManufacturingManager;

public class ManufacturingManagerSaver {

    private final ManufacturingManager manager;

    public ManufacturingManagerSaver(ManufacturingManager manager){
        this.manager = manager;
    }

    public void save(QuietXmlWriter writer){
        QuietXmlWriter inventoryWriter = writer.element("ManufacturingQueue");

        for(RecipeType type: RecipeType.values()){
            manager.getQueueForType(type).forEach(recipe -> writeRecipe(inventoryWriter, recipe));
        }

        inventoryWriter.pop();
    }

    void writeRecipe(QuietXmlWriter inventoryWriter, ManufacturingRecipe recipe) {
        QuietXmlWriter element = inventoryWriter.element("Recipe");

        recipe.writeRecipe(element);

        element.pop();
    }
}
