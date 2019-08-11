package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class BatteryContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private final BatteryContainer batteryContainer = new BatteryContainer(tile,
            Blocks.getBattery(), BlockRotation.NORTH, 25);

    @BeforeEach
    void setUp() {
        tile.setConduit(true);
    }

    /**
     * Noah - make sure to do saving stuff
     */



    @Test
    void onUpdateRemoveTilePowerWhenTileFullTest() {
        tile.setPower(25);
        batteryContainer.onUpdate();

        Assertions.assertEquals(24, tile.getPower());
    }

    @Test
    void onUpdateIntakesPowerWhenTileFullTest() {
        tile.setPower(25);
        batteryContainer.onUpdate();

        Assertions.assertEquals(1, batteryContainer.getAmount());
    }

    @Test
    void doesNotIntakePowerWhenFullTest() {
        tile.setPower(25);
        batteryContainer.setAmount(batteryContainer.getCapacity());

        batteryContainer.onUpdate();

        Assertions.assertEquals(batteryContainer.getCapacity(), batteryContainer.getAmount());
    }

    @Test
    void ifTileLowerAddPowerTest() {
        batteryContainer.setAmount(1);

        batteryContainer.onUpdate();

        Assertions.assertEquals(1,tile.getPower());
    }

    @Test
    void ifTileLowerRemoveBatteryPowerTest() {
        batteryContainer.setAmount(1);

        batteryContainer.onUpdate();

        Assertions.assertEquals(0, batteryContainer.getAmount());
    }

    @Test
    void ifNoPowerDoesNotTransferPower() {
        batteryContainer.setAmount(0);
        tile.setPower(0);

        batteryContainer.onUpdate();

        Assertions.assertEquals(0, tile.getPower());
    }

    @Test
    void onSaveAmountTest() {
        StringWriter stringWriter = new StringWriter();
        batteryContainer.setAmount(12);

        batteryContainer.onSave(new QuietXmlWriter(stringWriter));

        String expected = "<PowerStored>12</PowerStored>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void onLoadAmountTest() {
        XmlReader.Element xml = LoadTestUtils.asChild("<PowerStored>7</PowerStored>");

        batteryContainer.onLoad(xml);

        Assertions.assertEquals(7, batteryContainer.getAmount());
    }
}
