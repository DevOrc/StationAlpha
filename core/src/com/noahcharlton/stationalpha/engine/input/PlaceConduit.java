package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.ConduitRenderer;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

public class PlaceConduit implements BuildAction, Selectable{

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

        if(tile.hasConduit())
            return;

        if(inventory.getAmountForItem(Item.STEEL) > 0){
            inventory.changeAmountForItem(Item.STEEL, -1);
            tile.setConduit(true);
        }
    }

    @Override
    public void onSelected() {
        InputHandler.getInstance().setCurrentlySelected(this);
    }

    @Override
    public void onDeselected() {
        InputHandler.getInstance().setCurrentlySelected(null);
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
    public InGameIcon getIcon() {
        return InGameIcon.NO_POWER;
    }

    @Override
    public String getName() {
        return "Place Conduit";
    }

    @Override
    public String getTitle() {
        return getName();
    }

    @Override
    public String getDesc() {
        return "Place down conduits to transfer power.";
    }

    @Override
    public String[] getDebugInfo() {
        return new String[]{"Requires: 1 Steel"};
    }

    @Override
    public String toString() {
        return getName();
    }
}
