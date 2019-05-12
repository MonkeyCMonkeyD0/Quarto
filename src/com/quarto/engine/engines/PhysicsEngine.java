package com.quarto.engine.engines;

import com.quarto.engine.core.Engine;
import com.quarto.engine.core.Object;
import com.quarto.engine.utilities.Vector2D;

public class PhysicsEngine extends Engine {
	
	private Vector2D gravity = new Vector2D(0, 9.81f);

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
			if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i)
				this.updatePhysics(this.getGameEngine().getCurrentScene().getSceneObjects().get(i), delta);
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getParticles().size(); i++)
			if(this.getGameEngine().getCurrentScene().getParticles().size() > i)
				this.updatePhysics(this.getGameEngine().getCurrentScene().getParticles().get(i), delta);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}
	
	private void updatePhysics(Object sceneObject, double delta) {
		sceneObject.getAcceleration().mult(.9f);
		//sceneObject.setRotationSpeed(sceneObject.getRotationSpeed() * .9f);
		sceneObject.getSpeed().add(sceneObject.getAcceleration().multR((float) delta));
		if(sceneObject.isHasGravity())
			sceneObject.getSpeed().add(this.getGravity().multR((float) delta));
		sceneObject.getPosition().add(sceneObject.getSpeed().multR((float) delta));
		sceneObject.setRotation((float) (sceneObject.getRotation() + (sceneObject.getRotationSpeed() * delta)));
	}

	public Vector2D getGravity() {
		return gravity;
	}

	public void setGravity(Vector2D gravity) {
		this.gravity = gravity;
	}

}
