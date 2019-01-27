package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class BuildManager {

    private static final Logger logger = LogManager.getLogger(BuildManager.class);
    private final StationAlpha gameInstance = StationAlpha.getInstance();

    public void handleGameClick(int x, int y, int button) {
        Vector3 worldPos = toWorldPos(x, y);

        x = (int) worldPos.x;
        y = (int) worldPos.y;

        getTileFromPixel(x, y).ifPresent(tile -> build(tile, button));
    }

    private Vector3 toWorldPos(int x, int y) {
        Vector3 v = new Vector3(x, y, 0);
        OrthographicCamera cam = gameInstance.getGameRenderer().getCamera();
        Viewport viewport = gameInstance.getGameRenderer().getViewport();

        return (cam.unproject(v, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
                viewport.getScreenHeight()));
    }

    Optional<Tile> getTileFromPixel(int x, int y){
        int tileX = x / Tile.TILE_SIZE;
        int tileY = y / Tile.TILE_SIZE;

        if(tileX < 0 || tileY < 0 || tileX >= World.WORLD_TILE_SIZE || tileY >= World.WORLD_TILE_SIZE) {
            return Optional.empty();
        }

        return World.getInstance().get().getTileAt(tileX, tileY);
    }

    void build(Tile tile, int button) {
        logger.info("Clicked on {} with button {}", tile, button);

        if(button == Input.Buttons.LEFT)
            tile.setBlock(Blocks.getWall());
        else if(button == Input.Buttons.RIGHT)
            tile.setBlock(null);
    }
}
