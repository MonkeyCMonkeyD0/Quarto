package com.quarto.engine.objects;

public abstract class MultiImageObject extends ImageObject {
	
	private String texturePathBase;
	private int textureCount;
	private int currentTexture;
	private float timer;
	private float step;
	
	public MultiImageObject(String texturePathBase, int textureCount, float duration) {
		this.texturePathBase = texturePathBase;
		this.textureCount = textureCount;
		currentTexture = 0;
		step = duration / textureCount;
		timer = 0;
	}

	public void onTickImage(double delta) {
		timer += delta;
		if(timer >= step) {
			timer = 0;
			currentTexture++;
			if(currentTexture >= textureCount)
				currentTexture = 0;
			setCurrentTexturePath();
		}
	}
	
	protected void setCurrentTexturePath() {
		setTexturePath(texturePathBase + "_" + currentTexture + ".png");
	}

}
