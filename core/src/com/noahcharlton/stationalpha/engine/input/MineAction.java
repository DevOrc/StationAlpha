package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.ArrayList;
import java.util.Optional;

public class MineAction implements BuildAction{

    private final Inventory inventory;
    private final JobQueue jobQueue;

    public MineAction(Inventory inventory, JobQueue jobQueue) {
        this.inventory = inventory;
        this.jobQueue = jobQueue;
    }

    @Override
    public void onClick(Tile tile, int button) {
        boolean leftClick = button == Input.Buttons.LEFT;
        boolean clickedOnRock = tile.getBlock().map(block -> block == Blocks.getIce()).orElse(false);

        if(leftClick && clickedOnRock){
            getAdjacentTile(tile).ifPresent(adjacent -> createJob(tile, adjacent));
        }
    }

    void createJob(Tile rockTile, Tile adjacentTile) {
        jobQueue.addJob(new MineJob(rockTile, adjacentTile, inventory));
    }

    static Optional<Tile> getAdjacentTile(Tile root) {
        ArrayList<Tile> adjacentTiles = root.getAdjacent();

        for(Tile tile : adjacentTiles){
            if(!tile.getBlock().isPresent())
                return Optional.of(tile);
        }

        return Optional.empty();
    }

    @Override
    public String getName() {
        return "Mine Action";
    }
}
