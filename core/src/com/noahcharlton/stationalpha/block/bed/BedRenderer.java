package com.noahcharlton.stationalpha.block.bed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.DefaultBlockRenderer;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.worker.SleepJob;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class BedRenderer extends DefaultBlockRenderer {

    private static final int UNOCCUPIED_Y = 0;
    private static final int OCCUPIED_Y = 32;

    private final BitmapFont font = GuiComponent.getFont();

    public BedRenderer(Block block) {
        super(block);
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        if(!getContainer(tile).getTile().equals(tile)) {
            return;
        }

        BedContainer bedContainer = (BedContainer) getContainer(tile);


        if(bedContainer.getWorker().isPresent()) {
            drawTexture(batch, tile, bedContainer.getWorker().get());
            drawName(batch, tile, bedContainer);
        }else{
            drawPartialTexture(batch, tile, UNOCCUPIED_Y);
        }
    }

    private void drawTexture(SpriteBatch batch, Tile tile, Worker worker) {
        if(isBedOccupied(worker)) {
            drawPartialTexture(batch, tile, OCCUPIED_Y);
        } else {
            drawPartialTexture(batch, tile, UNOCCUPIED_Y);
        }
    }

    boolean isBedOccupied(Worker worker) {
        if(worker == null)
            return false;

        Optional<Job> currentJob = worker.getAi().getJobManager().getCurrentJob();
        Optional<Tile> adjacent = worker.getBedroom().flatMap(bed -> bed.getTile().getOpenAdjecent());

        if(currentJob.filter(this::isInProgressSleep).isPresent() && adjacent.isPresent()) {
            return true;
        }

        return false;
    }

    private boolean isInProgressSleep(Job job) {
        return job instanceof SleepJob && job.getStage() == Job.JobStage.IN_PROGRESS;
    }

    private void drawPartialTexture(SpriteBatch batch, Tile tile, int srcY) {
        BlockContainer container = getContainer(tile);
        Texture texture = Blocks.getBedBlock().getTexture().get().get();

        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;
        int width = 64;
        int height = 32;
        int containerWidth = container.getWidth();
        int rotation = container.getRotation().getDegrees();

        DefaultBlockRenderer.drawRotated(batch, texture, x, y, rotation, containerWidth, width, height,
                0, srcY, 0, 0);
    }

    private void drawName(SpriteBatch batch, Tile tile, BedContainer bedContainer) {
        String name = bedContainer.getWorker().get().getName();
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;
        int pillowSize = 20;

        font.setColor(Color.WHITE);
        font.getData().setScale(.22f);

        switch(bedContainer.getRotation()) {
            case NORTH:
                font.draw(batch, name, x + pillowSize, y + (Tile.TILE_SIZE * 3 / 4),
                        (Tile.TILE_SIZE * 2) - pillowSize, Align.center, true);
                break;
            case WEST:
                font.draw(batch, name, x, y + (Tile.TILE_SIZE * 5 / 3 - pillowSize),
                        Tile.TILE_SIZE, Align.center, true);
                break;
            case SOUTH:
                font.draw(batch, name, x, y + (Tile.TILE_SIZE * 3 / 4),
                        (Tile.TILE_SIZE * 2) - pillowSize, Align.center, true);
                break;
            case EAST:
                font.draw(batch, name, x, y + (Tile.TILE_SIZE * 5 / 3),
                        Tile.TILE_SIZE, Align.center, true);
                break;
        }

    }
}
