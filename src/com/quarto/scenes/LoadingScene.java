package com.quarto.scenes;

import com.quarto.engine.core.Scene;
import com.quarto.engine.interfaces.Callback;
import com.quarto.engine.managers.TextureManager;
import com.quarto.engine.utilities.Color;
import com.quarto.enums.Scenes;
import com.quarto.objects.loading.LoadingDetails;
import com.quarto.objects.loading.LoadingText;

public class LoadingScene extends Scene {
	
	private Scene loadingScene;
	private Scenes loadingSceneEnum;
	private boolean loaded;
	private boolean transitioned;
	private int ticks = 0;
	
	private LoadingText loadingText;
	private LoadingDetails loadingDetails;

	@Override
	public void onFinishedLoading(LoadingScene loadingScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setBackgroundColor(new Color(0, 0, 0, 1));
		this.setLoaded(false);
		this.setTransitioned(false);
		
		loadingText = new LoadingText("loading/chargement", 4, 1);
		this.addSceneObject(loadingText);

		loadingDetails = new LoadingDetails();
		this.addSceneObject(loadingDetails);
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		if(!isLoaded()) {
			
			if(ticks < loadingScene.getPreloadedTexturePaths().size()) {
				TextureManager.onTexture(loadingScene.getPreloadedTexturePaths().get(ticks));
				ticks++;
				loadingDetails.setText("Images (" + ticks + "/" + loadingScene.getPreloadedTexturePaths().size() + ")");
			}else {
				setLoaded(true);
				loadingScene.onFinishedLoading(this);
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.getSceneObjects().size(); i++)
			this.getSceneObjects().get(i).destroy();
		
		if(!isTransitioned())
			loadingScene.onDestroy();
	}
	
	public void makeTransition() {
		this.setTransitioned(true);
		this.fadeOut(new Callback() {
			@Override
			public void onCallback() {
				getGameEngine().setCurrentScene(loadingSceneEnum);
				getGameEngine().removeScene(Scenes.LOADING);
			}
		});
	}
	
	public void backToMenu() {
		this.fadeOut(new Callback() {
			@Override
			public void onCallback() {
				getGameEngine().setCurrentScene(Scenes.MENU);
				getGameEngine().removeScene(loadingSceneEnum);
			}
		});
	}
	
	public Scene getLoadingScene() {
		return loadingScene;
	}
	
	public void setLoadingScene(Scenes loadingSceneEnum, Scene loadingScene) {
		this.loadingSceneEnum = loadingSceneEnum;
		this.loadingScene = loadingScene;
		getGameEngine().addScene(loadingSceneEnum, loadingScene);
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public LoadingDetails getLoadingDetails() {
		return loadingDetails;
	}

	public void setLoadingDetails(LoadingDetails loadingDetails) {
		this.loadingDetails = loadingDetails;
	}

	public boolean isTransitioned() {
		return transitioned;
	}

	public void setTransitioned(boolean transitioned) {
		this.transitioned = transitioned;
	}

}
