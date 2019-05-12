package com.quarto.engine.core;

import com.quarto.engine.interfaces.Initializable;
import com.quarto.engine.interfaces.Turnable;
import com.quarto.enums.Players;

public abstract class Player implements Initializable, Turnable{

	private Scene scene;
	private Players playerEnum;
	
	public Player(Players playerEnum) {
		this.setEnum(playerEnum);
	}

	public abstract void onTurn();
	public abstract void onFinished(Player winningPlayer);

	public Players getEnum() {
		return playerEnum;
	}

	public void setEnum(Players playerEnum) {
		this.playerEnum = playerEnum;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
}
