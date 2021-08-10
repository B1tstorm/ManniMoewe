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

	/**
	 * Creates an ImageButton.
	 * @param upImagePath Up Image
	 * @param downImagePath Down Image
	 * @param checkedImagePath Checked Image
	 * @param function The click Function
	 * @return The newly created Button
	 */
	public static ImageButton CreateImageButton(String upImagePath, String downImagePath, String checkedImagePath, ActionFunction function) {
		ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
		style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(upImagePath)));
		style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(downImagePath)));
		style.imageChecked = new TextureRegionDrawable(new TextureRegion(new Texture(checkedImagePath)));
		ImageButton button = new ImageButton(style);
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				function.run();
			}
		});
		return button;
	}

	/**
	 * Creates a new Image Button.
	 * @param imageName The imagePath to the Image the button should have. NEED to have a ".png" , "-pressed.png" and "-hover.png".
	 * @param hasCheckedState If the button has a check state. NEEDs a "-active.png"
	 * @param function The function that is called on Click
	 * @return The newly created Button
	 */
	public static ImageButton CreateImageButton(String imageName, boolean hasCheckedState, ActionFunction function) {
		ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
		style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(imageName + ".png")));
		style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(imageName + "-pressed.png")));
		style.imageOver = new TextureRegionDrawable(new TextureRegion(new Texture(imageName + "-hover.png")));
		if(hasCheckedState) {
			style.imageChecked = new TextureRegionDrawable(new TextureRegion(new Texture(imageName + "-active.png")));
		}
		ImageButton button = new ImageButton(style);
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				function.run();
			}
		});
		return button;
	}
}
