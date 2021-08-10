package de.fhkiel.aem.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Factory to create ImageButtons
 */
public class ButtonFactory {

	/**
	 * Lambda Function that is run as the button click function.
	 */
	@FunctionalInterface
	public interface ActionFunction {
		void run();
	}

	/**
	 * Creates a new Image Button.
	 * @param imagePath The imagePath to the Image the button should have
	 * @param function The function that is called on Click
	 * @return The newly created Button
	 */
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

	/**
	 * Creates an new ImageButton with an different Image as the Checked Image.
	 * @param imagePath The imagePath to the Image the button should have
	 * @param checkedImagePath The imagePath to the Image the button should have if he is checked
	 * @param function The function that is called on Click
	 * @return The newly created Button
	 */
	public static ImageButton CreateImageButton(String imagePath, String checkedImagePath, ActionFunction function) {
		ImageButton button = new ImageButton(
				new TextureRegionDrawable(new TextureRegion(new Texture(imagePath))),
				new TextureRegionDrawable(new TextureRegion(new Texture(imagePath))),
				new TextureRegionDrawable(new TextureRegion(new Texture(checkedImagePath))));
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				function.run();
			}
		});
		return button;
	}
	public static ImageButton CreateImageButton(String upImagePath, String downImagePath, String checkedImagePath, ActionFunction function) {
		ImageButton button = new ImageButton(
				new TextureRegionDrawable(new TextureRegion(new Texture(upImagePath))),
				new TextureRegionDrawable(new TextureRegion(new Texture(downImagePath))),
				new TextureRegionDrawable(new TextureRegion(new Texture(checkedImagePath))));
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				function.run();
			}
		});
		return button;
	}

}
