package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;

public class WorkbenchJob extends TickBasedJob {

    WorkbenchJob(Tile target) {
        super(target, 120);
    }

    @Override
    public void finish() {
        super.finish();

        getTarget().getWorld().getInventory().changeAmountForItem(Item.TEST_ITEM, 1);
    }
}
