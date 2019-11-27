package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class BatteryContainer extends BlockContainer implements PoweredContainer {

    private final int capacity;
    private int amount;

    public BatteryContainer(Tile tile, Block block, BlockRotation rotation, int capacity) {
        super(tile, block, rotation);

        this.capacity = capacity;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public String[] getDebugInfo() {
        return super.combineDebugInfo(
                "Stored Power: " + amount + " / " + capacity
        );
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        writer.element("PowerStored", amount);
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        amount = element.getInt("PowerStored");
    }

    public int getAmount() {
        return amount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
