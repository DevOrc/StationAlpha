package com.noahcharlton.stationalpha.block.mineable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.engine.input.mine.MineAction;
import com.noahcharlton.stationalpha.engine.input.mine.MineActions;
import com.noahcharlton.stationalpha.engine.input.mine.MineJob;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.Optional;

public class MineableBlockContainer extends BlockContainer {

    private Optional<MineJob> currentJob;

    public MineableBlockContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);

        currentJob = Optional.empty();
    }

    @Override
    public void onDestroy() {
        currentJob.filter(job -> job.getStage() != Job.JobStage.FINISHED).ifPresent(Job::permanentEnd);
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        writer.element("MarkedForMining", currentJob.isPresent());
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        boolean markedForMining = element.getBoolean("MarkedForMining");

        if(markedForMining) {
            if(Gdx.app != null)
                Gdx.app.postRunnable(this::createJob);
            else createJob();
        }
    }

    private void createJob() {
        for(MineAction action : MineActions.getActions()) {
            if(action.getInput().equals(this.getBlock())) {
                action.createJob(this.getTile(), this);
            }
        }
    }

    public void setCurrentJob(MineJob currentJob) {
        this.currentJob = Optional.of(currentJob);
    }

    public Optional<MineJob> getCurrentJob() {
        return currentJob;
    }
}
