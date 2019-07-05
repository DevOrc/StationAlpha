package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface JobRenderer {

    void render(SpriteBatch batch, Job job);

}
