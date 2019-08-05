package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BlockTests {

    @Test
    void emptyTexturePathCausesEmptyTextureTest() {
        Block block = new Block() {
            @Override
            public String getID() {
                return "";
            }

            @Override
            protected Optional<String> getTextureFileName() {
                return Optional.empty();
            }
        };

        Assertions.assertFalse(block.getTexture().isPresent());
    }

    @Test
    void getContainerFromTileBasicTest() {
        Tile tile = new Tile(0, 0, new World());
        tile.setBlock(Blocks.getDoor());

        Assertions.assertSame(tile.getContainer().get(), Block.getContainerFromTile(tile));
    }

    @Test
    void getContainerFromTileEmptyContainerFailsTest() {
        Tile tile = new Tile(0, 0, new World());

        Assertions.assertThrows(GdxRuntimeException.class, () -> {
            Block.getContainerFromTile(tile);
        });
    }

    @Test
    void nullRendererFailsTest() {
        Assertions.assertThrows(NullPointerException.class, NullRendererTestBlock::new);
    }
}
class NullRendererTestBlock extends Block{

    @Override
    public String getID() {
        return "";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    @Override
    protected BlockRenderer createRenderer() {
        return null;
    }
}