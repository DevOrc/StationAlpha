package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.plant.PlantContainer;
import com.noahcharlton.stationalpha.block.scaffolding.ScaffoldingContainer;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

public class BuildBlockTests {

    private final World world = new World();

    @Test
    void basicBuildBlock() {
        world.getInventory().changeAmountForItem(Item.STEEL, 1);
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        Tile tile = world.getTileAt(5, 5).get();

        buildBlock.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertSame(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void buildBlockWithoutResourcesDoesNotWork() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        Tile tile = world.getTileAt(5, 5).get();

        buildBlock.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }

    @Test
    void buildBlockRemovesRequireResourcesTest() {
        world.getInventory().setAmountForItem(Item.STEEL, 1);

        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        Tile tile = world.getTileAt(5, 5).get();

        buildBlock.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(0, world.getInventory().getAmountForItem(Item.STEEL));
    }

    @Test
    void basicDestroyBlock() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        Tile tile = world.getTileAt(5, 5).get();

        tile.setBlock(Blocks.getPotatoPlant());
        buildBlock.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }

    @Test
    void checkMultiblockBasicTest() {
        Block multiblock = Blocks.getWorkbench();
        Tile tile = world.getTileAt(0, 0).get();

        Assertions.assertTrue(BuildBlock.checkBlock(tile, multiblock.createContainer(tile, BlockRotation.NORTH)));
    }

    @Test
    void checkMultiblockAtEdgeTest() {
        Block multiblock = Blocks.getWorkbench();
        Tile tile = world.getTileAt(World.WORLD_TILE_SIZE - 1, 0).get();

        Assertions.assertFalse(BuildBlock.checkBlock(tile, multiblock.createContainer(tile, BlockRotation.NORTH)));
    }

    @Test
    void checkMultiblockCoversAnotherBlockTest() {
        Block multiblock = Blocks.getWorkbench();
        Tile tile = world.getTileAt(0, 0).get();
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        Assertions.assertFalse(BuildBlock.checkBlock(tile, multiblock.createContainer(tile, BlockRotation.NORTH)));
    }

    @Test
    void buildMultiblockSameContainerTest() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWorkbench());
        buildBlock.onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);

        Assertions.assertSame(world.getTileAt(0, 0).get().getContainer().get(),
                world.getTileAt(1, 0).get().getContainer().get());
    }

    @Test
    void useScaffoldingCreatesScaffoldingContainerTest() {
        Tile tile = world.getTileAt(0, 0).get();
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        buildBlock.setUseScaffolding(true);

        Assertions.assertTrue(buildBlock.createContainer(tile) instanceof ScaffoldingContainer);
    }

    @Test
    void scaffoldingBlockContainerHasCorrectBlockTest() {
        Tile tile = world.getTileAt(0, 0).get();
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        buildBlock.setUseScaffolding(true);

        Assertions.assertTrue(buildBlock.createContainer(tile) instanceof ScaffoldingContainer);
    }

    @Test
    void createContainerAutoBuildNoScaffoldingTest() {
        Tile tile = world.getTileAt(0, 0).get();
        BuildBlock buildBlock = new BuildBlock(Blocks.getPotatoPlant());
        buildBlock.setUseScaffolding(true);

        Assertions.assertTrue(buildBlock.createContainer(tile) instanceof PlantContainer);
    }

    @Test
    void hasCompletedResearchFalseTest() {
        ResearchItem.TEST.setCompleted(false);
        ResearchBlock block = new ResearchBlock(ResearchItem.TEST);
        BuildBlock builder = new BuildBlock(block);

        Assertions.assertFalse(builder.hasCompletedResearch());
    }

    @Test
    void hasCompletedResearchTrueTest() {
        ResearchItem.TEST.setCompleted(true);
        ResearchBlock block = new ResearchBlock(ResearchItem.TEST);
        BuildBlock builder = new BuildBlock(block);

        Assertions.assertTrue(builder.hasCompletedResearch());
    }

    @Test
    void hasCompletedResearchNoResearchTest() {
        ResearchBlock block = new ResearchBlock(null);
        BuildBlock builder = new BuildBlock(block);

        Assertions.assertTrue(builder.hasCompletedResearch());
    }

    @ParameterizedTest(name = "destroyMultiblock(clickX = {0})")
    @ValueSource(ints = {0, 1})
    void destroyMultiblockTest(int destroyClickX) {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWorkbench());
        buildBlock.onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);

        buildBlock.onClick(world.getTileAt(destroyClickX, 0).get(), Input.Buttons.RIGHT);

        Assertions.assertFalse(world.getTileAt(0, 0).get().getBlock().isPresent());
        Assertions.assertFalse(world.getTileAt(1, 0).get().getBlock().isPresent());

    }
}
class ResearchBlock extends Block{

    private final Optional<ResearchItem> item;

    public ResearchBlock(ResearchItem item) {
        this.item = Optional.ofNullable(item);
    }

    @Override
    public String getID() {
        return "Test Block";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return item;
    }
}