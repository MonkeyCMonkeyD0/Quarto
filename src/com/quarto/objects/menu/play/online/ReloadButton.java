package com.quarto.objects.menu.play.online;

import com.quarto.engine.managers.DataManager;
import com.quarto.engine.managers.SoundManager;
import com.quarto.engine.objects.ButtonObject;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.scenes.MenuScene;

public class ReloadButton extends ButtonObject {

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setTexturePath("menu/buttons/reload.png");
		this.setSize(new Vector2D(.65f, 0));
		this.onSetupButton();
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		getPosition().setX((getScene().getGameEngine().getWindowSize().getX() - getSize().getX()) / 2 + 1.8f);
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
		SoundManager.onPlay("effects/woosh.wav");
		((MenuScene) this.getScene()).getHostNameText().setText(DataManager.reloadDataFile("online").get("HOST"));
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

}
