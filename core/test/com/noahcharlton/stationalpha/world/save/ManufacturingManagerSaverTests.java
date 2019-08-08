package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class ManufacturingManagerSaverTests {

    private final World world = new World();
    private final StringWriter stringWriter = new StringWriter();
    private final QuietXmlWriter xmlWriter = new QuietXmlWriter(stringWriter);
    private final ManufacturingManagerSaver saver = new ManufacturingManagerSaver(world.getManufacturingManager());

    @Test
    void writeRecipeBasicTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.SPACE_DUST.stack(7),
                Item.UNOBTAINIUM.stack(15), 5321, RecipeType.CRAFT);

        saver.writeRecipe(xmlWriter, recipe);

        String expected = "<Recipe>\n\t<Input item=\"SPACE_DUST\" amount=\"7\"/>\n\t" +
                "<Output item=\"UNOBTAINIUM\" amount=\"15\"/>\n\t" +
                "<Type>CRAFT</Type>\n\t<Time>5321</Time>\n</Recipe>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void saveChildCountTest() {
        addRecipeOfType(RecipeType.CRAFT);
        addRecipeOfType(RecipeType.CRAFT);
        addRecipeOfType(RecipeType.SYNTHESIZE);
        addRecipeOfType(RecipeType.COMPOST);

        saver.save(xmlWriter);
        XmlReader.Element output = LoadTestUtils.asElement(stringWriter.toString());

        Assertions.assertEquals(4, output.getChildrenByName("Recipe").size);
    }

    public void addRecipeOfType(RecipeType type){
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.SPACE_DUST.stack(7),
                Item.UNOBTAINIUM.stack(15), 5321, type));
    }
}
