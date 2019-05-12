package com.quarto.engine.core;

import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.utilities.Vector2D;

public class Particle extends ImageObject {
	
	private float lifespan;
	private float timer;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setSize(new Vector2D(3.5f, 3.5f));
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		timer += delta;
		
		getColor().set(1 - (timer / lifespan));
		
		if(timer >= lifespan)
			getScene().removeParticle(this);
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

	public float getLifespan() {
		return lifespan;
	}

	public void setLifespan(float lifespan) {
		this.lifespan = lifespan;
	}

}
