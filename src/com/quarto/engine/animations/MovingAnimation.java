package com.quarto.engine.animations;

import com.quarto.engine.core.Animation;
import com.quarto.engine.core.Object;
import com.quarto.engine.utilities.Vector2D;

public abstract class MovingAnimation extends Animation{

	private Vector2D startingPosition;
	private Vector2D diffPosition;

	public MovingAnimation(float duration, Object sceneObject, Vector2D endingPosition, boolean autoPlay) {
		super(duration, sceneObject, autoPlay);
		this.setStartingPosition(sceneObject.getPosition().copy());
		this.setDiffPosition(endingPosition.subR(this.getStartingPosition()));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onProgress() {
		// TODO Auto-generated method stub
		this.getSceneObject().getPosition().set(this.getStartingPosition().addR(this.getDiffPosition().multR(this.getProgress())));
	}

	public Vector2D getStartingPosition() {
		return startingPosition;
	}

	public void setStartingPosition(Vector2D startingPosition) {
		this.startingPosition = startingPosition;
	}

	public Vector2D getDiffPosition() {
		return diffPosition;
	}

	public void setDiffPosition(Vector2D diffPosition) {
		this.diffPosition = diffPosition;
	}

}
