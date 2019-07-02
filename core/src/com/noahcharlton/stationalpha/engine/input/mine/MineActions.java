package com.noahcharlton.stationalpha.engine.input.mine;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildAction;
import com.noahcharlton.stationalpha.item.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MineActions {

    private static List<BuildAction> actions = Collections.emptyList();

    public static void init() {
        actions = Arrays.asList(
                new MineAction.Builder()
                        .setBlock(Blocks.getIce())
                        .setOutput(Item.SPACE_ROCK)
                        .setOutputAmount(3)
                        .build(),
                new MineAction.Builder()
                        .setBlock(Blocks.getTreeBlock())
                        .setOutput(Item.WOOD)
                        .setOutputAmount(3)
                        .setDisplayName("Cut Tree")
                        .build()
        );
    }

    public static List<BuildAction> getActions() {
        return actions;
    }
}
