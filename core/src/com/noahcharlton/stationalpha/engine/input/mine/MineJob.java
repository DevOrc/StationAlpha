package com.noahcharlton.stationalpha.engine.input.mine;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MineJob extends TickBasedJob {

    static final int TICKS = 120;
    private static final Logger logger = LogManager.getLogger(MineJob.class);

    private final Tile blockTile;
    private final Item outputItem;
    private final int outputAmount;

    MineJob(Tile blockTile, Tile openBlock, Item outputItem, int outputAmount) {
        super(openBlock, TICKS);

        this.blockTile = blockTile;
        this.outputItem = outputItem;
        this.outputAmount = outputAmount;
    }

    @Override
    public void start() {
        super.start();
        logger.debug("Starting mining job!");
    }

    @Override
    public void finish() {
        super.finish();
        logger.debug("Finished mining job!");

        blockTile.getWorld().getInventory().changeAmountForItem(outputItem, outputAmount);

        BuildBlock block = new BuildBlock(Blocks.getWall());
        block.onClick(blockTile, Input.Buttons.RIGHT);
    }
}
