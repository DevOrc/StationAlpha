package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TileTests {

    private final Tile tile = new Tile(0, 0, new World());

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
    void setBlockNoContainerTest() {
        NoContainerTestBlock block = new NoContainerTestBlock();
        tile.setBlock(block);

        Assertions.assertFalse(tile.getContainer().isPresent());
    }

    @Test
    void setBlockOverrideContainerWithEmptyTest() {
        tile.setBlock(Blocks.getDoor());

        Assertions.assertTrue(tile.getContainer().isPresent());

        tile.setBlock(new NoContainerTestBlock());
        Assertions.assertFalse(tile.getContainer().isPresent());
    }

    @Test
    void setBlockEmptySetsContainerEmptyTest() {
        tile.setBlock(Blocks.getDoor());

        Assertions.assertTrue(tile.getContainer().isPresent());

        tile.setBlock(null);
        Assertions.assertFalse(tile.getContainer().isPresent());
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

}
class NoContainerTestBlock extends Block{

    @Override
    public Optional<BlockContainer> createContainer(Tile tile) {
        return Optional.empty();
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }
}
class ContainerCountTestBlock extends Block {

    private int containerCount;

    @Override
    public Optional<BlockContainer> createContainer(Tile tile) {
        containerCount++;
        return Optional.empty();
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    public int getContainerCount() {
        return containerCount;
    }
}
