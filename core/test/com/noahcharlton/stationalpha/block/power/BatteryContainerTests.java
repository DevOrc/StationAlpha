package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class BatteryContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private final BatteryContainer batteryContainer = new BatteryContainer(tile,
            Blocks.getBattery(), BlockRotation.NORTH, 1125);

    @Test
    void onBuiltCapacityIncreaseTest() {
        int before = world.getPowerNetwork().getCapacity();
        batteryContainer.onBuilt();

        int diff = world.getPowerNetwork().getCapacity() - before;

        Assertions.assertEquals(1125, diff);
    }

    @Test
    void onDestroyCapacityDecreaseTest() {
        world.getPowerNetwork().changeCapacity(1130);
        batteryContainer.onDestroy();

        Assertions.assertEquals(1005, world.getPowerNetwork().getCapacity());
    }

    @Test
    void onSaveAmountTest() {
        StringWriter stringWriter = new StringWriter();
        batteryContainer.onSave(new QuietXmlWriter(stringWriter));

        String expected = "<Capacity>1125</Capacity>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void onLoadAmountTest() {
        XmlReader.Element xml = LoadTestUtils.asChild("<Capacity>1125</Capacity>");

        batteryContainer.onLoad(xml);

        Assertions.assertEquals(1125, batteryContainer.getCapacity());
    }
}
