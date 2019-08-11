package com.noahcharlton.stationalpha.block.power;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class BatteryContainer extends BlockContainer implements PoweredContainer {

    private final int INTAKE_THRESHOLD = Tile.MAX_POWER - 5;
    private final int capacity;
    private int amount;

    public BatteryContainer(Tile tile, Block block, BlockRotation rotation, int capacity) {
        super(tile, block, rotation);

        this.capacity = capacity;
    }

    @Override
    public void onUpdate() {
        if(getTile().getPower() >= INTAKE_THRESHOLD && amount != capacity){
            amount++;
            getTile().setPower(getTile().getPower() - 1);
        }else if(getTile().getPower() < INTAKE_THRESHOLD && amount > 0){
            amount--;
            getTile().setPower(getTile().getPower() + 1);
        }
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
