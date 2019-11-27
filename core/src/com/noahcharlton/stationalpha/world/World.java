package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildAction;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.PlaceConduit;
import com.noahcharlton.stationalpha.science.ScienceManager;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRenderer;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.load.SaveGameLoader;
import com.noahcharlton.stationalpha.world.save.SaveGame;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class World {

    public static final int WORLD_TILE_SIZE = 150;
    public static final int WORLD_PIXEL_SIZE = WORLD_TILE_SIZE * Tile.TILE_SIZE;

    private final Tile[][] tiles = new Tile[WORLD_TILE_SIZE][WORLD_TILE_SIZE];
    private final ArrayList<Worker> workers = new ArrayList<>();
    private final Inventory inventory = new Inventory();
    private final ManufacturingManager manufacturingManager = new ManufacturingManager();
    private final ScienceManager scienceManager = new ScienceManager(this);

    /**
     * Used for testing
     */
    public World() {
        this(false);
    }

    public World(boolean generate) {
        fillTiles();
        addStartingWorkers();

        if(generate){
            generateWorld();
            giveStartingItems();
        }
    }

    private void addStartingWorkers() {
        for(int i = 0; i < 2; i++){
            Worker worker = Worker.create(this);
            worker.setPixelX(worker.getPixelX() + (i * 2 * Tile.TILE_SIZE));
            worker.setPixelY(worker.getPixelY() + (-i * 2 * Tile.TILE_SIZE));

            for(WorkerRole role: WorkerRole.values()){
                worker.addRole(role);
            }

            workers.add(worker);
        }
    }

    private void giveStartingItems() {
        inventory.changeAmountForItem(Item.POTATO, 115);
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

    public void save(int saveNumber){
        SaveGame.create(this, saveNumber);
    }

    public void load(int saveNumber) {
        this.workers.clear();

        SaveGameLoader.load(this, saveNumber);
    }

    public void render(SpriteBatch spriteBatch) {
        renderTiles(spriteBatch);
        renderWorkers(spriteBatch);
    }

    public void update() {
        updateTiles();
        updateWorkers();
        scienceManager.update();

        if(DebugKeys.isDebugPressed(DebugKeys.INVENTORY)){
            fillInventory();
        }
    }

    void fillInventory() {
        for(Item item: Item.values()){
            inventory.changeAmountForItem(item, 1);
        }
    }

    void updateWorkers() {
        workers.forEach(Worker::update);
        workers.removeIf(Worker::isDead);
    }

    void updateTiles() {
        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                tiles[x][y].updateOxygen();

                updateContainer(tiles[x][y]);
            }
        }
    }

    private void updateContainer(Tile tile) {
        if(!tile.getContainer().isPresent())
            return;

        BlockContainer container = tile.getContainer().get();

        if(container.getTile().equals(tile)){
            container.onUpdate();
        }
    }

    private void renderWorkers(SpriteBatch spriteBatch) {
        workers.forEach(worker -> WorkerRenderer.render(spriteBatch, worker));
    }

    private void renderTiles(SpriteBatch spriteBatch) {
        drawFloors(spriteBatch);

        if(conduitViewMode()){
            drawBlocks(spriteBatch);
            ConduitRenderer.render(this, spriteBatch);
        }else{
            ConduitRenderer.render(this, spriteBatch);
            drawBlocks(spriteBatch);
        }
    }

    boolean conduitViewMode() {
        Optional<BuildAction> buildAction = InputHandler.getInstance().getBuildManager().getAction();

        return buildAction.filter(action -> action instanceof PlaceConduit).isPresent();
    }

    private void drawBlocks(SpriteBatch spriteBatch) {
        boolean drawOxygen = DebugKeys.isDebugPressed(DebugKeys.OXYGEN_LEVEL);

        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                Tile tile = tiles[x][y];

                tile.getBlock().ifPresent(block -> block.render(spriteBatch, tile));

                if(drawOxygen)
                    tile.drawOxygen(spriteBatch);
            }
        }
    }

    private void drawFloors(SpriteBatch spriteBatch) {
        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                Tile tile = tiles[x][y];

                tile.getFloor().ifPresent(floor -> floor.render(spriteBatch, tile));
            }
        }
    }

    public ManufacturingManager getManufacturingManager() {
        return manufacturingManager;
    }

    public Optional<Tile> getTileAt(int x, int y){
        if(x < 0 || y < 0 || x >= WORLD_TILE_SIZE || y >= WORLD_TILE_SIZE)
            return Optional.empty();

        return Optional.of(tiles[x][y]);
    }

    public ScienceManager getScienceManager() {
        return scienceManager;
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
