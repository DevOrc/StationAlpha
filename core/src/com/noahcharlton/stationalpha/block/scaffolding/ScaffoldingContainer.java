package com.noahcharlton.stationalpha.block.scaffolding;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class ScaffoldingContainer extends BlockContainer {

    private final Block blockToBuild;
    private Optional<ScaffoldingJob> currentJob = Optional.empty();

    public ScaffoldingContainer(Tile tile, Block blockToBuild, BlockRotation rotation) {
        super(tile, Blocks.getScaffoldingBlock(), rotation);

        this.blockToBuild = blockToBuild;
    }

    @Override
    public void onUpdate() {
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
            job.cancel();

            JobQueue.getInstance().getJobQueue(job.getRequiredRole()).remove(job);
        });
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
}
