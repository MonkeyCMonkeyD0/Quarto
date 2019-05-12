package com.quarto.engine.objects;

import com.quarto.engine.animations.ColorAnimation;
import com.quarto.engine.animations.SizeAnimation;
import com.quarto.engine.utilities.Color;
import com.quarto.engine.utilities.Vector2D;

public abstract class ButtonObject extends ImageObject {
	
	public void onSetupButton() {
		SizeAnimation sizeAnimation = new SizeAnimation(.1f, this, new Vector2D(getSize().getX() * .97f, getSize().getY() * .9f), false);
		ColorAnimation colorAnimation = new ColorAnimation(.1f, this, new Color(.9f, .9f, .9f), false);
		this.addAnimation("sizeHover", sizeAnimation);
		this.addAnimation("colorHover", colorAnimation);
	}

	@Override
	public void onHoverEnter() {
		// TODO Auto-generated method stub
		getAnimation("sizeHover").setReversed(false);
		getAnimation("sizeHover").setPlaying(true);
		getAnimation("colorHover").setReversed(false);
		getAnimation("colorHover").setPlaying(true);
	}

	@Override
	public void onHoverLeave() {
		// TODO Auto-generated method stub
		getAnimation("sizeHover").setReversed(true);
		getAnimation("sizeHover").setPlaying(true);
		getAnimation("colorHover").setReversed(true);
		getAnimation("colorHover").setPlaying(true);
	}

}
