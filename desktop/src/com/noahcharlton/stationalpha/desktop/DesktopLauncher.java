package com.noahcharlton.stationalpha.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.noahcharlton.stationalpha.StationAlpha;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Station Alpha";
		config.width = 854;
		config.height = 480;

		new LwjglApplication(new StationAlpha(), config);
	}
}
