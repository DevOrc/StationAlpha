package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Multiblock;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MultiblockUpdateTests {

    private final World world = new World();

    @Test
    void multiblockUpdateOnceTest() {
        BuildBlock buildBlock = new BuildBlock(new TestMultiblock());
        buildBlock.onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);

        TestMultiblockContainer container = (TestMultiblockContainer) world.getTileAt(2, 3).get()
                .getContainer().get();

        world.updateTiles();

        Assertions.assertEquals(1, container.getUpdateCount());
    }
}
class TestMultiblock extends Block implements Multiblock {

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    @Override
    public int getWidth() {
        return 5;
    }

    @Override
    public int getHeight() {
        return 5;
    }

    @Override
    public BlockContainer createContainer(Tile tile) {
        return new TestMultiblockContainer(tile, this);
    }
}
class TestMultiblockContainer extends BlockContainer{

    private int updateCount;

    public TestMultiblockContainer(Tile tile, Block block) {
        super(tile, block);
    }

    @Override
    public void onUpdate() {
        updateCount++;
    }

    public int getUpdateCount() {
        return updateCount;
    }
}