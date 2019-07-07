package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BuildFloorTests {

    private final World world = new World();
    private final BuildFloor floor = new BuildFloor(Floor.WOOD);

    @Test
    void doesNotBuildIfNotEnoughResourcesTest() {
        Tile tile = world.getTileAt(0, 0).get();

        floor.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Optional.empty(), tile.getFloor());
    }

    @Test
    void removesResourcesOnBuildTest() {
        world.getInventory().setAmountForItem(Item.WOOD, 3);
        Tile tile = world.getTileAt(0, 0).get();

        floor.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(2, world.getInventory().getAmountForItem(Item.WOOD));
    }
}
