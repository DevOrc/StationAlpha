package com.noahcharlton.stationalpha.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.noahcharlton.stationalpha.StationAlpha;
import org.apache.logging.log4j.LogManager;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LogManager.getLogger(DesktopLauncher.class).info("Starting StationAlpha!");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Station Alpha " + StationAlpha.VERSION;
		config.width = StationAlpha.DEFAULT_WIDTH;
		config.height = StationAlpha.DEFAULT_HEIGHT;
		config.vSyncEnabled = false;
		config.foregroundFPS = 60;
		config.backgroundFPS = 51;

		config.addIcon("icon.png", Files.FileType.Internal);

		new SafeLWJGLApplication(new StationAlpha(), config);
	}
}
class SafeLWJGLApplication extends LwjglApplication {

	public SafeLWJGLApplication(ApplicationListener listener, LwjglApplicationConfiguration config) {
		super(listener, config);

		Watchdog.watch(this.mainLoopThread);
	}

	@Override
	public void exit() {
		super.exit();
	}
}
