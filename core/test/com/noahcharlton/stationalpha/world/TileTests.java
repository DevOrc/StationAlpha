package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TileTests {

    private final World world = new World();
    private final Tile tile = new Tile(0, 0, world);

    @Test
    public void basicSetBlockContainerTest(){
        ContainerCountTestBlock block = new ContainerCountTestBlock();
        tile.setBlock(block);

        Assertions.assertEquals(1, block.getContainerCount());
    }

    @Test
    public void setBlockNullTest(){
        tile.setBlock(null);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }

    @Test
    void setBlockMultiblockNullContainerFailsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           tile.setBlock(Blocks.getWorkbench(), null);
        });
    }

    @Test
    void setBlockNonNullContainerWithNullBlockFailsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            tile.setBlock(null, Blocks.getDoor().createContainer(tile, BlockRotation.NORTH));
        });
    }

    @Test
    void setBlockEmptySetsContainerEmptyTest() {
        tile.setBlock(Blocks.getDoor());

        Assertions.assertTrue(tile.getContainer().isPresent());

        tile.setBlock(null);
        Assertions.assertFalse(tile.getContainer().isPresent());
    }

    @Test
    void setBlockNotNullContainerNullFailsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> tile.setBlock(Blocks.getIce(), null));
    }

    @Test
    void newTileDefaultBlockEmptyTest() {
        Assertions.assertFalse(tile.getBlock().isPresent());
    }

    @Test
    void newTileDefaultContainerEmptyTest() {
        Assertions.assertFalse(tile.getContainer().isPresent());
    }

    @Test
    void newTileDefaultFloorEmptyTest() {
        Assertions.assertFalse(tile.getFloor().isPresent());
    }

    @Test
    void xBiggerThanWorldFailsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Tile(1000, 10, world));
    }

    @Test
    void yBiggerThanWorldFailsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Tile(5, 12345, world));
    }

    @Test
    void negativeXBeforeWorldFailsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Tile(-5, 10, world));
    }

    @Test
    void negativeYBeforeWorldFailsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Tile(5, -8, world));
    }

    @Test
    void getAdjacentNorthTest() {
        adjacentBaseTest(5, 5, 5, 6);
    }

    @Test
    void getAdjacentSouthTest() {
        adjacentBaseTest(5, 5, 5, 4);
    }

    @Test
    void getAdjacentEastTest() {
        adjacentBaseTest(6, 5, 5, 5);
    }

    @Test
    void getAdjacentWestTest() {
        adjacentBaseTest(4, 5, 5, 5);
    }

    void adjacentBaseTest(int originX, int originY, int adjacentX, int adjacentY){
        Tile origin = world.getTileAt(originX, originY).get();
        Tile adjacent = world.getTileAt(adjacentX, adjacentY).get();

        Assertions.assertTrue(origin.getAdjacent().contains(adjacent));
    }

    @Test
    void getOpenAdjacentNoneOpenTest() {
        Tile origin = world.getTileAt(0, 0).get();

        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        Assertions.assertFalse(origin.getOpenAdjecent().isPresent());
    }

    @Test
    void getOpenAdjacentBasicTestTest() {
        Tile origin = world.getTileAt(0, 0).get();

        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());

        Assertions.assertEquals(origin.getOpenAdjecent().get(), world.getTileAt(1, 0).get());
    }

    @Test
    void getOpenAdjacentPassableBlockTest() {
        Tile origin = world.getTileAt(0, 0).get();

        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getPotatoPlant());

        Assertions.assertEquals(origin.getOpenAdjecent().get(), world.getTileAt(1, 0).get());
    }

    @Test
    void getAdjacentCornerTest() {
        Tile origin = world.getTileAt(0, 0).get();
        Tile corner = world.getTileAt(1, 1).get();

        Assertions.assertFalse(origin.getAdjacent().contains(corner));
    }

    @Test
    void onDestroyBlockContainerTest() {
        Tile tile = world.getTileAt(0, 0).get();
        OnDestroyTestContainer container =  new OnDestroyTestContainer(tile, Blocks.getIce());
        
        tile.setBlock(Blocks.getIce(), container);
        tile.setBlock(null);

        Assertions.assertTrue(container.isDestroyed());
    }

    @Test
    void hasNonPassableBlockNoBlockTest() {
        Tile tile = world.getTileAt(0, 0).get();

        Assertions.assertFalse(tile.hasNonPassableBlock());
    }

    @Test
    void hasNonPassableBlockPassableBlockTest() {
        Tile tile = world.getTileAt(0, 0).get();
        tile.setBlock(Blocks.getDoor());

        Assertions.assertFalse(tile.hasNonPassableBlock());
    }

    @Test
    void hasNonPassableBlockBlockTest() {
        Tile tile = world.getTileAt(0, 0).get();
        tile.setBlock(Blocks.getWall());

        Assertions.assertTrue(tile.hasNonPassableBlock());
    }
}
class OnDestroyTestContainer extends BlockContainer{

    private boolean destroyed = false;

    public OnDestroyTestContainer(Tile tile, Block block) {
        super(tile, block);
    }

    @Override
    public void onDestroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
class ContainerCountTestBlock extends Block {

    private int containerCount;

    @Override
    public String getID() {
        return "";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation rotation) {
        containerCount++;
        return super.createContainer(tile, rotation);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    public int getContainerCount() {
        return containerCount;
    }
}
