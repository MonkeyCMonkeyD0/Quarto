package com.quarto.engine.core;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.quarto.engine.GameEngine;
import com.quarto.engine.interfaces.Callback;
import com.quarto.engine.interfaces.Drawable;
import com.quarto.engine.interfaces.Initializable;
import com.quarto.engine.interfaces.Tickable;
import com.quarto.engine.managers.TextureManager;
import com.quarto.engine.utilities.Color;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Players;
import com.quarto.objects.FadeOverlay;
import com.quarto.scenes.LoadingScene;

public abstract class Scene implements Initializable, Drawable, Tickable{

	private GameEngine gameEngine;
	private ArrayList<Object> sceneObjects = new ArrayList<Object>();
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private HashMap<Players, Player> players = new HashMap<Players, Player>();
	private Color backgroundColor = new Color(0, 0, 0);
	private String backgroundTexturePath;
	private Vector2D backgroundTextureSize;
	private int backgroundTextureID;
	private boolean hasBackgroundTexture = false;
	private ArrayList<String> preloadedTexturePaths = new ArrayList<String>();

	@Override
	public void onDraw(double delta, float pixelsPerMeter) {
		// TODO Auto-generated method stub
	    glClearColor(getBackgroundColor().getR(), getBackgroundColor().getG(), getBackgroundColor().getB(), 1f);
	    
	    if(hasBackgroundTexture) {
	    	backgroundTextureID = TextureManager.onTexture(getBackgroundTexturePath());
	    	
			glColor3f(this.getBackgroundColor().getR(), this.getBackgroundColor().getG(), this.getBackgroundColor().getB());

	        glEnable(GL_BLEND);
	        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

	        glBindTexture(GL_TEXTURE_2D, backgroundTextureID);
	        glEnable(GL_TEXTURE_2D);
	        glBegin(GL_QUADS);
	        
	        glTexCoord2f(0.0f, 0.0f);
	        glVertex2f(0, 0);

	        glTexCoord2f(1.0f, 0.0f);
	        glVertex2f(getGameEngine().getWindowSize().getX() * pixelsPerMeter, 0);

	        glTexCoord2f(1.0f, 1.0f);
	        glVertex2f(getGameEngine().getWindowSize().getX() * pixelsPerMeter, getGameEngine().getWindowSize().getY() * pixelsPerMeter);

	        glTexCoord2f(0.0f, 1.0f);
	        glVertex2f(0, getGameEngine().getWindowSize().getY() * pixelsPerMeter);
	        
	        glEnd();
	        glDisable(GL_TEXTURE_2D);
	        glBindTexture(GL_TEXTURE_2D, 0);
	        
	        glDisable(GL_BLEND);
	    }
	    
	    this.onTick(delta);
	}
	
	public abstract void onFinishedLoading(LoadingScene loadingScene);
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.getSceneObjects().size(); i++)
			this.getSceneObjects().get(i).destroy();
	}
	
	public void fadeOut(Callback callback) {
		this.addSceneObject(new FadeOverlay(callback));
	}

	public ArrayList<Object> getSceneObjects() {
		return sceneObjects;
	}

	public void addSceneObject(Object sceneObject) {
		sceneObject.setScene(this);
		sceneObject.init();
		this.sceneObjects.add(sceneObject);
		
		Collections.sort(this.sceneObjects, Object.zIndexCmp);
	}

	public void removeSceneObject(Object sceneObject) {
		sceneObject.destroy();
		this.sceneObjects.remove(sceneObject);
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void addParticle(Particle particle) {
		particle.setScene(this);
		particle.init();
		this.particles.add(particle);
	}

	public void removeParticle(Particle particle) {
		particle.destroy();
		this.particles.remove(particle);
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBackgroundTexturePath() {
		return backgroundTexturePath;
	}

	public void setBackgroundTexturePath(String backgroundTexturePath) {
		this.backgroundTexturePath = backgroundTexturePath;
		this.hasBackgroundTexture = true;
	}

	public Vector2D getBackgroundTextureSize() {
		return backgroundTextureSize;
	}

	public void setBackgroundTextureSize(Vector2D backgroundTextureSize) {
		this.backgroundTextureSize = backgroundTextureSize;
	}

	public HashMap<Players, Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		player.setScene(this);
		player.onInit();
		this.players.put(player.getEnum(), player);
	}

	public ArrayList<String> getPreloadedTexturePaths() {
		return preloadedTexturePaths;
	}

	public void addPreloadedTexturePaths(String preloadedTexturePath) {
		this.preloadedTexturePaths.add(preloadedTexturePath);
	}

}
