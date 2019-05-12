package com.quarto.engine.interfaces;

public interface Clickable {

	public void onHover();
	public void onNotHover();
	public void onHoverEnter();
	public void onHovered();
	public void onHoverLeave();
	
	public void onLeftClickPressed();
	public void onLeftClickHeld();
	public void onLeftClickReleased();
	public void onRightClickPressed();
	public void onRightClickHeld();
	public void onRightClickReleased();
	
}
