package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class WorldTests {

    private final World world = new World();

    @Test
    void getTileAtBasicTest() {
        Assertions.assertEquals(new Tile(0, 0, world), world.getTileAt(0, 0).get());
    }

    @Test
    void getTileOutsideBorrow() {
        Assertions.assertFalse(world.getTileAt(1000, 1000).isPresent());
    }

    @Test
    void onWorldUpdateNorthTest() {
        assertWorldUpdate(0, 0, 0, 1);
    }

    @Test
    void onWorldUpdateSouthTest() {
        assertWorldUpdate(0, 1, 0, 0);
    }

    @Test
    void onWorldUpdateEastTest() {
        assertWorldUpdate(0, 0, 1, 0);
    }

    @Test
    void onWorldUpdateWestTest() {
        assertWorldUpdate(1, 0, 0, 0);
    }

    void assertWorldUpdate(int x1, int y1, int x2, int y2) {
        Block block = new BlockUpdateTestBlock();
        world.getTileAt(x1, y1).get().setBlock(block);
        world.getTileAt(x2, y2).get().setBlock(block);

        world.triggerWorldUpdate(x1, y1);

        BlockUpdateTestContainer container = (BlockUpdateTestContainer)
                Block.getContainerFromTile(world.getTileAt(x2, y2).get());

        Assertions.assertEquals(2, container.updateCount);
    }

    @Test
    void onWorldUpdateOnPlaceTest() {
        Block block = new BlockUpdateTestBlock();
        world.getTileAt(0, 0).get().setBlock(block);

        BlockUpdateTestContainer container = (BlockUpdateTestContainer)
                Block.getContainerFromTile(world.getTileAt(0, 0).get());

        Assertions.assertEquals(1, container.updateCount);
    }

    @Test
    void worldUpdateOnFloorPlaceTest() {
        Block block = new BlockUpdateTestBlock();
        world.getTileAt(0, 0).get().setBlock(block);
        world.getTileAt(0, 0).get().setFloor(null);

        BlockUpdateTestContainer container = (BlockUpdateTestContainer)
                Block.getContainerFromTile(world.getTileAt(0, 0).get());

        Assertions.assertEquals(2, container.updateCount);
    }

    @Test
    void removeDeadWorkersTest() {
        world.getWorkers().get(0).die("");
        world.updateWorkers();

        Assertions.assertEquals(0, world.getWorkers().size());
    }
}
class BlockUpdateTestContainer extends BlockContainer {

    int updateCount;

    public BlockUpdateTestContainer(Tile tile, Block block) {
        super(tile, block);
    }

    @Override
    public void onBlockUpdate() {
        updateCount++;
    }
}
class BlockUpdateTestBlock extends Block{

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation rotation) {
        return new BlockUpdateTestContainer(tile, this);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }
}