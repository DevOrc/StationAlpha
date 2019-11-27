package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PoweredBlockContainerTest {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private final TestPoweredBlockContainer container = new TestPoweredBlockContainer(tile);

    @BeforeEach
    void setUp() {
        tile.setBlock(Blocks.getTreeBlock(), container);
    }

    @Test
    void hasPowerExactAmountTest() {
        container.setPowerPerTick(0);

        Assertions.assertTrue(container.hasPower());
    }

    @Test
    void hasPowerMoreThanAmountTest() {
        container.setPowerPerTick(5);
        world.getPowerNetwork().changePower(25);

        Assertions.assertTrue(container.hasPower());
    }

    @Test
    void hasPowerLessThanAmountTest() {
        container.setPowerPerTick(10);

        Assertions.assertFalse(container.hasPower());
    }

    @Test
    void getDebugInfoContainsInfoTest() {
        world.getPowerNetwork().changePower(12);

        List<String> debugInfo = Arrays.asList(container.getDebugInfo());

        String expected = "Network: 12 / 1000";
        Assertions.assertTrue(debugInfo.contains(expected));
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
