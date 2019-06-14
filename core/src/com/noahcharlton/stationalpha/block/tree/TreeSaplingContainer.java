package com.noahcharlton.stationalpha.block.tree;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeSaplingContainer extends BlockContainer {

    private static final int BASE_GROWTH_TIME = 6000;
    private static final int RANDOM_GROWTH_BOUND = 4000;

    private static final Random random = new Random();

    private int tick;

    public TreeSaplingContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);

        tick = BASE_GROWTH_TIME + random.nextInt(RANDOM_GROWTH_BOUND);
    }

    @Override
    public void onUpdate() {
        if(tick > 0)
            tick--;
        else
            createTree();

        if(DebugKeys.isDebugPressed(DebugKeys.MAGICAL_GROWTH)){
            tick = 0;
        }
    }

    void createTree() {
        List<Tile> tiles = getTilesAdjacentWithDiagonals();

        if(allTilesOpen(tiles) && allAdjacentTilesPresent(tiles)){

            BuildBlock blockBuilder = new BuildBlock(Blocks.getTreeBlock());

            this.getTile().setBlock(null);

            blockBuilder.onClick(getBottomLeftCornerTile(), Input.Buttons.LEFT);
        }
    }

    boolean allTilesOpen(List<Tile> tiles) {
        return tiles.stream().allMatch(tile -> !tile.getBlock().isPresent());
    }

    boolean allAdjacentTilesPresent(List<Tile> tiles) {
        return tiles.size() == 8;
    }

    Tile getBottomLeftCornerTile() {
        World world = getTile().getWorld();

        return world.getTileAt(getTile().getX() - 1, getTile().getY() - 1).get();
    }

    List<Tile> getTilesAdjacentWithDiagonals() {
        World world = getTile().getWorld();
        List<Tile> tiles = new ArrayList<>();

        int x = getTile().getX();
        int y = getTile().getY();

        world.getTileAt(x - 1, y).ifPresent(tiles::add);
        world.getTileAt(x + 1, y).ifPresent(tiles::add);
        world.getTileAt(x, y - 1).ifPresent(tiles::add);
        world.getTileAt(x, y + 1).ifPresent(tiles::add);
        world.getTileAt(x - 1, y - 1).ifPresent(tiles::add);
        world.getTileAt(x - 1, y + 1).ifPresent(tiles::add);
        world.getTileAt(x + 1, y - 1).ifPresent(tiles::add);
        world.getTileAt(x + 1, y + 1).ifPresent(tiles::add);

        return tiles;
    }

    void setTick(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }
}
