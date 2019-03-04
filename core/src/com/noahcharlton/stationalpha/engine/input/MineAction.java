package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;

public class MineAction implements BuildAction{

    private final Inventory inventory;

    public MineAction(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void onClick(Tile tile, int button) {
        if(button == Input.Buttons.LEFT){
            onLeftClick(tile);
        }
    }

    private void onLeftClick(Tile tile) {
        if(tile.getBlock().filter(block -> block.equals(Blocks.getIce())).isPresent()){
            inventory.changeAmountForItem(Item.SPACE_ROCK, 1);
            tile.setBlock(null);
        }
    }

    @Override
    public String getName() {
        return "Mine Action";
    }
}
