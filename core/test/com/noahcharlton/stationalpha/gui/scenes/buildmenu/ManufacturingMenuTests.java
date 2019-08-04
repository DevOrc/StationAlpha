package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ManufacturingMenuTests {

    private final ManufacturingMenu menu = new ManufacturingMenu();

    @Test
    void setVisibleFalseResetsRecipeGui() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.SPACE_ROCK.stack(1), Item.TEST_ITEM.stack(1), 120);

        menu.getRecipeGui().setRecipe(recipe);
        menu.setVisible(false);

        Assertions.assertFalse(menu.getRecipeGui().getCurrentRecipe().isPresent());
    }

    @Test
    void manufacturingMenuLayoutDoesNotEffectRecipeGuiTest() {
        ManufacturingLayoutManager manager = new ManufacturingLayoutManager();
        ManufacturingMenu menu = new ManufacturingMenu();

        menu.getRecipeGui().setX(Integer.MAX_VALUE);
        menu.getRecipeGui().setY(Integer.MIN_VALUE);
        manager.layout(menu, new ArrayList<>(Arrays.asList(menu.getRecipeGui())));

        Assertions.assertEquals(Integer.MAX_VALUE, menu.getRecipeGui().getX());
        Assertions.assertEquals(Integer.MIN_VALUE, menu.getRecipeGui().getY());
    }
}