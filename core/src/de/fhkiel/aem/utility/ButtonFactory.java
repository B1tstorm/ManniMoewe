package de.fhkiel.aem.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {

	@FunctionalInterface
	public interface ActionFunction {
		void run();
	}

	public static ImageButton CreateImageButton(String imagePath, ActionFunction function) {
		ImageButton button = new ImageButton(
				new TextureRegionDrawable(new TextureRegion(new Texture(imagePath))));
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				function.run();
			}
		});
		return button;
	}

}
