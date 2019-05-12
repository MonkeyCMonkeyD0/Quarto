package com.quarto.objects.loading;

import com.quarto.engine.objects.MultiImageObject;
import com.quarto.engine.utilities.Vector2D;

public class LoadingText extends MultiImageObject {

	public LoadingText(String texturePathBase, int textureCount, float duration) {
		super(texturePathBase, textureCount, duration);
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		setCurrentTexturePath();
		this.setSize(new Vector2D(4f, 0));
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		this.onTickImage(delta);
		getPosition().setX((getScene().getGameEngine().getWindowSize().getX() - getSize().getX()) / 2 + .2f);
		getPosition().setY((getScene().getGameEngine().getWindowSize().getY() - getSize().getY()) / 2 - 1f);
	}

	@Override
	public void onHoverEnter() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onHovered() {

	}

	@Override
	public void onHoverLeave() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeftClickPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeftClickHeld() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeftClickReleased() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRightClickPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRightClickHeld() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRightClickReleased() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

}
