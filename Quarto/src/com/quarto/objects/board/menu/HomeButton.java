package com.quarto.objects.board.menu;

import com.quarto.engine.interfaces.Callback;
import com.quarto.engine.managers.SoundManager;
import com.quarto.engine.objects.ButtonObject;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Scenes;

public class HomeButton extends ButtonObject {

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setTexturePath("board/home_button.png");
		this.setSize(new Vector2D(.75f, 0));
		this.onSetupButton();
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub

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
		this.getScene().fadeOut(new Callback() {
			@Override
			public void onCallback() {
				getScene().getGameEngine().setCurrentScene(Scenes.MENU);
				getScene().getGameEngine().removeScene(Scenes.BOARD);
			}
		});
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
