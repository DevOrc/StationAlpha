package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.BeforeEach;

public class PoweredBlockContainerTest {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private final TestPoweredBlockContainer container = new TestPoweredBlockContainer(tile);

    @BeforeEach
    void setUp() {
        tile.setBlock(Blocks.getTreeBlock(), container);

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                world.getTileAt(x, y).get().setConduit(true);
            }
        }
    }

}
class TestPoweredBlockContainer extends PoweredBlockContainer{

    private int powerPerTick;

    public TestPoweredBlockContainer(Tile tile) {
        super(tile, Blocks.getTreeBlock(), BlockRotation.NORTH);
    }

    @Override
    public int getPowerPerTick() {
        return powerPerTick;
    }

    public void setPowerPerTick(int powerPerTick) {
        this.powerPerTick = powerPerTick;
    }
}
