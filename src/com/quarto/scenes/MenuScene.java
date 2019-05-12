package com.quarto.scenes;

import com.quarto.engine.core.Scene;
import com.quarto.engine.managers.DataManager;
import com.quarto.engine.managers.SoundManager;
import com.quarto.engine.objects.TextObject;
import com.quarto.engine.utilities.Color;
import com.quarto.objects.menu.HelpButton;
import com.quarto.objects.menu.Menu;
import com.quarto.objects.menu.MusicButton;
import com.quarto.objects.menu.PlayButton;
import com.quarto.objects.menu.QuartoLogo;
import com.quarto.objects.menu.QuitButton;
import com.quarto.objects.menu.SoundButton;
import com.quarto.objects.menu.StatsButton;
import com.quarto.objects.menu.ThemesButton;
import com.quarto.objects.menu.play.PlayAIButton;
import com.quarto.objects.menu.play.PlayOnlineButton;
import com.quarto.objects.menu.play.PlayPlayerButton;
import com.quarto.objects.menu.play.ai.BackButton;
import com.quarto.objects.menu.play.ai.EasyButton;
import com.quarto.objects.menu.play.ai.HardButton;
import com.quarto.objects.menu.play.ai.NormalButton;
import com.quarto.objects.menu.play.online.ConnectButton;
import com.quarto.objects.menu.play.online.HostButton;
import com.quarto.objects.menu.play.online.HostNameBackground;
import com.quarto.objects.menu.play.online.ReloadButton;
import com.quarto.objects.menu.stats.StatsBoard;
import com.quarto.objects.menu.tutorial.Tutorial;

public class MenuScene extends Scene {

	private Menu mainMenu;
	private Menu playMenu;
	private Menu aiMenu;
	private Menu onlineMenu;
	private Menu statsMenu;
	private Menu tutorialMenu;
	
	private StatsBoard statsBoard;
	private Tutorial tutorial;

	private PlayButton playButton;
	private HelpButton helpButton;
	private StatsButton statsButton;
	private MusicButton musicButton;
	private SoundButton soundButton;
	private ThemesButton themesButton;
	private QuitButton quitButton;
	
	private PlayAIButton playAIButton;
	private PlayPlayerButton playPlayerButton;
	private PlayOnlineButton playOnlineButton;
	
	private EasyButton easyButton;
	private NormalButton normalButton;
	private HardButton hardButton;

	private HostButton hostButton;
	private HostNameBackground hostNameBackground;
	private TextObject hostNameText;
	private ReloadButton reloadButton;
	private ConnectButton connectButton;
	
	
	private BackButton backButton;
	
	private boolean menuSwitched = false;

	@Override
	public void onFinishedLoading(LoadingScene loadingScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setBackgroundColor(new Color(1f, 1f, 1f));
		this.setBackgroundTexturePath("menu/background.png");

		mainMenu = new Menu();
		playMenu = new Menu();
		aiMenu = new Menu();
		onlineMenu = new Menu();
		statsMenu = new Menu();
		tutorialMenu = new Menu();
		
		QuartoLogo quartoLogo = new QuartoLogo();
		this.addSceneObject(quartoLogo);
		mainMenu.addObject(quartoLogo);
		playMenu.addObject(quartoLogo);
		aiMenu.addObject(quartoLogo);
		onlineMenu.addObject(quartoLogo);
		statsMenu.addObject(quartoLogo);
		quartoLogo.getPosition().setY(.5f);
		
		playButton = new PlayButton();
		this.addSceneObject(playButton);
		mainMenu.addObject(playButton);
		playButton.getPosition().setY(2.5f);
		
		helpButton = new HelpButton();
		this.addSceneObject(helpButton);
		mainMenu.addObject(helpButton);
		helpButton.getPosition().setY(3.5f);
		
		statsButton = new StatsButton();
		this.addSceneObject(statsButton);
		mainMenu.addObject(statsButton);
		statsButton.getPosition().setY(4.5f);
		
		soundButton = new SoundButton();
		this.addSceneObject(soundButton);
		mainMenu.addObject(soundButton);
		soundButton.getPosition().setY(4.5f);
		
		musicButton = new MusicButton();
		this.addSceneObject(musicButton);
		mainMenu.addObject(musicButton);
		musicButton.getPosition().setY(4.5f);
		
		themesButton = new ThemesButton();
		this.addSceneObject(themesButton);
		themesButton.getPosition().set(getGameEngine().getWindowSize().getX() - themesButton.getSize().getX() - .1f, .1f);
		
		quitButton = new QuitButton();
		this.addSceneObject(quitButton);
		mainMenu.addObject(quitButton);
		quitButton.getPosition().setY(5.5f);
		
		
		
		playAIButton = new PlayAIButton();
		this.addSceneObject(playAIButton);
		playMenu.addObject(playAIButton);
		playAIButton.getPosition().setY(2.5f);
		
		playPlayerButton = new PlayPlayerButton();
		this.addSceneObject(playPlayerButton);
		playMenu.addObject(playPlayerButton);
		playPlayerButton.getPosition().setY(3.5f);
		
		playOnlineButton = new PlayOnlineButton();
		this.addSceneObject(playOnlineButton);
		playMenu.addObject(playOnlineButton);
		playOnlineButton.getPosition().setY(4.5f);
		

		
		easyButton = new EasyButton();
		this.addSceneObject(easyButton);
		aiMenu.addObject(easyButton);
		easyButton.getPosition().setY(2.5f);
		
		normalButton = new NormalButton();
		this.addSceneObject(normalButton);
		aiMenu.addObject(normalButton);
		normalButton.getPosition().setY(3.5f);
		
		hardButton = new HardButton();
		this.addSceneObject(hardButton);
		aiMenu.addObject(hardButton);
		hardButton.getPosition().setY(4.5f);
		
		

		statsBoard = new StatsBoard();
		this.addSceneObject(statsBoard);
		statsMenu.addObject(statsBoard);
		statsBoard.getPosition().setY(2.3f);
		
		tutorial = new Tutorial();
		this.addSceneObject(tutorial);
		tutorialMenu.addObject(tutorial);
		tutorial.getPosition().setY(.8f);
		

		
		hostButton = new HostButton();
		this.addSceneObject(hostButton);
		onlineMenu.addObject(hostButton);
		hostButton.getPosition().setY(2.5f);
		
		hostNameBackground = new HostNameBackground();
		this.addSceneObject(hostNameBackground);
		onlineMenu.addObject(hostNameBackground);
		hostNameBackground.getPosition().setY(3.5f);

		hostNameText = new TextObject() {
			
			@Override
			public void onInit() {
				getScale().set(2.5f);
				setText(DataManager.getDataFile("online").get("HOST"));
			}
			
		};
		this.addSceneObject(hostNameText);
		hostNameText.setzIndex(2);
		onlineMenu.addObject(hostNameText);
		hostNameText.getPosition().setY(3.75f);
		hostNameText.getPosition().setX((getGameEngine().getWindowSize().getX() - hostNameBackground.getSize().getX()) / 2 + .3f);
		
		reloadButton = new ReloadButton();
		this.addSceneObject(reloadButton);
		onlineMenu.addObject(reloadButton);
		reloadButton.getPosition().setY(3.5f);
		
		connectButton = new ConnectButton();
		this.addSceneObject(connectButton);
		onlineMenu.addObject(connectButton);
		connectButton.getPosition().setY(4.5f);
		
		
		
		backButton = new BackButton();
		this.addSceneObject(backButton);
		playMenu.addObject(backButton);
		aiMenu.addObject(backButton);
		onlineMenu.addObject(backButton);
		statsMenu.addObject(backButton);
		tutorialMenu.addObject(backButton);
		backButton.getPosition().setY(5.5f);
		
		
		playMenu.hide();
		aiMenu.hide();
		onlineMenu.hide();
		statsMenu.hide();
		tutorialMenu.hide();
		mainMenu.show();
		
		SoundManager.setBackgroundMusic("music/menu_atmosphere.wav");
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		setMenuSwitched(false);
	}

	public void onPlayButtonClicked() {
		mainMenu.hide();
		aiMenu.hide();
		onlineMenu.hide();
		statsMenu.hide();
		tutorialMenu.hide();
		playMenu.show();
		setMenuSwitched(true);
	}

	public void onPlayAIButtonClicked() {
		mainMenu.hide();
		playMenu.hide();
		onlineMenu.hide();
		statsMenu.hide();
		tutorialMenu.hide();
		aiMenu.show();
		setMenuSwitched(true);
	}

	public void onPlayOnlineButtonClicked() {
		mainMenu.hide();
		aiMenu.hide();
		playMenu.hide();
		statsMenu.hide();
		tutorialMenu.hide();
		onlineMenu.show();
		setMenuSwitched(true);
	}

	public void onStatsButtonClicked() {
		mainMenu.hide();
		aiMenu.hide();
		playMenu.hide();
		onlineMenu.hide();
		tutorialMenu.hide();
		statsMenu.show();
		statsBoard.updateTexts();
		setMenuSwitched(true);
	}

	public void onBackButtonClicked() {
		playMenu.hide();
		aiMenu.hide();
		onlineMenu.hide();
		statsMenu.hide();
		tutorialMenu.hide();
		mainMenu.show();
		setMenuSwitched(true);
	}

	public void onHelpButtonClicked() {
		playMenu.hide();
		aiMenu.hide();
		onlineMenu.hide();
		statsMenu.hide();
		mainMenu.hide();
		tutorialMenu.show();
		setMenuSwitched(true);
	}

	public boolean isMenuSwitched() {
		return menuSwitched;
	}

	public void setMenuSwitched(boolean menuSwitched) {
		this.menuSwitched = menuSwitched;
	}
	
	public Menu getStatsMenu() {
		return statsMenu;
	}
	
	public TextObject getHostNameText() {
		return hostNameText;
	}
	
}