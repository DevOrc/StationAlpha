package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.world.ManufacturingManager;

public class ManufacturingManagerLoader {

    static void load(XmlReader.Element elements, ManufacturingManager manager) {
        for(XmlReader.Element element : elements.getChildrenByName("Recipe")){
            manager.addRecipeToQueue(ManufacturingRecipe.loadRecipe(element));
        }
    }

}
