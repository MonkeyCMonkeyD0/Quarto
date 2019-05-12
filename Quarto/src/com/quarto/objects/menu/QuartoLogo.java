package com.quarto.objects.menu;

import com.quarto.engine.animations.SizeAnimation;
import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.utilities.Vector2D;

public class QuartoLogo extends ImageObject {
	
	private SizeAnimation sizeAnimation;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setTexturePath("menu/logo.png");
		this.setSize(new Vector2D(3.5f, 0));
		
		sizeAnimation = new SizeAnimation(3f, this, new Vector2D(3.3f, 0), true);
		sizeAnimation.setInfinite(true);
		this.addAnimation("sizeIdle", sizeAnimation);
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		getPosition().setX((getScene().getGameEngine().getWindowSize().getX() - getSize().getX()) / 2);
		//getPosition().setY(2 - getSize().getY() / 2);
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

}
