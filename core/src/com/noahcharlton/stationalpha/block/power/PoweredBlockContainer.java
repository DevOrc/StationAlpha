package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public abstract class PoweredBlockContainer extends BlockContainer implements PoweredContainer{

    public PoweredBlockContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    public abstract int getPowerPerTick();

    public boolean hasPower(){
        World world = getTile().getWorld();
        int power = getPowerPerTick();

        for(int x = 0; x < this.getWidth(); x++){
            for(int y = 0; y < this.getHeight(); y++){
                Optional<Tile> tile = world.getTileAt(x + getTile().getX(), y + getTile().getY());

                power -= tile.map(Tile::getPower).orElse(0);
            }
        }

        return power < 0;
    }

    public boolean usePower(){
        if(!hasPower())
            return false;

        World world = getTile().getWorld();
        int power = getPowerPerTick();

        for(int x = 0; x < this.getWidth(); x++){
            for(int y = 0; y < this.getHeight(); y++){
                Optional<Tile> tileOpt = world.getTileAt(x + getTile().getX(), y + getTile().getY());

                if(tileOpt.isPresent()){
                    Tile tile = tileOpt.get();

                    power -= removePower(power, tile);
                }

                if(power <= 0)
                    return true;
            }
        }

        //Code should never reach this point
        throw new UnsupportedOperationException("");
    }

    int removePower(int amount, Tile tile){
        int powerToRemove = Math.min(amount, tile.getPower());

        tile.setPower(tile.getPower() - powerToRemove);

        return powerToRemove;
    }
}
