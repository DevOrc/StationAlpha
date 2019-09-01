package com.noahcharlton.stationalpha.engine.input.mine;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.mineable.MineableBlockContainer;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MineJob extends TickBasedJob {

    static final int TICKS = 120;
    private static final Logger logger = LogManager.getLogger(MineJob.class);

    private final WorkerRole role;
    private final Tile blockTile;
    private final List<Item> outputItems;
    private final int outputAmount;

    public MineJob(Tile blockTile, Tile openBlock, List<Item> outputItems, int outputAmount, WorkerRole role) {
        super(openBlock, TICKS);

        this.blockTile = blockTile;
        this.outputItems = outputItems;
        this.outputAmount = outputAmount;
        this.role = role;

        setBlockMineState(blockTile);
    }

    void setBlockMineState(Tile blockTile) {
        if(blockTile.getContainer().isPresent()){
            BlockContainer blockContainer = blockTile.getContainer().get();

            if(blockContainer instanceof MineableBlockContainer){
                ((MineableBlockContainer) blockContainer).setCurrentJob(this);
            }else{
                throw new GdxRuntimeException("Cannot mine block with container of type: " + blockContainer);
            }
        }else{
            throw new GdxRuntimeException("Cannot mine a block when there is no container!");
        }
    }

    @Override
    public WorkerRole getRequiredRole() {
        return role;
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

        Inventory inventory = blockTile.getWorld().getInventory();
        outputItems.forEach(item -> inventory.changeAmountForItem(item, outputAmount));

        BuildBlock block = new BuildBlock(Blocks.getWall());
        block.onClick(blockTile, Input.Buttons.RIGHT);
    }

    @Override
    public String toString() {
        return "Mining: " + super.toString();
    }
}
