package de.fhkiel.aem.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.fhkiel.aem.Configuration;
import de.fhkiel.aem.FlappyBird;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new FlappyBird(), config);

        config.title = "Flappy Bird";
        config.width = Configuration.ScreenWidth;
        config.height = Configuration.ScreenHeight;
    }
}
