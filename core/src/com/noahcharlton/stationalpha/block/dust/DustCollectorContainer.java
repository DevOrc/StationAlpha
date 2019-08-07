package com.noahcharlton.stationalpha.block.dust;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class DustCollectorContainer extends BlockContainer {

    private static final int BASE_TIME = 7200;

    private int tick;

    public DustCollectorContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);

        generateTick();
    }

    private void generateTick(){
        tick = (int) (BASE_TIME * (Math.random() + 1));
    }

    @Override
    public void onUpdate() {
        tick--;

        if(tick < 0){
            generateTick();

            getTile().getWorld().getInventory().changeAmountForItem(Item.SPACE_DUST, 1);
        }
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        writer.element("Tick", tick);
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        tick = element.getInt("Tick");
    }

    public int getTick() {
        return tick;
    }
}
