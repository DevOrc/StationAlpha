package com.noahcharlton.zulu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.noahcharlton.zulu.widget.EmptyWidget
import com.noahcharlton.zulu.widget.Widget

class GuiContainer{

    private val camera = OrthographicCamera()
    private val viewport = ScreenViewport(camera)
    private val spriteBatch = SpriteBatch()

    var currentScene: Widget = EmptyWidget();

    fun render(){
        viewport.update(Gdx.graphics.width, Gdx.graphics.height)
        camera.projection.setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat());
        spriteBatch.projectionMatrix = camera.projection

        spriteBatch.begin()
        if(currentScene.visible)
            currentScene.render(spriteBatch)
        spriteBatch.end()
    }

    fun dispose(){
        spriteBatch.dispose()
    }

}