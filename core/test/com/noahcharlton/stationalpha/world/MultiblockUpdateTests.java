package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
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
class TestMultiblock extends Block {

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    @Override
    protected int getDimensionedWidth() {
        return 5;
    }

    @Override
    protected int getDimensionedHeight() {
        return 5;
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation rotation) {
        return new TestMultiblockContainer(tile, this, rotation);
    }
}
class TestMultiblockContainer extends BlockContainer{

    private int updateCount;

    public TestMultiblockContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
        this.updateCount = 0;
    }

    @Override
    public void onUpdate() {
        updateCount++;
    }

    public int getUpdateCount() {
        return updateCount;
    }
}