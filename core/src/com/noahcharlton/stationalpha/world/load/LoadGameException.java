package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.GdxRuntimeException;

public class LoadGameException extends GdxRuntimeException {

    private final Throwable cause;

    public LoadGameException(Throwable t) {
        super(t);

        this.cause = t;
    }

    @Override
    public String getMessage() {
        return cause.getMessage();
    }
}
