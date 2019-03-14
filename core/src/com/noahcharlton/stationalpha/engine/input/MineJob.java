package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MineJob extends TickBasedJob {

    static final int TICKS = 120;
    private static final Logger logger = LogManager.getLogger(MineJob.class);

    private final Inventory inventory;
    private final Tile rockTile;

    public MineJob(Tile rockTile, Tile openBlock, Inventory inventory) {
        super(openBlock, TICKS);

        this.inventory = inventory;
        this.rockTile = rockTile;
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

        inventory.changeAmountForItem(Item.SPACE_ROCK, 3);
        rockTile.setBlock(null);
    }
}
