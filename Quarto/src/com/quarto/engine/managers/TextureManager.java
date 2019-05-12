package com.quarto.engine.managers;

import java.util.HashMap;

import com.quarto.engine.utilities.Texture;
import com.quarto.engine.utilities.Vector2D;

public class TextureManager {

	private static String[] themes = {"bois", "plage"};
	private static int currentTheme = 0;
	private static String theme;
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public static void onLoadTexture(String path) {
		if(!textures.containsKey(path)) {
			Texture texture = new Texture(path);
			texture.onLoad();
			textures.put(path, texture);
		}
	}

	public static int onTexture(String path) {
		path = "res/img/themes/" + theme + "/" + path;
		if(!textures.containsKey(path))
			onLoadTexture(path);
		return textures.get(path).getTexture();
	}

	public static Vector2D getTextureSize(String path) {
		path = "res/img/themes/" + theme + "/" + path;
		if(!textures.containsKey(path))
			onLoadTexture(path);
		return textures.get(path).getSize();
	}

	public static String getTheme() {
		return theme;
	}

	public static void setTheme(String theme) {
		TextureManager.theme = theme;
	}
	
	public static void nextTheme() {
		currentTheme++;
		if(currentTheme >= themes.length)
			currentTheme = 0;
		setTheme(themes[currentTheme]);
	}

}
