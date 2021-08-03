package de.fhkiel.aem.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.fhkiel.aem.FlappyBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "FlappyBird";
		config.width = 800;
		config.height = 800;
		new LwjglApplication(new FlappyBird(), config);
	}
}
