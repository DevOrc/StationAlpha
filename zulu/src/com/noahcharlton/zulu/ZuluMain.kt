package com.noahcharlton.zulu

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.GL20

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

    override fun create() {

    }

    override fun resize(width: Int, height: Int) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun render() {

    }

    override fun dispose() {

    }

}