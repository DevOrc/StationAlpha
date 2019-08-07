package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class DustCollectorContainerTests {

    private final World world = new World();
    private final DustCollectorContainer container = new DustCollectorContainer(world.getTileAt(0, 0).get(),
            Blocks.getDustCollector(), BlockRotation.EAST);

    @Test
    void tickDecrementsOnUpdateTest() {
        int start = container.getTick();

        container.onUpdate();

        Assertions.assertEquals(1, start - container.getTick());
    }

    @Test
    void onSaveBasicTest() {
        StringWriter writer = new StringWriter();

        container.onSave(new QuietXmlWriter(writer));

        String expected = "<Tick>" + container.getTick() + "</Tick>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void onLoadBasicTest() {
        String xml = "<Tick>234</Tick>";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(234, container.getTick());
    }
}
