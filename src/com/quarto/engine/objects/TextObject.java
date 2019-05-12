package com.quarto.engine.objects;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.stb.STBEasyFont.stb_easy_font_print;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.quarto.engine.core.Object;

public class TextObject extends Object {

	private String text;
	
	private ByteBuffer textBuffer;
	private int textQuads;

	public TextObject() {
		// TODO Auto-generated constructor stub
	}
	
	public TextObject(String Text) {
		this.setText(Text);
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(double delta, float pixelsPerMeter) {
		// TODO Auto-generated method stub
		this.onTick(delta);
		
		textBuffer = BufferUtils.createByteBuffer(text.length() * 1000);

		textQuads = stb_easy_font_print(0, 0, getText(), null, textBuffer);

        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(2, GL_FLOAT, 16, textBuffer);

        glColor4f(getColor().getR(), getColor().getG(), getColor().getB(), this.getColor().getA());

        glPushMatrix();
        
        glTranslatef(getPosition().getX() * pixelsPerMeter, getPosition().getY() * pixelsPerMeter, 0.0f);
        glScalef(getScale().getX(), getScale().getY(), 1f);
        
        glDrawArrays(GL_QUADS, 0, textQuads * 4);
        glPopMatrix();

        glDisableClientState(GL_VERTEX_ARRAY);
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
