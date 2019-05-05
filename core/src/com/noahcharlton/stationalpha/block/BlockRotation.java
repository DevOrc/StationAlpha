package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.utils.GdxRuntimeException;

public enum BlockRotation {

    NORTH(0), SOUTH(180), EAST(270), WEST(90);

    private int degrees;

    BlockRotation(int degrees) {
        this.degrees = degrees;
    }

    public BlockRotation getNext(){
        switch(this){
            case NORTH: return EAST;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            case EAST: return SOUTH;
        }

        throw new GdxRuntimeException("Unknown block rotation: " + this);
    }

    public int getDegrees() {
        return degrees;
    }
}
