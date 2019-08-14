package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.bed.BedContainer;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;
import java.util.Optional;

public class Worker {

    private static final Logger logger = LogManager.getLogger(Worker.class);
    private final World world;
    private final WorkerAI ai;
    private final String name;
    private Optional<BedContainer> bedroom = Optional.empty();


    private final EnumSet<WorkerRole> roles = EnumSet.noneOf(WorkerRole.class);

    protected int pixelX;
    protected int pixelY;
    private boolean dead = false;

    public Worker(String name, World world) {
        this.name = name;
        this.world = world;

        this.pixelX = World.WORLD_PIXEL_SIZE / 2;
        this.pixelY = World.WORLD_PIXEL_SIZE / 2;

        this.ai = new WorkerAI(this);
        addRole(WorkerRole.GENERAL);
    }

    public void update(){
        ai.update();
    }

    public void die(String reason){
        ai.getJobManager().setCurrentJob(null);
        logger.debug("Worker Died ({}): {}", name, reason);
        MessageQueue.getInstance().add("Worker Died!", reason);

        dead = true;
    }

    public static Worker create(World world){
        return new Worker(WorkerNameGenerator.generate(world), world);
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

    public void setBedroom(BedContainer bedroom) {
        this.bedroom = Optional.ofNullable(bedroom);
    }

    public Optional<BedContainer> getBedroom() {
        return bedroom;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addRole(WorkerRole role){
        roles.add(role);
    }

    public void removeRole(WorkerRole role){
        roles.remove(role);
    }

    public EnumSet<WorkerRole> getRoles() {
        return roles;
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
