package com.noahcharlton.stationalpha.block.dust;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.power.PoweredBlockContainer;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class DustCollectorContainer extends PoweredBlockContainer {

    private static final int BASE_TIME = 7200;

    private int startTick;
    private int tick;

    public DustCollectorContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);

        generateTick();
    }

    private void generateTick() {
        tick = (int) (BASE_TIME * (Math.random() + 1));
        startTick = tick;
    }

    @Override
    public int getPowerPerTick() {
        return 2;
    }

    @Override
    public void onUpdate() {
        if(!usePower())
            return;

        tick--;

        if(tick < 0) {
            generateTick();

            getTile().getWorld().getInventory().changeAmountForItem(Item.SPACE_DUST, 1);
        }
    }

    @Override
    public String[] getDebugInfo() {
        int dustCollected  = startTick == 0 ? 0 : 100 * (startTick - tick) / startTick;
        double percent = dustCollected / 100.0;
        return combineDebugInfo("Dust Collected: " + percent);
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        writer.element("Tick", tick);
        writer.element("StartTick", startTick);
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        tick = element.getInt("Tick");
        startTick = element.getInt("StartTick");
    }

    public int getTick() {
        return tick;
    }

    public int getStartTick() {
        return startTick;
    }
}
