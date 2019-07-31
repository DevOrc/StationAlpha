package com.noahcharlton.stationalpha.engine.input.mine;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.engine.input.BuildAction;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

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
            BlockContainer container = tile.getContainer().get();

            getOpenAdjacent(container).ifPresent(adjacent -> createJob(tile, adjacent));
        }
    }

    private Optional<Tile> getOpenAdjacent(BlockContainer container) {
        Tile rootTile = container.getTile();
        World world = rootTile.getWorld();

        for(int x = 0; x < container.getWidth(); x++){
            for(int y = 0; y < container.getHeight(); y++){
                if(isEdgeOfContainer(container, x, y)){
                    Optional<Tile> tile = world.getTileAt(x + rootTile.getX(), y + rootTile.getY());
                    System.out.println(tile);
                    Optional<Tile> adjacent = tile.flatMap(Tile::getOpenAdjecent);

                    if(adjacent.isPresent())
                        return adjacent;
                }
            }
        }

        return Optional.empty();
    }

    static boolean isEdgeOfContainer(BlockContainer container, int x, int y) {
        return x == 0 || y ==0 || x + 1 == container.getWidth() || y + 1 == container.getHeight();
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
