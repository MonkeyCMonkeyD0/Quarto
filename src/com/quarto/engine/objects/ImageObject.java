package com.quarto.engine.objects;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.quarto.engine.core.Object;
import com.quarto.engine.managers.TextureManager;
import com.quarto.engine.utilities.Vector2D;

public abstract class ImageObject extends Object{

	private String texturePath;
	private Vector2D textureSize;
	private Vector2D size;
	private boolean maintainRatio = true;
	private int textureID;
	
	@Override
	public void onDraw(double delta, float pixelsPerMeter) {
		this.onTick(delta);
		
		textureID = TextureManager.onTexture(getTexturePath());
		if(maintainRatio) {
			size.setY(size.getX() * (TextureManager.getTextureSize(this.getTexturePath()).getY() / TextureManager.getTextureSize(this.getTexturePath()).getX()));
			this.updatePoints();
		}

		glColor4f(this.getColor().getR(), this.getColor().getG(), this.getColor().getB(), this.getColor().getA());
		
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glEnable(GL_TEXTURE_2D);
        
        glBegin(GL_QUADS);
        
        glTexCoord2f(0.0f, 0.0f);
        glVertex2f(getPosition().getX() * pixelsPerMeter, getPosition().getY() * pixelsPerMeter);

        glTexCoord2f(1.0f, 0.0f);
        glVertex2f((getPosition().getX() + getSize().getX()) * pixelsPerMeter, getPosition().getY() * pixelsPerMeter);

        glTexCoord2f(1.0f, 1.0f);
        glVertex2f((getPosition().getX() + getSize().getX()) * pixelsPerMeter, (getPosition().getY() + getSize().getY()) * pixelsPerMeter);

        glTexCoord2f(0.0f, 1.0f);
        glVertex2f(getPosition().getX() * pixelsPerMeter, (getPosition().getY() + getSize().getY()) * pixelsPerMeter);
        
        glEnd();
        
        glDisable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
        
        glDisable(GL_BLEND);
	}
	
	public String getTexturePath() {
		return texturePath;
	}
	public void setTexturePath(String texturePath) {
		this.getScene().addPreloadedTexturePaths(texturePath);
		this.texturePath = texturePath;
	}

	public Vector2D getSize() {
		return size;
	}

	public void setSize(Vector2D size) {
		this.size = size;
		this.addPoint(new Vector2D(0, 0));
		this.addPoint(new Vector2D(0, size.getY()));
		this.addPoint(new Vector2D(size.getX(), size.getY()));
		this.addPoint(new Vector2D(size.getX(), 0));
	}
	
	private void updatePoints() {
		this.getPoints().get(1).setY(size.getY());
		this.getPoints().get(2).setX(size.getX());
		this.getPoints().get(2).setY(size.getY());
		this.getPoints().get(3).setX(size.getX());
	}

	public Vector2D getTextureSize() {
		return textureSize;
	}

	public void setTextureSize(Vector2D textureSize) {
		this.textureSize = textureSize;
	}

	public boolean isMaintainRatio() {
		return maintainRatio;
	}

	public void setMaintainRatio(boolean maintainRatio) {
		this.maintainRatio = maintainRatio;
	}
	
}
