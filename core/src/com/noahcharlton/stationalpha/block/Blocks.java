package com.noahcharlton.stationalpha.block;

public class Blocks {

    private static Block wall;

    public static void init(){
        wall = createSimple("wall.png");
    }

    private static Block createSimple(String textureName) {
        return new Block() {
            @Override
            protected String getTextureName() {
                return textureName;
            }
        };
    }

    public static Block getWall() {
        return wall;
    }
}
