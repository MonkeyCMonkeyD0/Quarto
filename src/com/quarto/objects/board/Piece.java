package com.quarto.objects.board;

import com.quarto.engine.animations.ColorAnimation;
import com.quarto.engine.animations.SizeAnimation;
import com.quarto.engine.objects.ImageObject;
import com.quarto.engine.utilities.Color;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.scenes.BoardScene;
import com.quarto.utilities.PieceProperties;

public class Piece extends ImageObject {

	private float size;

	private boolean selected;
	private boolean placed;
	
	private PieceProperties properties;
	
	public Piece(float socketSize) {
		// TODO Auto-generated constructor stub
		size = socketSize;
		
		this.setProperties(new PieceProperties());
	}
	
	public void onSelect(boolean userClicked) {
		((BoardScene) this.getScene()).onPieceSelected(this, userClicked);
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		if(this.isTall())
			size = size * 1.2f;
		this.setSize(new Vector2D(size, size));
		
		String image;
		if (this.isDark())
			image = "n";
		else
			image = "b";
		
		if(this.isSquare())
			image += "_c";
		else
			image += "_r";
		
		if(this.isFull())
			image += "_p";
		else
			image += "_t";
		
		if(this.isTall())
			image += "_g";
		else
			image += "_p";
		
		getColor().set(1f, 1f, 1f);
		
		this.getScale().set(1);
		this.setTexturePath("board/pieces/" + image + ".png");

		SizeAnimation sizeAnimation = new SizeAnimation(.1f, this, new Vector2D(size * 1.05f, 0), false);
		ColorAnimation colorAnimation = new ColorAnimation(.1f, this, new Color(.8f, .8f, .8f), false);
		this.addAnimation("sizeHover", sizeAnimation);
		this.addAnimation("colorHover", colorAnimation);
		
		SizeAnimation sizeAnimationSelected = new SizeAnimation(.3f, this, new Vector2D(size * 1.05f, 0), false);
		sizeAnimationSelected.setInfinite(true);
		this.addAnimation("sizeSelected", sizeAnimationSelected);
		
		ColorAnimation colorAnimationSelected = new ColorAnimation(.3f, this, new Color(.8f, .8f, .8f), false);
		colorAnimationSelected.setInfinite(true);
		this.addAnimation("colorSelected", colorAnimationSelected);
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHoverEnter() {
		// TODO Auto-generated method stub
		if(!this.isSelected() && !this.isPlaced()){
			getAnimation("sizeHover").setReversed(false);
			getAnimation("sizeHover").setPlaying(true);
			getAnimation("colorHover").setReversed(false);
			getAnimation("colorHover").setPlaying(true);
		}
	}
	
	@Override
	public void onHovered() {

	}

	@Override
	public void onHoverLeave() {
		// TODO Auto-generated method stub
		if(!this.isSelected() && !this.isPlaced()){
			getAnimation("sizeHover").setReversed(true);
			getAnimation("sizeHover").setPlaying(true);
			getAnimation("colorHover").setReversed(true);
			getAnimation("colorHover").setPlaying(true);
		}
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
		if(!this.isSelected() && !this.isPlaced())
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
	
	public PieceProperties getProperties() {
		return this.properties;
	}
	
	public void setProperties(PieceProperties properties) {
		this.properties = properties;
	}
	
	public boolean isDark() {
		return properties.isDark();
	}

	public void setDark(boolean dark) {
		this.properties.setDark(dark);
	}

	public boolean isSquare() {
		return properties.isSquare();
	}

	public void setSquare(boolean square) {
		this.properties.setSquare(square);
	}

	public boolean isFull() {
		return properties.isFull();
	}

	public void setFull(boolean full) {
		this.properties.setFull(full);
	}

	public boolean isTall() {
		return properties.isTall();
	}

	public void setTall(boolean tall) {
		this.properties.setTall(tall);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

}
