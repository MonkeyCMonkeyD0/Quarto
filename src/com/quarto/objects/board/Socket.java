package com.quarto.objects.board;

import com.quarto.engine.core.Object;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.scenes.BoardScene;

public class Socket extends Object {
	
	private float size;
	private boolean selected;
	private Piece piece;
	
	private Vector2D gridPosition;
	
	public Socket(float socketSize) {
		// TODO Auto-generated constructor stub
		setSize(socketSize);
	}
	
	public void onSelect(boolean userClicked) {
		((BoardScene) this.getScene()).onSocketSelected(this, userClicked);
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.addPoint(new Vector2D(0, 0));
		this.addPoint(new Vector2D(0, size));
		this.addPoint(new Vector2D(size, size));
		this.addPoint(new Vector2D(size, 0));
		this.getColor().set(0, 0, 0, 0);
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
		if(!this.isSelected())
			this.onSelect(true);
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.setSelected(true);
		this.piece = piece;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public Vector2D getGridPosition() {
		return gridPosition;
	}

	public void setGridPosition(Vector2D gridPosition) {
		this.gridPosition = gridPosition;
	}

}
