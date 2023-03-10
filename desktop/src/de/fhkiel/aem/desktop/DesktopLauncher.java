package de.fhkiel.aem.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.fhkiel.aem.Configuration;
import de.fhkiel.aem.FlappyBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new FlappyBird(), config);

		config.title = "Manni die M" + '\u00F6' + "we";
		config.width = Configuration.ScreenWidth;
		config.height = Configuration.ScreenHeight;
		config.fullscreen = Configuration.fullscreen;
	}
}
