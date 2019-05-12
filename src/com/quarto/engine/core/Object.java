package com.quarto.engine.core;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.quarto.engine.interfaces.Clickable;
import com.quarto.engine.interfaces.Drawable;
import com.quarto.engine.interfaces.Initializable;
import com.quarto.engine.interfaces.Tickable;
import com.quarto.engine.utilities.Color;
import com.quarto.engine.utilities.Vector2D;

public abstract class Object implements Initializable, Drawable, Clickable, Tickable{

	private Scene scene;
	
	private Vector2D position;
	private Vector2D speed;
	private Vector2D acceleration;
	private Vector2D scale;
	private float rotation;
	private float rotationSpeed;
	private Color color;
	private boolean hasGravity;
	private ArrayList<Vector2D> points = new ArrayList<Vector2D>();
	private Vector2D center;
	private boolean considerCenter;
	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private boolean hovered;
	private boolean visible;
	private int zIndex;
	
	protected Vector2D tmpDraw;

	public void init() {
		this.setPosition(new Vector2D());
		this.setSpeed(new Vector2D());
		this.setAcceleration(new Vector2D());
		this.setRotation(0);
		this.setScale(new Vector2D(1, 1));
		this.setColor(new Color(1, 1, 1));
		this.setCenter(new Vector2D());
		this.setHasGravity(false);
		this.setConsiderCenter(false);
		this.setVisible(true);
		this.setzIndex(0);
		this.onInit();
	}
	
	public abstract void onInit();
	
	@Override
	public void onDraw(double delta, float pixelsPerMeter) {
		// TODO Auto-generated method stub
		this.onTick(delta);
		
		glColor4f(this.getColor().getR(), this.getColor().getG(), this.getColor().getB(), this.getColor().getA());
		
		glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		
		glBegin(GL_POLYGON);
		for (int i = 0; i < this.getPoints().size(); i++) {
			if(this.getPoints().size() > i) {
				if(this.isConsiderCenter())
					tmpDraw = this.getPoints().get(i).subR(this.getCenter()).multR(pixelsPerMeter).addR(this.getPosition().multR(pixelsPerMeter));
				else
					tmpDraw = this.getPoints().get(i).multR(pixelsPerMeter).addR(this.getPosition().multR(pixelsPerMeter));
				glVertex2f(tmpDraw.getX(), tmpDraw.getY());
			}
		}
        glEnd();
        
        glDisable(GL_BLEND);
	}
	
	public void makePolygon(int numPoints, float size) {
		for (int i = 0; i < numPoints; i++)
			this.addPoint(new Vector2D((float) Math.cos((((Math.PI * 2) / numPoints) * i) - (Math.PI / 2)) * size, (float) Math.sin((((Math.PI * 2) / numPoints) * i) - (Math.PI / 2)) * size));
	}
	
	public abstract void onDestroy();

	public void destroy() {
		this.onDestroy();
	}
	
	public void onHover() {
		if(!hovered) {
			hovered = true;
			this.onHoverEnter();
		}
		this.onHovered();
	}
	
	public void onNotHover() {
		if(hovered) {
			hovered = false;
			this.onHoverLeave();
		}
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public Vector2D getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2D speed) {
		this.speed = speed;
	}

	public Vector2D getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	public boolean isHasGravity() {
		return hasGravity;
	}

	public void setHasGravity(boolean hasGravity) {
		this.hasGravity = hasGravity;
	}

	public Vector2D getScale() {
		return scale;
	}

	public void setScale(Vector2D scale) {
		this.scale = scale;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Vector2D> getPoints() {
		return points;
	}

	public void addPoint(Vector2D point) {
		this.points.add(point);
		this.getCenter().set(0, 0);
		for(int i = 0; i < this.getPoints().size(); i++)
			this.getCenter().add(this.getPoints().get(i));
		this.getCenter().divide(this.getPoints().size());
	}

	public Vector2D getCenter() {
		return center;
	}

	public void setCenter(Vector2D center) {
		this.center = center;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public boolean isConsiderCenter() {
		return considerCenter;
	}

	public void setConsiderCenter(boolean considerCenter) {
		this.considerCenter = considerCenter;
	}

	public HashMap<String, Animation> getAnimations() {
		return animations;
	}

	public Animation getAnimation(String name) {
		if(this.animations.containsKey(name))
			return this.animations.get(name);
		return null;
	}

	public void addAnimation(String name, Animation animation) {
		if(!this.animations.containsKey(name))
			this.animations.put(name, animation);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}
	
	public static Comparator<Object> zIndexCmp = new Comparator<Object>() {
		public int compare(Object s1, Object s2) {
		   return s1.getzIndex() - s2.getzIndex();
		}
	};
	
	
}
