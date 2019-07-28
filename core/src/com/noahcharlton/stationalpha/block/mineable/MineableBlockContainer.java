package com.noahcharlton.stationalpha.block.mineable;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.engine.input.mine.MineJob;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class MineableBlockContainer extends BlockContainer {

    private Optional<MineJob> currentJob;

    public MineableBlockContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);

        currentJob = Optional.empty();
    }

    @Override
    public void onDestroy() {
        if(currentJob.filter(job -> job.getStage() != Job.JobStage.FINISHED).isPresent())
            currentJob.ifPresent(Job::cancel);
    }

    public void setCurrentJob(MineJob currentJob) {
        this.currentJob = Optional.of(currentJob);
    }

    public Optional<MineJob> getCurrentJob() {
        return currentJob;
    }
}
