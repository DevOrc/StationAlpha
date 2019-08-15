package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class PowerProducerContainer extends BlockContainer implements PoweredContainer {

    private final int powerPerTick;

    public PowerProducerContainer(Tile tile, Block block, BlockRotation rotation, int powerPerTick) {
        super(tile, block, rotation);

        this.powerPerTick = powerPerTick;
    }

    @Override
    public void onUpdate() {
        Tile root = getTile();
        int power = powerPerTick;

        for(int x = 0; x < getWidth(); x++){
            for(int y = 0; y < getHeight(); y++){
                Optional<Tile> tile = root.getWorld().getTileAt(x + root.getX(), y + root.getY());

                if(tile.isPresent()){
                    power -= transferPower(power, tile.get());
                }

                if(power == 0)
                    return;
            }
        }
    }

    private int transferPower(int power, Tile tile) {
        int powerTransferred = Tile.MAX_POWER - tile.getPower();

        powerTransferred = Math.min(power, powerTransferred);

        tile.setPower(tile.getPower() + powerTransferred);

        return powerTransferred;
    }
}
