package com.noahcharlton.stationalpha.block.dust;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SynthesizerJobTests extends JobTests {

    private static final ManufacturingRecipe recipe = new ManufacturingRecipe(Item.SPACE_DUST, 1,
            Item.SPACE_ROCK, 3, 125, RecipeType.SYNTHESIZE);

    private final World world = new World();

    @Test
    void jobLengthMatchesRecipeTimeTest() {
        TickBasedJob job = (TickBasedJob) getJob();

        Assertions.assertEquals(125, job.getJobDuration());
    }

    @Test
    void tickDoesNotResetOnCancelRestartTest() {
        TickBasedJob job = (TickBasedJob) getJob();

        for(int i = 0; i < 5; i++){
            job.update();
        }
        job.cancel();
        job.start();

        Assertions.assertEquals(5, job.getTick());
    }

    @Test
    void addProductsOnFinishTest() {
        Inventory inventory = getJob().getTarget().getWorld().getInventory();

        getJob().finish();

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.SPACE_ROCK));
    }

    @Override
    public Job getJob() {
        Tile tile = world.getTileAt(0, 0).get();
        BuildBlock buildBlock = new BuildBlock(Blocks.getSynthesizer());
        buildBlock.onClick(tile, Input.Buttons.LEFT);

        return new SynthesizerJob(tile, recipe);
    }
}
