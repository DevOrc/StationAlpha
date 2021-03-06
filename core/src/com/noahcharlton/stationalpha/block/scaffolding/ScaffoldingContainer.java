package com.noahcharlton.stationalpha.block.scaffolding;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.Optional;

public class ScaffoldingContainer extends BlockContainer {

    private Block blockToBuild;
    private Optional<ScaffoldingJob> currentJob = Optional.empty();

    public ScaffoldingContainer(Tile tile, Block blockToBuild, BlockRotation rotation) {
        super(tile, Blocks.getScaffoldingBlock(), rotation);

        this.blockToBuild = blockToBuild;
    }

    @Override
    public void onUpdate() {
        if(blockToBuild == null)
            return;

        if(!currentJob.isPresent())
            createJob();

        if(currentJob.isPresent()){
            Job job = currentJob.get();
            Tile target = job.getTarget();

            if(target.hasNonPassableBlock()){
                JobQueue.getInstance().getJobQueue(job.getRequiredRole()).remove(job);
                currentJob = Optional.empty();
            }
        }
    }

    private void createJob() {
        currentJob = getTile().getOpenAdjecent().map(adjacent -> {
            ScaffoldingJob job = new ScaffoldingJob(adjacent, this);

            JobQueue.getInstance().addJob(job);

            return job;
        });
    }

    void finishBuilding() {
        BlockContainer container = blockToBuild.createContainer(getTile(), getRotation());
        World world = getTile().getWorld();
        int rootX = getTile().getX();
        int rootY = getTile().getY();

        for(int x = rootX; x < rootX + container.getWidth(); x++) {
            for(int y = rootY; y < rootY + container.getHeight(); y++) {
                world.getTileAt(x, y).get().setBlock(blockToBuild, container);
            }
        }
    }

    @Override
    public void onDestroy() {
        currentJob.filter(job -> job.getStage() != Job.JobStage.FINISHED).ifPresent(job -> {
            Inventory inventory = getTile().getWorld().getInventory();

            for(ItemStack requirement: blockToBuild.getRequirements()){
                inventory.changeAmountForItem(requirement.getItem(), requirement.getAmount());
            }

            job.permanentEnd();
        });

        //This will prevent the items from getting replaced twice
        //This happens because onDestroy is called for each tile (i.e. its called
        //twice for beds because it takes up two tiles)
        currentJob = Optional.empty();
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        writer.element("Block", blockToBuild.getID());
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        String blockID = element.get("Block");

        blockToBuild = Blocks.getByID(blockID)
                .orElseThrow(() -> new GdxRuntimeException("Unknown block id: " + blockID));
    }

    @Override
    public String[] getDebugInfo() {
        if(blockToBuild == null)
            return super.getDebugInfo();
        return super.combineDebugInfo("Block: " + blockToBuild.getDisplayName());
    }

    public int getWidth(){
        if(getRotation() == BlockRotation.NORTH || getRotation() == BlockRotation.SOUTH)
            return blockToBuild.getDimensionedWidth();

        return blockToBuild.getDimensionedHeight();
    }

    public int getHeight(){
        if(getRotation() == BlockRotation.NORTH || getRotation() == BlockRotation.SOUTH)
            return blockToBuild.getDimensionedHeight();

        return blockToBuild.getDimensionedWidth();
    }

    public Optional<ScaffoldingJob> getCurrentJob() {
        return currentJob;
    }

    public Block getBlockToBuild() {
        return blockToBuild;
    }
}
