package com.quarto.engine.utilities;

public class Color {

	private float r, g, b, a;
	
	public Color() {
		this.setR(0);
		this.setG(0);
		this.setB(0);
		this.setA(1);
	}
	
	public Color(float r, float g, float b) {
		this.setR(r);
		this.setG(g);
		this.setB(b);
		this.setA(1);
	}
	
	public Color(float r, float g, float b, float a) {
		this.setR(r);
		this.setG(g);
		this.setB(b);
		this.setA(a);
	}
	
	public void set(Color color){
		this.setR(color.getR());
		this.setG(color.getG());
		this.setB(color.getB());
		this.setA(color.getA());
	}
	
	public void set(float r, float g, float b){
		this.setR(r);
		this.setG(g);
		this.setB(b);
	}
	
	public void set(float r, float g, float b, float a){
		this.setR(r);
		this.setG(g);
		this.setB(b);
		this.setA(a);
	}
	
	public void set(float i){
		this.setR(i);
		this.setG(i);
		this.setB(i);
		this.setA(i);
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}
	
	public Color addR(Color color) {
		Color newColor = new Color();
		newColor.setR(this.getR() + color.getR());
		newColor.setG(this.getG() + color.getG());
		newColor.setB(this.getB() + color.getB());
		newColor.setA(this.getA() + color.getA());
		return newColor;
	}
	
	public Color multR(float value) {
		Color newColor = new Color();
		newColor.setR(this.getR() * value);
		newColor.setG(this.getG() * value);
		newColor.setB(this.getB() * value);
		newColor.setA(this.getA() * value);
		return newColor;
	}
	
	public Color subR(Color color) {
		Color newColor = new Color();
		newColor.setR(this.getR() - color.getR());
		newColor.setG(this.getG() - color.getG());
		newColor.setB(this.getB() - color.getB());
		newColor.setA(this.getA() - color.getA());
		return newColor;
	}
	
	public Color copy() {
		return new Color(this.getR(), this.getG(), this.getB(), this.getA());
	}

	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}
	
}
