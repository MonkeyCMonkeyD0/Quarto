package com.quarto.objects.board;

import com.quarto.engine.core.Generator;
import com.quarto.engine.interfaces.Callback;
import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Scenes;

public class GameBoard extends ImageObject {

	private Generator generator1;
	private Generator generator2;
	
	private float boardYOffset = 1f;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setTexturePath("board/board.png");
		this.setMaintainRatio(false);
		this.setSize(new Vector2D(4f, 4f));
		getPosition().setX((getScene().getGameEngine().getWindowSize().getX() - getSize().getX()) / 2);
		getPosition().setY((getScene().getGameEngine().getWindowSize().getY() + boardYOffset - getSize().getY()) / 2);
		
		generator1 = new Generator("star.png");
		generator1.setParticleImage("star2.png");
		getScene().addSceneObject(generator1);
		generator1.setTime(5);
		generator1.setRate(.05f);
		generator1.getSize().set(.3f, .6f);
		generator1.getLifespan().set(.75f, 1.5f);
		generator1.getForce().set(40, 80);
		generator1.getAngle().set((float) Math.toRadians(-45), (float) Math.toRadians(-135));
		
		generator2 = new Generator("star.png");
		generator2.setParticleImage("star2.png");
		getScene().addSceneObject(generator2);
		generator2.setTime(5);
		generator2.setRate(.05f);
		generator2.getSize().set(.3f, .6f);
		generator2.getLifespan().set(.75f, 1.5f);
		generator2.getForce().set(40, 80);
		generator2.getAngle().set((float) Math.toRadians(-45), (float) Math.toRadians(-135));
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		generator1.getPosition().setY(getPosition().getY() + .3f);
		generator1.getPosition().setX(getPosition().getX() + .3f);
		
		generator2.getPosition().setY(getPosition().getY() + .3f);
		generator2.getPosition().setX(getPosition().getX() + getSize().getX() - .3f);
	}
	
	public void showFireworks() {
		generator1.setGenerating(true);
		generator2.setGenerating(true, new Callback() {
			@Override
			public void onCallback() {
				getScene().fadeOut(new Callback() {
					@Override
					public void onCallback() {
						getScene().getGameEngine().setCurrentScene(Scenes.MENU);
						getScene().getGameEngine().removeScene(Scenes.BOARD);
					}
				});
			}
		});
	}
	
	public void hideFireworks() {
		generator1.setGenerating(false);
		generator2.setGenerating(false);
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
