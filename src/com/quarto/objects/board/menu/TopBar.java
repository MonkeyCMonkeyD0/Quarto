package com.quarto.objects.board.menu;

import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.objects.menu.MusicButton;
import com.quarto.objects.menu.SoundButton;
import com.quarto.objects.menu.ThemesButton;

public class TopBar extends ImageObject {

	private HomeButton homeButton;
	private ThemesButton themesButton;
	private SoundButton soundButton;
	private MusicButton musicButton;
	private TurnImage turnImage;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setTexturePath("board/topbar.png");
		this.setSize(new Vector2D(getScene().getGameEngine().getWindowSize().getX(), 0f));

		homeButton = new HomeButton();
		getScene().addSceneObject(homeButton);
		homeButton.getPosition().set(.05f, .05f);
		homeButton.setzIndex(1);

		themesButton = new ThemesButton();
		getScene().addSceneObject(themesButton);
		themesButton.getPosition().set(homeButton.getSize().getX() + .1f, .05f);
		themesButton.setzIndex(1);
		
		soundButton = new SoundButton();
		getScene().addSceneObject(soundButton);
		soundButton.setInMenu(false);
		soundButton.getPosition().set(getScene().getGameEngine().getWindowSize().getX() - soundButton.getSize().getX() - .05f, .05f);
		soundButton.setzIndex(1);
		
		musicButton = new MusicButton();
		getScene().addSceneObject(musicButton);
		musicButton.setInMenu(false);
		musicButton.getPosition().set(getScene().getGameEngine().getWindowSize().getX() - musicButton.getSize().getX() - soundButton.getSize().getX() - .1f, .05f);
		musicButton.setzIndex(1);
		
		turnImage = new TurnImage();
		getScene().addSceneObject(turnImage);
		turnImage.getPosition().setY(.1f);
		turnImage.setzIndex(1);
	}
	
	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHoverEnter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHovered() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHoverLeave() {
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

	public HomeButton getHomeButton() {
		return homeButton;
	}

	public void setHomeButton(HomeButton homeButton) {
		this.homeButton = homeButton;
	}

	public SoundButton getSoundButton() {
		return soundButton;
	}

	public void setSoundButton(SoundButton soundButton) {
		this.soundButton = soundButton;
	}

	public TurnImage getTurnImage() {
		return turnImage;
	}

	public void setTurnImage(TurnImage turnImage) {
		this.turnImage = turnImage;
	}

}
