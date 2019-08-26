package com.noahcharlton.stationalpha.engine.input.mine;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.WorkerRole;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MineActions {

    private static List<MineAction> actions = Collections.emptyList();

    public static void init() {
        actions = Arrays.asList(
                new MineAction.Builder()
                        .setBlock(Blocks.getIce())
                        .setOutput(Item.SPACE_ROCK)
                        .setDisplayName("Mine Space Rock")
                        .setOutputAmount(3)
                        .build(),
                new MineAction.Builder()
                        .setBlock(Blocks.getTreeBlock())
                        .setOutput(Item.WOOD, Item.LEAVES, Item.LEAVES)
                        .setOutputAmount(8)
                        .setDisplayName("Cut Tree")
                        .setRole(WorkerRole.GARDENER)
                        .build()
        );
    }

    public static List<MineAction> getActions() {
        return actions;
    }
}
