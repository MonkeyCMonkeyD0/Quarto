package com.quarto.engine.core;

public abstract class Animation {

	private float duration;
	private float timer;
	private float progress;
	private boolean finished;
	private boolean playing;
	private boolean reversed;
	private boolean infinite;
	
	private Object sceneObject;
	
	public Animation(float duration, Object sceneObject, boolean autoPlay) {
		this.setDuration(duration);
		this.setSceneObject(sceneObject);
		this.setTimer(0);
		this.setProgress(0);
		this.setPlaying(autoPlay);
		this.setReversed(false);
		this.setInfinite(false);
	}
	
	public abstract void onProgress();
	public abstract void onFinished();
	
	public void onTick(double delta) {
		if(this.isPlaying()) {
			if(reversed)
				timer -= delta;
			else
				timer += delta;
			progress = Math.min(timer / duration, 1f);
			progress = Math.max(progress, 0f);
			this.onProgress();
			if((progress == 1f && !reversed) || (progress == 0f && reversed)) {
				if(this.isInfinite())
					this.setReversed(!this.isReversed());
				else
					this.setPlaying(false);
				this.onFinished();
			}
		}
	}
	
	public void reset() {
		setPlaying(false);
		progress = 0;
		this.onProgress();
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Object getSceneObject() {
		return sceneObject;
	}

	public void setSceneObject(Object sceneObject) {
		this.sceneObject = sceneObject;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}
	
}
