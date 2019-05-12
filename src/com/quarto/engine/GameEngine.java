package com.quarto.engine;

import java.util.ArrayList;
import java.util.HashMap;

import com.quarto.engine.core.Engine;
import com.quarto.engine.core.Scene;
import com.quarto.engine.interfaces.Initializable;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Scenes;

public class GameEngine implements Initializable{

	private ArrayList<Engine> engines = new ArrayList<Engine>();
	private HashMap<Scenes, Scene> scenes = new HashMap<Scenes, Scene>();
	private Scene currentScene;
	private long window;
	private float pixelsPerMeter;
	private Vector2D windowSize;

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameEngine gameEngine = new GameEngine();
		gameEngine.onInit();
	}*/
	
	public static void Log(Object object) {
		System.out.println(object);
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.getEngines().size(); i++)
			this.getEngines().get(i).destroy();
		getCurrentScene().onDestroy();
	}

	public ArrayList<Engine> getEngines() {
		return engines;
	}

	public void addEngine(Engine engine) {
		engine.setGameEngine(this);
		engine.init();
		this.engines.add(engine);
	}

	public HashMap<Scenes, Scene> getScenes() {
		return scenes;
	}

	public void addScene(Scenes name, Scene scene) {
		scene.setGameEngine(this);
		scene.onInit();
		this.scenes.put(name, scene);
	}

	public void removeScene(Scenes name) {
		if(this.scenes.containsKey(name)) {
			this.scenes.get(name).onDestroy();
			this.scenes.remove(name);
		}
	}

	public Scene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(Scenes name) {
		this.currentScene = this.getScenes().get(name);
	}

	public long getWindow() {
		return window;
	}

	public void setWindow(long window) {
		this.window = window;
	}

	public float getPixelsPerMeter() {
		return pixelsPerMeter;
	}

	public void setPixelsPerMeter(float pixelsPerMeter) {
		this.pixelsPerMeter = pixelsPerMeter;
	}

	public Vector2D getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(Vector2D windowSize) {
		this.windowSize = windowSize;
	}

}
