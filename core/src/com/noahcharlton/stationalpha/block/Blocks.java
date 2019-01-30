package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.block.door.DoorBlock;

import java.util.Optional;

public class Blocks {

    private static Block wall = new WallBlock();
    private static Block door = new DoorBlock();
    private static Block ice;

    public static void init(){
        ice = createSimple("ice.png");
    }

    private static Block createSimple(String textureName) {
        return new Block() {
            @Override
            protected Optional<String> getTextureFileName() {
                return Optional.of(textureName);
            }
        };
    }

    public static Block getWall() {
        return wall;
    }

    public static Block getIce() {
        return ice;
    }

    public static Block getDoor() {
        return door;
    }
}
