package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class DustCollectorContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private final DustCollectorContainer container = new DustCollectorContainer(tile,
            Blocks.getDustCollector(), BlockRotation.EAST);

    @Test
    void tickDecrementsOnUpdateTest() {
        tile.setConduit(true);
        int start = container.getTick();

        container.onUpdate();

        Assertions.assertEquals(1, start - container.getTick());
    }

    @Test
    void doesNotDecreaseTickOnNoPowerTest() {
        int start = container.getTick();

        container.onUpdate();

        Assertions.assertEquals(start, container.getTick());
    }

    @Test
    void onSaveBasicTest() {
        StringWriter writer = new StringWriter();

        container.onSave(new QuietXmlWriter(writer));

        String expected = "<Tick>" + container.getTick() + "</Tick>\n" +
                "<StartTick>" + container.getStartTick() + "</StartTick>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void onLoadTickTest() {
        String xml = "<Tick>234</Tick><StartTick>510</StartTick>";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(234, container.getTick());
    }

    @Test
    void onLoadStartTickTest() {
        String xml = "<Tick>234</Tick><StartTick>510</StartTick>";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(510, container.getStartTick());
    }
}
