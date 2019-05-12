package com.quarto.engine.core;

import com.quarto.engine.GameEngine;
import com.quarto.engine.interfaces.Initializable;
import com.quarto.engine.interfaces.Tickable;

public abstract class Engine extends Thread implements Initializable, Tickable{
	
	private GameEngine gameEngine;
	private int tickRate = 60;
	private boolean isTicking = false;
	
	public void init() {
		this.start();
	}

	public abstract void onInit();
	public abstract void onTick(double delta);
	public abstract void onDestroy();
	
	public void destroy() {
		this.setTicking(false);
		this.onDestroy();
	}
	
	public void run() {
		this.onInit();
		this.setTicking(true);
		long lastTime = System.nanoTime();
		long nowTime = System.nanoTime();
		long optimalTime = 1000000000 / this.getTickRate();
		long updateLength = 0;
		double delta;
		while (this.isTicking()){
			nowTime = System.nanoTime();
			updateLength = nowTime - lastTime;
			lastTime = nowTime;
			delta = (double)updateLength / 1000000000;
			this.onTick(delta);
			try{Thread.sleep((lastTime - System.nanoTime() + optimalTime)/1000000);}catch(Exception e) {};
		}
	}

	public int getTickRate() {
		return tickRate;
	}

	public void setTickRate(int tickRate) {
		this.tickRate = tickRate;
	}

	public boolean isTicking() {
		return isTicking;
	}

	public void setTicking(boolean isTicking) {
		this.isTicking = isTicking;
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

}
