package com.noahcharlton.zulu.widget

import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class Pane : PaintedWidget(){

    abstract fun getChildren() : List<Widget>;

    override fun render(batch: SpriteBatch){
        super.render(batch);

        getChildren().forEach { widget -> widget.render(batch) }
    }

    override fun layout() {
        getChildren().forEach { widget -> widget.layout() }
    }

}