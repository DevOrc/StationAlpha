package com.noahcharlton.stationalpha.engine.input.mine;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.input.BuildAction;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MineAction implements BuildAction {

    private final String displayName;
    private final JobQueue jobQueue;
    private final Block input;
    private final List<Item> output;
    private final int outputAmount;

    public MineAction(Builder builder) {
        this.jobQueue = builder.jobQueue;
        this.output = builder.output;
        this.input = builder.block;
        this.outputAmount = builder.outputAmount;
        this.displayName = builder.displayName.orElse("Mining " + input.getDisplayName());
    }

    @Override
    public void onClick(Tile tile, int button) {
        boolean leftClick = button == Input.Buttons.LEFT;
        boolean clickedOnBlock = tile.getBlock().map(block -> block == input).orElse(false);

        if(leftClick && clickedOnBlock){
            Tile rootTile = tile.getContainer().get().getTile();

            rootTile.getOpenAdjecent().ifPresent(adjacent -> createJob(tile, adjacent));
        }
    }

    void createJob(Tile rockTile, Tile adjacentTile) {
        jobQueue.addJob(new MineJob(rockTile, adjacentTile, output, outputAmount));
    }

    @Override
    public String getName() {
        return displayName;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static class Builder{

        private Optional<String> displayName = Optional.empty();
        private JobQueue jobQueue = JobQueue.getInstance();
        private Block block;
        private List<Item> output;
        private int outputAmount;

        public MineAction build(){
            checkArgs();

            return new MineAction(this);
        }

        private void checkArgs() {
            Objects.requireNonNull(block, "Block parameter was not set");
            Objects.requireNonNull(jobQueue, "JobQueue parameter cannot be null");
            Objects.requireNonNull(output, "Output parameter was not set");

            if(outputAmount == 0)
                throw new GdxRuntimeException("Output Amount was not set!");
        }

        public Builder setOutputAmount(int outputAmount) {
            this.outputAmount = outputAmount;

            return this;
        }

        public Builder setBlock(Block block) {
            this.block = block;

            return this;
        }

        public Builder setJobQueue(JobQueue jobQueue) {
            this.jobQueue = jobQueue;

            return this;
        }

        public Builder setOutput(Item... output) {
            this.output = Arrays.asList(output);

            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = Optional.of(displayName);

            return this;
        }
    }
}
