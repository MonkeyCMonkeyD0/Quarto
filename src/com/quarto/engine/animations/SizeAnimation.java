package com.quarto.engine.animations;

import com.quarto.engine.core.Animation;
import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.utilities.Vector2D;

public class SizeAnimation extends Animation {

	private Vector2D startingSize;
	private Vector2D diffSize;

	public SizeAnimation(float duration, ImageObject sceneObject, Vector2D endingSize, boolean autoPlay) {
		super(duration, sceneObject, autoPlay);
		this.setStartingSize(sceneObject.getSize().copy());
		this.setDiffSize(endingSize.subR(this.getStartingSize()).copy());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onProgress() {
		// TODO Auto-generated method stub
		((ImageObject)this.getSceneObject()).getSize().set(this.getStartingSize().addR(this.getDiffSize().multR(this.getProgress())));
	}

	@Override
	public void onFinished() {
		// TODO Auto-generated method stub

	}

	public Vector2D getStartingSize() {
		return startingSize;
	}

	public void setStartingSize(Vector2D startingSize) {
		this.startingSize = startingSize;
	}

	public Vector2D getDiffSize() {
		return diffSize;
	}

	public void setDiffSize(Vector2D diffSize) {
		this.diffSize = diffSize;
	}

}
