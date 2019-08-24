package com.noahcharlton.stationalpha.block.scaffolding;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueueTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class ScaffoldingContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private ScaffoldingContainer container;

    @BeforeEach
    void setUp() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getTreeBlock());
        buildBlock.setUseScaffolding(true);
        buildBlock.onClick(tile, Input.Buttons.LEFT);

        container = (ScaffoldingContainer) tile.getContainer().get();
    }

    @Test
    void finishBuildContainerFilledTest() {
        container.finishBuilding();

        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++){
                Tile tile = world.getTileAt(x, y).get();

                Assertions.assertEquals(Blocks.getTreeBlock(), tile.getContainer().get().getBlock());
            }
        }
    }

    @Test
    void getWidthBasicTest() {
        Assertions.assertEquals(3, container.getWidth());
    }

    @Test
    void getHeightBasicTest() {
        Assertions.assertEquals(3, container.getHeight());
    }

    @Test
    void saveBasicTest() {
        StringWriter writer = new StringWriter();
        container.onSave(new QuietXmlWriter(writer));

        String expected = "<Block>tree</Block>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void onLoadBasicTest() {
        String expected = "<Block>wall</Block>\n";
        container.onLoad(LoadTestUtils.asChild(expected));

        Assertions.assertEquals(Blocks.getWall(), container.getBlockToBuild());
    }

    @Test
    void onDestroyItemsReturnedTest() {
        world.getInventory().setAmountForItem(Item.STEEL, 4);
        world.getInventory().setAmountForItem(Item.WOOD, 4);

        BuildBlock buildBlock = new BuildBlock(Blocks.getBedBlock());
        buildBlock.setUseScaffolding(true);
        buildBlock.onClick(tile, Input.Buttons.LEFT);

        container = (ScaffoldingContainer) tile.getContainer().get();
        buildBlock.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertEquals(4, world.getInventory().getAmountForItem(Item.STEEL));
        Assertions.assertEquals(4, world.getInventory().getAmountForItem(Item.WOOD));
    }

    @Test
    void onDestroyRemovesJobTest() {
        container.onUpdate();
        Job job = container.getCurrentJob().get();

        container.onDestroy();

        JobQueueTests.assertNotInJobQueue(job);
    }
}
