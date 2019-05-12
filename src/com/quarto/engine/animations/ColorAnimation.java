package com.quarto.engine.animations;

import com.quarto.engine.core.Animation;
import com.quarto.engine.core.Object;
import com.quarto.engine.utilities.Color;

public class ColorAnimation extends Animation {
	
	private Color startingColor;
	private Color diffColor;

	public ColorAnimation(float duration, Object sceneObject, Color finalColor, boolean autoPlay) {
		super(duration, sceneObject, autoPlay);
		this.setStartingColor(sceneObject.getColor().copy());
		this.setDiffColor(finalColor.subR(this.getStartingColor()));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onProgress() {
		// TODO Auto-generated method stub
		this.getSceneObject().getColor().set(this.getStartingColor().addR(this.getDiffColor().multR(this.getProgress())));
	}

	@Override
	public void onFinished() {
		// TODO Auto-generated method stub

	}

	public Color getStartingColor() {
		return startingColor;
	}

	public void setStartingColor(Color startingColor) {
		this.startingColor = startingColor;
	}

	public Color getDiffColor() {
		return diffColor;
	}

	public void setDiffColor(Color diffColor) {
		this.diffColor = diffColor;
	}

}
