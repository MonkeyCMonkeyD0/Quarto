package com.quarto.engine.core;

import java.util.ArrayList;
import java.util.Random;

import com.quarto.engine.interfaces.Callback;
import com.quarto.engine.utilities.Vector2D;

public class Generator extends Object{
	
	private ArrayList<String> particleImages = new ArrayList<String>();
	private float rate;
	private Vector2D size;
	private Vector2D force;
	private Vector2D rot;
	private Vector2D lifespan;
	private Vector2D angle;
	private Particle particle;
	private float particleAngle;
	private float time;
	
	private boolean generating;
	
	private float generationTimer;
	private float timer;
	
	private Callback callback;
	
	public Generator(String particleImage) {
		// TODO Auto-generated constructor stub
		setParticleImage(particleImage);
		setSize(new Vector2D());
		setForce(new Vector2D());
		setLifespan(new Vector2D());
		setAngle(new Vector2D());
		setGenerating(false);
		setTime(1);
		timer = 0;
		generationTimer = 0;
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		if(isGenerating()) {
			timer += delta;
			if(timer >= rate) {
				for(int i = 0; i < Math.floor(timer / rate); i++) {
					particle = new Particle();
					particle.setLifespan(randomBetween(lifespan.getX(), lifespan.getY()));
					getScene().addParticle(particle);
					particle.setTexturePath(getParticleImage());
					particle.getSize().set(randomBetween(size.getX(), size.getY()));
					particle.getPosition().setX(getPosition().getX() - particle.getSize().getX() / 2);
					particle.getPosition().setY(getPosition().getY() - particle.getSize().getY() / 2);
					particle.setHasGravity(true);
					particle.getColor().set(randomBetween(.5f, 1), randomBetween(.5f, 1), randomBetween(.5f, 1));
					particleAngle = randomBetween(angle.getX(), angle.getY());
					particle.getAcceleration().set(new Vector2D((float) Math.cos(particleAngle), (float) Math.sin(particleAngle)).multR(randomBetween(force.getX(), force.getY()))); 
					particle.setRotationSpeed(10f);
				}
				timer = 0;
			}
			generationTimer += delta;
			if(generationTimer > time) {
				setGenerating(false);
				generationTimer = 0;
				if(callback != null)
					callback.onCallback();
			}
		}
	}
	
	private float randomBetween(float min, float max) {
		return (float) (min + Math.random() * (max - min));
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

	public String getParticleImage() {
		Random rand = new Random();
		return particleImages.get(rand.nextInt(particleImages.size()));
	}

	public void setParticleImage(String particleImage) {
		this.particleImages.add("particles/" + particleImage);
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Vector2D getSize() {
		return size;
	}

	public void setSize(Vector2D size) {
		this.size = size;
	}

	public Vector2D getForce() {
		return force;
	}

	public void setForce(Vector2D force) {
		this.force = force;
	}

	public Vector2D getLifespan() {
		return lifespan;
	}

	public void setLifespan(Vector2D lifespan) {
		this.lifespan = lifespan;
	}

	public Vector2D getAngle() {
		return angle;
	}

	public void setAngle(Vector2D angle) {
		this.angle = angle;
	}

	public Vector2D getRot() {
		return rot;
	}

	public void setRot(Vector2D rot) {
		this.rot = rot;
	}

	public boolean isGenerating() {
		return generating;
	}

	public void setGenerating(boolean generating) {
		this.generating = generating;
	}

	public void setGenerating(boolean generating, Callback callback) {
		this.generating = generating;
		this.callback = callback;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

}
