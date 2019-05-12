package com.quarto.enums;

public enum Difficulties {

	EASY(45), NORMAL(60), HARD((int) Integer.MAX_VALUE), PVP(0), ONLINE(1);
	
	private int value;

	Difficulties(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
