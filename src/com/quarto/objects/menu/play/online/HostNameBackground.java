package com.quarto.objects.menu.play.online;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.utilities.Vector2D;

public class HostNameBackground extends ImageObject {

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setTexturePath("menu/button_bg.png");
		this.setSize(new Vector2D(2.5f, 0));
	}
	
	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		getPosition().setX((getScene().getGameEngine().getWindowSize().getX() - getSize().getX()) / 2);
	}

	@Override
	public void onHovered() {
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
		try {
			Desktop.getDesktop().edit(new File("data/online.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void onHoverEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHoverLeave() {
		// TODO Auto-generated method stub
		
	}

}
