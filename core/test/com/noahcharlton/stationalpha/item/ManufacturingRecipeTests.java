package com.noahcharlton.stationalpha.item;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class ManufacturingRecipeTests {

    private final ManufacturingRecipe recipe = new ManufacturingRecipe(Item.SPACE_ROCK.stack(1),
            Item.STEEL.stack(3), 100);

    private final Inventory inventory = new World().getInventory();

    @Test
    void isResourcesAvailableFalseTest() {
        Assertions.assertFalse(recipe.resourcesAvailable(inventory));
    }

    @Test
    void isResourcesAvailableTrueTest() {
        inventory.changeAmountForItem(Item.SPACE_ROCK, 1);

        Assertions.assertTrue(recipe.resourcesAvailable(inventory));
    }

    @Test
    void removeRequirementsBasicTest() {
        inventory.changeAmountForItem(Item.SPACE_ROCK, 4);

        recipe.removeRequirements(inventory);

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.SPACE_ROCK));
    }

    @Test
    void addProductsBasicTest() {
        recipe.addProducts(inventory);

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.STEEL));
    }

    @Test
    void writeRecipeBasicTest() {
        StringWriter writer = new StringWriter();
        ManufacturingRecipe recipe = ManufacturingRecipe.createBuilder()
                .setInput(Item.WOOD.stack(5)).setOutput(Item.DIRT.stack(2))
                .setTime(250).setType(RecipeType.CRAFT).build();

        recipe.writeRecipe(new QuietXmlWriter(writer));

        String expected = "<Input item=\"WOOD\" amount=\"5\"/>\n<Output item=\"DIRT\" amount=\"2\"/>\n" +
                "<Type>CRAFT</Type>\n<Time>250</Time>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void loadRecipeTimeTest() {
        XmlReader.Element element = LoadTestUtils.asElement(getTestXML());
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(element);

        Assertions.assertEquals(25000, recipe.getTime());
    }

    @Test
    void loadRecipeInputItemTest() {
        XmlReader.Element element = LoadTestUtils.asElement(getTestXML());
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(element);

        Assertions.assertEquals(Item.LEAVES, recipe.getInput().getItem());
    }

    @Test
    void loadRecipeOutputItemTest() {
        XmlReader.Element element = LoadTestUtils.asElement(getTestXML());
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(element);

        Assertions.assertEquals(Item.DIRT, recipe.getOutput().getItem());
    }

    @Test
    void loadRecipeInputAmountTest() {
        XmlReader.Element element = LoadTestUtils.asElement(getTestXML());
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(element);

        Assertions.assertEquals(5, recipe.getInput().getAmount());
    }

    @Test
    void loadRecipeOutputAmountTest() {
        XmlReader.Element element = LoadTestUtils.asElement(getTestXML());
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(element);

        Assertions.assertEquals(3, recipe.getOutput().getAmount());
    }

    @Test
    void loadRecipeTypeTest() {
        XmlReader.Element element = LoadTestUtils.asElement(getTestXML());
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(element);

        Assertions.assertEquals(RecipeType.SYNTHESIZE, recipe.getType());
    }

    public String getTestXML(){
        return "<Recipe>" +
                "<Time>25000</Time>" +
                "<Tick>19295</Tick>" +
                "<Type>SYNTHESIZE</Type>" +
                "<Input item=\"LEAVES\" amount=\"5\"/>" +
                "<Output item=\"DIRT\" amount=\"3\"/>" +
                "</Recipe>";
    }

    @Test
    void negativeTimeDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setTime(-1);

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void timeZeroDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setTime(0);

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void nullInputDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setInput(null);

        Assertions.assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void nullOutputDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setOutput(null);

        Assertions.assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void nullTypeDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setType(null);

        Assertions.assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void defaultBuilderBuildsTest() {
       createDefaultBuilder().build();
    }

    private ManufacturingRecipe.Builder createDefaultBuilder(){
        return ManufacturingRecipe.createBuilder()
                .setInput(Item.LEAVES.stack(0))
                .setOutput(Item.LEAVES.stack(0))
                .setType(RecipeType.CRAFT)
                .setTime(1);
    }
}
