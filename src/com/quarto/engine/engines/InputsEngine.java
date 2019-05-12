package com.quarto.engine.engines;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import com.quarto.engine.core.Engine;
import com.quarto.engine.core.Object;
import com.quarto.engine.utilities.Vector2D;

public class InputsEngine extends Engine {
	
	private DoubleBuffer posXBuffer, posYBuffer;
	private Vector2D mousePos, pointI, pointJ;
	private boolean result, leftClick, oldLeftClick, rightClick, oldRightClick;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		mousePos = new Vector2D(0, 0);
		posXBuffer = BufferUtils.createDoubleBuffer(1);
		posYBuffer = BufferUtils.createDoubleBuffer(1);
		this.setTickRate(30);
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		if(getGameEngine().getWindow() != NULL) {
			leftClick = glfwGetMouseButton(getGameEngine().getWindow(), GLFW_MOUSE_BUTTON_1) == 1;
			if(oldLeftClick != leftClick) {
				onLeftClickChanged();
				oldLeftClick = leftClick;
			}
			if(leftClick)
				onLeftClick();

			rightClick = glfwGetMouseButton(getGameEngine().getWindow(), GLFW_MOUSE_BUTTON_2) == 1;
			if(oldRightClick != rightClick) {
				onRightClickChanged();
				oldRightClick = rightClick;
			}
			if(rightClick)
				onRightClick();
			
			onHover();
		}
	}
	
	private boolean checkInside(Object sceneObject) {
		result = false;		
		for (int i = 0, j = sceneObject.getPoints().size() - 1; i < sceneObject.getPoints().size(); j = i++) {
			if(sceneObject.isConsiderCenter()) {
				pointI = sceneObject.getPoints().get(i).addR(sceneObject.getPosition()).subR(sceneObject.getCenter());
				pointJ = sceneObject.getPoints().get(j).addR(sceneObject.getPosition()).subR(sceneObject.getCenter());
			}else {
				pointI = sceneObject.getPoints().get(i).addR(sceneObject.getPosition());
				pointJ = sceneObject.getPoints().get(j).addR(sceneObject.getPosition());
			}
			if (
				(pointI.getY() > mousePos.getY()) != (pointJ.getY() > mousePos.getY()) &&
				(mousePos.getX() < (pointJ.getX() - pointI.getX()) * (mousePos.getY() - pointI.getY()) / (pointJ.getY() - pointI.getY()) + pointI.getX())) {
		      	result = !result;
		    }
		}
		return result;
	}
	
	private void onHover() {
		setMousePos();
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
			if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i)
				if(this.getGameEngine().getCurrentScene().getSceneObjects().get(i).isVisible())
					if(this.checkInside(this.getGameEngine().getCurrentScene().getSceneObjects().get(i)))
						this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onHover();
					else
						this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onNotHover();
	}
	
	private void onLeftClickChanged() {
		setMousePos();
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
			if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i)
				if(this.getGameEngine().getCurrentScene().getSceneObjects().get(i).isVisible())
					if(this.checkInside(this.getGameEngine().getCurrentScene().getSceneObjects().get(i)))
						if(leftClick)
							this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onLeftClickPressed();
						else
							this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onLeftClickReleased();
	}
	
	private void onRightClickChanged() {
		setMousePos();
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
			if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i)
				if(this.getGameEngine().getCurrentScene().getSceneObjects().get(i).isVisible())
					if(this.checkInside(this.getGameEngine().getCurrentScene().getSceneObjects().get(i)))
						if(rightClick)
							this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onRightClickPressed();
						else
							this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onRightClickReleased();
	}
	
	private void onLeftClick() {
		setMousePos();
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
			if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i)
				if(this.getGameEngine().getCurrentScene().getSceneObjects().get(i).isVisible())
					if(this.checkInside(this.getGameEngine().getCurrentScene().getSceneObjects().get(i)))
						this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onLeftClickHeld();
	}
	
	private void onRightClick() {
		setMousePos();
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
			if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i)
				if(this.getGameEngine().getCurrentScene().getSceneObjects().get(i).isVisible())
					if(this.checkInside(this.getGameEngine().getCurrentScene().getSceneObjects().get(i)))
						this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onRightClickHeld();
	}
	
	private void setMousePos() {
		glfwGetCursorPos(getGameEngine().getWindow(), posXBuffer, posYBuffer);
		mousePos.set((float)posXBuffer.get(0) / getGameEngine().getPixelsPerMeter(), (float)posYBuffer.get(0) / getGameEngine().getPixelsPerMeter());
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

}
