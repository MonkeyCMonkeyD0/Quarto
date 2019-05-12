package com.quarto.objects.menu;

import java.util.ArrayList;

import com.quarto.engine.core.Object;

public class Menu {

	private ArrayList<Object> objects = new ArrayList<Object>();
	
	public void hide() {
		for (int i = 0; i < objects.size(); i++)
			objects.get(i).setVisible(false);
	}
	
	public void show() {
		for (int i = 0; i < objects.size(); i++)
			objects.get(i).setVisible(true);
	}

	public ArrayList<Object> getObjects() {
		return objects;
	}

	public void addObject(Object object) {
		this.objects.add(object);
	}
	
}
