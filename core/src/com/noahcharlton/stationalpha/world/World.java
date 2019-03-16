package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRenderer;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class World {

    public static final int WORLD_TILE_SIZE = 150;
    public static final int WORLD_PIXEL_SIZE = WORLD_TILE_SIZE * Tile.TILE_SIZE;

    private final Tile[][] tiles = new Tile[WORLD_TILE_SIZE][WORLD_TILE_SIZE];
    private final ArrayList<Worker> workers = new ArrayList<>();
    private final Inventory inventory = new Inventory();

    public World() {
        this(false);
    }

    public World(boolean generate) {
        fillTiles();

        workers.add(Worker.create(this));

        if(generate)
            generateWorld();
    }

    public void triggerWorldUpdate(int x, int y){
        getTileAt(x, y).ifPresent(Tile::onWorldUpdate);
        getTileAt(x - 1, y).ifPresent(Tile::onWorldUpdate);
        getTileAt(x + 1, y).ifPresent(Tile::onWorldUpdate);
        getTileAt(x, y - 1).ifPresent(Tile::onWorldUpdate);
        getTileAt(x, y + 1).ifPresent(Tile::onWorldUpdate);
    }

    private void fillTiles() {
        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                tiles[x][y] = new Tile(x, y, this);
            }
        }
    }

    private void generateWorld() {
        Random random = new Random();

        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                if(random.nextInt(25) == 0){
                    tiles[x][y].setBlock(Blocks.getIce());
                }
            }
        }
    }

    public void render(SpriteBatch spriteBatch) {
        renderTiles(spriteBatch);
        renderWorkers(spriteBatch);

        update();
    }

    private void update() {
        updateTiles();
        workers.forEach(Worker::update);

        if(DebugKeys.isDebugPressed(DebugKeys.INVENTORY)){
            inventory.changeAmountForItem(Item.TEST_ITEM, 1);
        }else{
            inventory.changeAmountForItem(Item.TEST_ITEM, -1);
        }
    }

    private void updateTiles() {
        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                tiles[x][y].updateOxygen();
                tiles[x][y].getContainer().ifPresent(BlockContainer::onUpdate);
            }
        }
    }

    private void renderWorkers(SpriteBatch spriteBatch) {
        workers.forEach(worker -> WorkerRenderer.render(spriteBatch, worker));
    }

    private void renderTiles(SpriteBatch spriteBatch) {
        boolean drawOxygen = DebugKeys.isDebugPressed(DebugKeys.OXYGEN_LEVEL);

        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                Tile tile = tiles[x][y];

                tile.getFloor().ifPresent(floor -> floor.render(spriteBatch, tile));
                tile.getBlock().ifPresent(block -> block.render(spriteBatch, tile));

                if(drawOxygen)
                    tile.drawOxygen(spriteBatch);
            }
        }
    }

    public Optional<Tile> getTileAt(int x, int y){
        if(x < 0 || y < 0 || x >= WORLD_TILE_SIZE || y >= WORLD_TILE_SIZE)
            return Optional.empty();

        return Optional.of(tiles[x][y]);
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public static Optional<World> getInstance(){
        return StationAlpha.getInstance().getWorld();
    }
}
