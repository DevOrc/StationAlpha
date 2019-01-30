package com.noahcharlton.stationalpha.block.door;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Objects;
import java.util.Optional;

public class DoorContainer extends BlockContainer {

    enum Orientation {NORTH_SOUTH, EAST_WEST}

    private Orientation orientation;

    public DoorContainer(Tile tile, Block block) {
        super(tile, block);

        orientation = Orientation.EAST_WEST;
    }

    @Override
    public void onBlockUpdate() {
        World world = getTile().getWorld();
        int x = getTile().getX();
        int y = getTile().getY();

        Optional<Tile> tileWest = world.getTileAt(x - 1, y);
        Optional<Tile> tileEast= world.getTileAt(x + 1, y);

        if(tileEast.isPresent() && tileWest.isPresent()){
            if(tileEast.get().getBlock().isPresent() && tileWest.get().getBlock().isPresent())
                orientation = Orientation.EAST_WEST;
            else
                orientation = Orientation.NORTH_SOUTH;
        }else{
            orientation = Orientation.NORTH_SOUTH;
        }
    }

    public void setOrientation(Orientation orientation) {
        Objects.requireNonNull(orientation, "Orientation cannot be null!");

        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
