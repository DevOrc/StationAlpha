package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlaceConduitTests {

    private final PlaceConduit placeConduit = new PlaceConduit();
    private final World world = new World();

    @Test
    void rightClickDeletesWireTest() {
        Tile tile = world.getTileAt(0, 0).get();
        tile.setConduit(true);

        placeConduit.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.hasConduit());
    }

    @Test
    void rightClickEmptyTileCancelsBuildActionTest() {
        InputHandler.getInstance().setBuildAction(placeConduit);
        Tile tile = world.getTileAt(0, 0).get();

        placeConduit.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(InputHandler.getInstance().getBuildManager().getAction().isPresent());
    }

    @Test
    void leftClickAddsConduitTest() {
        world.getInventory().setAmountForItem(Item.STEEL, 1);
        Tile tile = world.getTileAt(0, 0).get();

        placeConduit.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertTrue(tile.hasConduit());
    }

    @Test
    void leftClickRemovesSteelTest() {
        world.getInventory().setAmountForItem(Item.STEEL, 1);
        Tile tile = world.getTileAt(0, 0).get();

        placeConduit.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(0, world.getInventory().getAmountForItem(Item.STEEL));
    }

    @Test
    void doesNotBuildIfNotEnoughSteelTest() {
        Tile tile = world.getTileAt(0, 0).get();

        placeConduit.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertFalse(tile.hasConduit());
    }
}
