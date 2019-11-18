package com.noahcharlton.zulu

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.noahcharlton.zulu.widget.LineBorder
import com.noahcharlton.zulu.widget.PaintedWidget

fun main(){
    var config = LwjglApplicationConfiguration();
    config.title = "Zulu Test"
    config.vSyncEnabled = false
    config.foregroundFPS = -1
    config.backgroundFPS = 60
    config.forceExit = false

    LwjglApplication(TestZuluGui(), config)
}

class TestZuluGui: ApplicationAdapter(){

    private var guiContainer: GuiContainer? = null

    override fun create() {
        guiContainer = GuiContainer()
        val rect = PaintedWidget()
        rect.size.set(250, 250)
        rect.pos.set(100, 300)
        rect.border = LineBorder(8, Color.FOREST, renderEast = false)

        guiContainer?.currentScene = rect
    }

    override fun resize(width: Int, height: Int) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun render() {
        guiContainer?.render()
    }

    override fun dispose() {
        guiContainer?.dispose()
    }

}