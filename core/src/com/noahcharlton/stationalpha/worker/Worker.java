package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Worker {

    private static final Logger logger = LogManager.getLogger(Worker.class);
    private final World world;
    private final WorkerAI ai;
    private final String name;

    protected int pixelX;
    protected int pixelY;
    private boolean dead = false;

    public Worker(String name, World world) {
        this.name = name;
        this.world = world;

        this.pixelX = 0;
        this.pixelY = 0;

        this.ai = new WorkerAI(this);
    }

    public void update(){
        ai.update();
    }

    public void die(String reason){
        logger.debug("Worker Died ({}): {}", name, reason);
        dead = true;
    }

    public static Worker create(World world){
        return new Worker("John Smith", world);
    }

    public void setPixelX(int pixelX) {
        this.pixelX = trimPositionToInsideWorld(pixelX);
    }

    public void setPixelY(int pixelY) {
        this.pixelY = trimPositionToInsideWorld(pixelY);
    }

    public Tile getTileOn(){
        return world.getTileAt(getPixelX() / Tile.TILE_SIZE, getPixelY() / Tile.TILE_SIZE)
                .orElseThrow(() -> new GdxRuntimeException("Worker not in world!"));
    }

    private static int trimPositionToInsideWorld(int pos){
        if(pos > World.WORLD_PIXEL_SIZE)
            return World.WORLD_PIXEL_SIZE - 1;

        else if(pos < 0)
            return 0;

        return pos;
    }

    public World getWorld() {
        return world;
    }

    public int getPixelX() {
        return pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public String getName() {
        return name;
    }

    public WorkerAI getAi() {
        return ai;
    }

    public boolean isDead() {
        return dead;
    }
}
