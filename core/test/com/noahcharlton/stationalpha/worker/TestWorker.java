package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.world.World;

public class TestWorker extends Worker{

    public TestWorker(String name, World world) {
        super(name, world);
    }

    public TestWorker() {
        super("Test Worker", new World());
    }

    public void setPixelXNoCheck(int pixelX) {
        this.pixelX = pixelX;
    }

    public void setPixelYNoCheck(int pixelY) {
        this.pixelY = pixelY;
    }
}
