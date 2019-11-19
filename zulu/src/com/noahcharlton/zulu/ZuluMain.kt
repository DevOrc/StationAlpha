package com.noahcharlton.zulu

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.noahcharlton.zulu.widget.FlexPane
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
        val pane = FlexPane(spacing = 5)
        pane.pos.set(200, 200)
        pane.size.set(350, 100)

        for(i in 0..5){
            val rect = PaintedWidget(
                    border = LineBorder(1, Color.FOREST),
                    backgroundColor = Color.RED);

            pane.addChild(rect, 0)
        }

        pane.layout()

        guiContainer?.currentScene = pane
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