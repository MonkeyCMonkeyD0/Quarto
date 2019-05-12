package com.quarto.engine.utilities;

import java.io.Serializable;

import com.quarto.engine.GameEngine;

public class Vector2D implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2377293786338433010L;
	private float x, y;
	
	public Vector2D() {
		this.setX(0);
		this.setY(0);
	}
	
	public Vector2D(float x, float y){
		this.setX(x);
		this.setY(y);
	}
	
	public void set(float value){
		this.setX(value);
		this.setY(value);
	}
	
	public void set(float x, float y){
		this.setX(x);
		this.setY(y);
	}
	
	public void set(Vector2D vector2D){
		this.setX(vector2D.getX());
		this.setY(vector2D.getY());
	}
	
	public void add(Vector2D vector) {
		this.setX(this.getX() + vector.getX());
		this.setY(this.getY() + vector.getY());
	}
	
	public void add(float value) {
		this.setX(this.getX() + value);
		this.setY(this.getY() + value);
	}
	
	public Vector2D addR(Vector2D vector) {
		Vector2D newVector = new Vector2D();
		newVector.setX(this.getX() + vector.getX());
		newVector.setY(this.getY() + vector.getY());
		return newVector;
	}
	
	public Vector2D addR(float value) {
		Vector2D newVector = new Vector2D();
		newVector.setX(this.getX() + value);
		newVector.setY(this.getY() + value);
		return newVector;
	}
	
	public void sub(Vector2D vector) {
		this.setX(this.getX() - vector.getX());
		this.setY(this.getY() - vector.getY());
	}
	
	public void sub(float value) {
		this.setX(this.getX() - value);
		this.setY(this.getY() - value);
	}
	
	public Vector2D subR(Vector2D vector) {
		Vector2D newVector = new Vector2D();
		newVector.setX(this.getX() - vector.getX());
		newVector.setY(this.getY() - vector.getY());
		return newVector;
	}
	
	public Vector2D subR(float value) {
		Vector2D newVector = new Vector2D();
		newVector.setX(this.getX() - value);
		newVector.setY(this.getY() - value);
		return newVector;
	}
	
	public void mult(float value) {
		this.setX(this.getX() * value);
		this.setY(this.getY() * value);
	}
	
	public Vector2D multR(float value) {
		Vector2D newVector = new Vector2D();
		newVector.setX(this.getX() * value);
		newVector.setY(this.getY() * value);
		return newVector;
	}
	
	public void divide(float value) {
		this.setX(this.getX() / value);
		this.setY(this.getY() / value);
	}
	
	public void log() {
		GameEngine.Log("(" + this.getX() + ", " + this.getY() + ")");
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public Vector2D copy() {
		return new Vector2D(this.getX(), this.getY());
	}
	
}
