package com.noahcharlton.stationalpha.block.composter;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class ComposterJobTests extends JobTests{

    protected static final ManufacturingRecipe recipe = new ManufacturingRecipe(Item.LEAVES.stack(4),
            Item.DIRT.stack(5), 180, RecipeType.COMPOST);

    protected World world;

    protected Tile tile;
    protected ComposterContainer container;

    public void init(){
        world = new World();
        tile = world.getTileAt(1, 1).get();
        tile.setBlock(Blocks.getComposter());

        container = (ComposterContainer) tile.getContainer().get();
    }

    @Override
    protected void jobStartBasicTest() {
        job.start();

        Assertions.assertEquals(job.getStage(), Job.JobStage.FINISHED);
    }

    static class StartJobTests extends ComposterJobTests{

        @Override
        public Job getJob() {
            init();

            return new CompostJob.StartCompostJob(container, tile, recipe);
        }
    }

    static class EndJobTests extends ComposterJobTests{

        @Test
        void addProductsToInventoryOnFinish() {
            job.finish();

            Assertions.assertEquals(5, world.getInventory().getAmountForItem(Item.DIRT));
        }

        @Override
        public Job getJob() {
            init();

            return new CompostJob.EndCompostJob(container, tile, recipe);
        }
    }

}
