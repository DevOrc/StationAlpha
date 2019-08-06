package com.noahcharlton.stationalpha.block;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BlocksTests {

    @Test
    void getBlockByIDBasicTest() {
        Optional<Block> block = Blocks.getByID("wall");

        Assertions.assertEquals(Blocks.getWall(), block.get());
    }

    @Test
    void getBlockByIDNoBlockTest() {
        Optional<Block> block = Blocks.getByID("fooBar");

        Assertions.assertFalse(block.isPresent());
    }
}
