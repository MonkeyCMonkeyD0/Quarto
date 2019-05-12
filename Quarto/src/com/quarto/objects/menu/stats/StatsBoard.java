package com.quarto.objects.menu.stats;

import com.quarto.engine.managers.DataManager;
import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.objects.TextObject;
import com.quarto.engine.utilities.DataFile;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.scenes.MenuScene;

public class StatsBoard extends ImageObject {

	private TextObject difficultyText, playedText, wonText;
	private TextObject easyText, normalText, hardText;
	private TextObject easyPlayedText, easyWonText;
	private TextObject normalPlayedText, normalWonText;
	private TextObject hardPlayedText, hardWonText;
	private float rowSize = .55f;
	private float colSize = .25f;
	
	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setVisible(false);
		this.setTexturePath("menu/stats_board.png");
		this.setMaintainRatio(false);
		this.setSize(new Vector2D(4f, 3f));
		getPosition().setX((getScene().getGameEngine().getWindowSize().getX() - getSize().getX()) / 2);
		getPosition().setY(2.3f);
		
		difficultyText = generateText("Difficulte", 1, 1, 2.7f);
		difficultyText.getColor().set(0, 0, 0);
		playedText = generateText("Joue", 1, 8, 2.7f);
		playedText.getColor().set(0, 0, 0);
		wonText = generateText("Gagne", 1, 12, 2.7f);
		wonText.getColor().set(0, 0, 0);
		easyText = generateText("Facile", 2, 1, 2.7f);
		easyText.getColor().set(0, 1, .2f);
		normalText = generateText("Normale", 3, 1, 2.7f);
		normalText.getColor().set(1, 1, .1f);
		hardText = generateText("Difficile", 4, 1, 2.7f);
		hardText.getColor().set(1, .1f, .1f);

		easyPlayedText = generateText("", 2, 8, 2.5f);
		normalPlayedText = generateText("", 3, 8, 2.5f);
		hardPlayedText = generateText("", 4, 8, 2.5f);

		easyWonText = generateText("", 2, 12, 2.5f);
		normalWonText = generateText("", 3, 12, 2.5f);
		hardWonText = generateText("", 4, 12, 2.5f);
		
		updateTexts();
	}
	
	public void updateTexts() {
		DataFile dataFile = DataManager.getDataFile("statistics");
		
		easyPlayedText.setText(dataFile.get("EASY_PLAYED"));
		normalPlayedText.setText(dataFile.get("NORMAL_PLAYED"));
		hardPlayedText.setText(dataFile.get("HARD_PLAYED"));

		easyWonText.setText(dataFile.get("EASY_WON"));
		normalWonText.setText(dataFile.get("NORMAL_WON"));
		hardWonText.setText(dataFile.get("HARD_WON"));
	}
	
	private TextObject generateText(String text, int row, int col, float scale) {
		TextObject textObject = new TextObject(text);
		
		this.getScene().addSceneObject(textObject);
		((MenuScene) this.getScene()).getStatsMenu().addObject(textObject);
		textObject.getScale().set(scale);
		textObject.getPosition().set(getPosition().getX() + (col * colSize), getPosition().getY() + (row * rowSize));
		textObject.setVisible(false);
		textObject.setzIndex(2);
		
		return textObject;
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		getPosition().setX((getScene().getGameEngine().getWindowSize().getX() - getSize().getX()) / 2);
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

}
