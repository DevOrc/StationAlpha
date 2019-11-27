package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.BeforeEach;

public class PowerProducerContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private PowerProducerContainer container;

    @BeforeEach
    void setUp() {
        container = new PowerProducerContainer(tile, Blocks.getArcReactor(), BlockRotation.NORTH, 5);

        for(int x = 0; x < 2; x++) {
            for(int y = 0; y < 2; y++) {
                Tile tile = world.getTileAt(x, y).get();

                tile.setBlock(Blocks.getArcReactor(), container);
            }
        }
    }
}
