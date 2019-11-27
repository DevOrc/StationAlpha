package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class BatteryContainer extends PoweredBlockContainer {

    private int capacity;

    public BatteryContainer(Tile tile, Block block, BlockRotation rotation, int capacity) {
        super(tile, block, rotation);

        this.capacity = capacity;
    }

    @Override
    public void onBuilt() {
        getTile().getWorld().getPowerNetwork().changeCapacity(capacity);
    }

    @Override
    public int getPowerPerTick() {
        return 0;
    }

    @Override
    public void onDestroy() {
        getTile().getWorld().getPowerNetwork().changeCapacity(-capacity);
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        writer.element("Capacity", capacity);
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        capacity = element.getInt("Capacity");
    }

    public int getCapacity() {
        return capacity;
    }
}
