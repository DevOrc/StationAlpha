package com.noahcharlton.stationalpha.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.noahcharlton.stationalpha.StationAlpha;
import org.apache.logging.log4j.LogManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LogManager.getLogger(DesktopLauncher.class).info("Starting StationAlpha!");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Station Alpha";
		config.width = 854;
		config.height = 480;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;

		new LwjglApplication(new StationAlpha(), config);
	}
}
