package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class PowerNetworkTests {

    private final PowerNetwork network = new PowerNetwork();

    @Test
    void changeCapacityBasicExample() {
        network.changeCapacity(500);

        Assertions.assertEquals(1500, network.getCapacity());
    }

    @Test
    void changeCapacityNegativeBasicTest() {
        network.changeCapacity(250);
        network.changeCapacity(-100);

        Assertions.assertEquals(1150, network.getCapacity());
    }

    @Test
    void changeCapacityCantGoBelowBaseCapacityTest() {
        network.changeCapacity(-100);

        Assertions.assertEquals(1000, network.getCapacity());
    }

    @Test
    void changeCapacityLimitsPowerTest() {
        network.changeCapacity(1000);
        network.changePower(2000);
        Assertions.assertEquals(2000, network.getPower());

        network.changeCapacity(-750);
        Assertions.assertEquals(1250, network.getPower());
    }

    @Test
    void changePowerAddTest() {
        network.changePower(25);

        Assertions.assertEquals(25, network.getPower());
    }

    @Test
    void changePowerRemoveTest() {
        network.changePower(25);
        network.changePower(-7);

        Assertions.assertEquals(18, network.getPower());
    }

    @Test
    void changePowerCantGoBelowZeroTest() {
        network.changePower(-22);

        Assertions.assertEquals(0, network.getPower());
    }

    @Test
    void cantGoAboveCapacityTest() {
        network.changePower(150000);

        Assertions.assertEquals(1000, network.getPower());
    }

    @Test
    void usePowerIfAvailableFalseTest() {
        network.changePower(50);

        Assertions.assertFalse(network.usePowerIfAvailable(100));
        Assertions.assertEquals(50, network.getPower());
    }

    @Test
    void usePowerIfAvailableTrueTest() {
        network.changePower(45);

        Assertions.assertTrue(network.usePowerIfAvailable(40));
        Assertions.assertEquals(5, network.getPower());
    }

    @Test
    void onSaveTest() {
        StringWriter stringWriter = new StringWriter();
        QuietXmlWriter writer = new QuietXmlWriter(stringWriter);

        network.changePower(11);
        network.save(writer);

        String expected = "<PowerNetwork power=\"11\" capacity=\"1000\"/>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void onLoadCapacityTest() {
        String xml = "<PowerNetwork power=\"11\" capacity=\"1163\"/>\n";
        network.load(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(1163, network.getCapacity());
    }

    @Test
    void onLoadPowerTest() {
        String xml = "<PowerNetwork power=\"177\" capacity=\"887\"/>\n";
        network.load(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(177, network.getPower());
    }
}
