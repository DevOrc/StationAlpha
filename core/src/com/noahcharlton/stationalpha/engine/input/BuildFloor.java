package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Objects;

public class BuildFloor implements BuildAction{

    private final Floor floor;

    public BuildFloor(Floor floor) {
        Objects.requireNonNull(floor, "floor cannot be null!");

        this.floor = floor;
    }

    @Override
    public void onClick(Tile tile, int button) {
        if(button == Input.Buttons.LEFT){
            tile.setFloor(floor);
        }

        if(button == Input.Buttons.RIGHT){
            if(tile.getFloor().isPresent())
                tile.setFloor(null);
            else
                InputHandler.getInstance().setBuildAction(null);
        }
    }

    @Override
    public String getName() {
        return "Building Floors!";
    }

    @Override
    public void render(SpriteBatch b) {
        BuildManager buildManager = InputHandler.getInstance().getBuildManager();
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        Vector3 worldPos = InputHandler.getInstance().getBuildManager().toWorldPos(mouseX, mouseY);

        b.setColor(new Color(1f, 1f, 1f, .75f));
        buildManager.getTileFromPixel((int) worldPos.x, (int) worldPos.y).ifPresent(tile -> floor.render(b, tile));
        b.setColor(new Color(1f, 1f, 1f, 1f));
    }
}
