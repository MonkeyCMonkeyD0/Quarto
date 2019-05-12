package com.quarto.objects.menu;

import com.quarto.engine.managers.SoundManager;
import com.quarto.engine.managers.TextureManager;
import com.quarto.engine.objects.ButtonObject;
import com.quarto.engine.utilities.Vector2D;

public class ThemesButton extends ButtonObject{

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setTexturePath("menu/buttons/theme.png");
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
		TextureManager.nextTheme();
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
