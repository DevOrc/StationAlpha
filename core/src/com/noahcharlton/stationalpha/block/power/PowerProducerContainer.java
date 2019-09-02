package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;
import java.util.Random;

public class PowerProducerContainer extends BlockContainer implements PoweredContainer {

    private static final Random random = new Random();
    private final int powerPerTick;

    public PowerProducerContainer(Tile tile, Block block, BlockRotation rotation, int powerPerTick) {
        super(tile, block, rotation);

        this.powerPerTick = powerPerTick;
    }

    @Override
    public void onUpdate() {
        Tile root = getTile();
        //Do not apply randomness when the game isn't real (aka running unit tests)
        boolean randomAddition = random.nextInt(100) == 0 && Gdx.app != null;
        int power = powerPerTick + (randomAddition ? 1: 0);

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
