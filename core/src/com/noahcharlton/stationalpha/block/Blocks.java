package com.noahcharlton.stationalpha.block;

public class Blocks {

    private static Block wall = new WallBlock();
    private static Block ice;

    public static void init(){
        ice = createSimple("ice.png");
    }

    private static Block createSimple(String textureName) {
        return new Block() {
            @Override
            protected String getTextureFileName() {
                return textureName;
            }
        };
    }

    public static Block getWall() {
        return wall;
    }

    public static Block getIce() {
        return ice;
    }
}
