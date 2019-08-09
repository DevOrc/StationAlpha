package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.ConduitRenderer;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

public class PlaceConduit implements BuildAction{

    @Override
    public void onClick(Tile tile, int button) {
        if(button == Input.Buttons.LEFT){
            handleLeftClick(tile);
        }else if(button == Input.Buttons.RIGHT){
            handleRightClick(tile);
        }
    }

    private void handleLeftClick(Tile tile) {
        World world = tile.getWorld();
        Inventory inventory = world.getInventory();

        if(inventory.getAmountForItem(Item.STEEL) > 0){
            inventory.changeAmountForItem(Item.STEEL, -1);
            tile.setConduit(true);
        }
    }

    private void handleRightClick(Tile tile) {
        if(tile.hasConduit()) {
            tile.setConduit(false);
        }else{
            InputHandler.getInstance().setBuildAction(null);
        }
    }

    @Override
    public void render(SpriteBatch b) {
        BuildManager buildManager = InputHandler.getInstance().getBuildManager();
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        Vector3 worldPos = InputHandler.getInstance().getBuildManager().toWorldPos(mouseX, mouseY);

        buildManager.getTileFromPixel((int) worldPos.x, (int) worldPos.y).ifPresent(tile -> renderAt(b, tile));
    }

    private void renderAt(SpriteBatch b, Tile tile) {
        ConduitRenderer.renderTile(tile, b);
    }

    @Override
    public String getName() {
        return "Place Conduit";
    }

    @Override
    public String toString() {
        return getName();
    }
}
