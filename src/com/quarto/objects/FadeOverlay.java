package com.quarto.objects;
import com.quarto.engine.animations.ColorAnimation;
import com.quarto.engine.core.Object;
import com.quarto.engine.interfaces.Callback;
import com.quarto.engine.utilities.Color;
import com.quarto.engine.utilities.Vector2D;

public class FadeOverlay extends Object{
	
	private Callback callback;
	
	public FadeOverlay(Callback callback) {
		// TODO Auto-generated constructor stub
		this.callback = callback;
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.getColor().set(0, 0, 0, 0);
		this.addPoint(new Vector2D(0, 0));
		this.addPoint(new Vector2D(0, getScene().getGameEngine().getWindowSize().getY()));
		this.addPoint(new Vector2D(getScene().getGameEngine().getWindowSize().getX(), getScene().getGameEngine().getWindowSize().getY()));
		this.addPoint(new Vector2D(getScene().getGameEngine().getWindowSize().getX(), 0));
		this.setzIndex(10);
		
		FadeOverlay self = this;
		
		this.addAnimation("fadeoutAnimation", new ColorAnimation(1f, this, new Color(0f, 0f, 0f, 1f), true) {
			
			@Override
			public void onFinished() {
				callback.onCallback();
				getScene().removeSceneObject(self);
			}
			
		});
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHoverEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHovered() {
		// TODO Auto-generated method stub
		
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
