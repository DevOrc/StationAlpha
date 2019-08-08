package com.noahcharlton.stationalpha.world.load;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.world.ManufacturingManager;
import com.noahcharlton.stationalpha.world.save.ManufacturingManagerSaver;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class ManufacturingManagerLoaderTests {

    @Test
    void loadCountTest() {
        ManufacturingManager manager = new ManufacturingManager();
        String xml = generateXML();

        ManufacturingManagerLoader.load(LoadTestUtils.asElement(xml), manager);

        Assertions.assertEquals(2, manager.getQueueForType(RecipeType.CRAFT).size());
    }

    private String generateXML() {
        ManufacturingManager manager = new ManufacturingManager();
        manager.addRecipeToQueue(createRecipe());
        manager.addRecipeToQueue(createRecipe());

        StringWriter writer = new StringWriter();

        new ManufacturingManagerSaver(manager).save(new QuietXmlWriter(writer));

        return writer.toString();
    }

    private ManufacturingRecipe createRecipe(){
        return ManufacturingRecipe.createBuilder()
                .setInput(Item.LEAVES.stack(0))
                .setOutput(Item.LEAVES.stack(0))
                .setType(RecipeType.CRAFT)
                .setTime(1)
                .build();
    }

}
