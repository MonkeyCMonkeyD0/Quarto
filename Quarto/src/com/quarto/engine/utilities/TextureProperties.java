package com.quarto.engine.utilities;

import java.nio.ByteBuffer;

public class TextureProperties {
	
	private final int mipmapLevel;
	private final int outputWidth;
	private final int outputHeight;
	private final ByteBuffer outputPixels;
	
	public TextureProperties(int m, int w, int h, ByteBuffer p) {
		mipmapLevel = m;
		outputWidth = w;
		outputHeight = h;
		outputPixels = p;
	}
	
	public int getMipmapLevel() {
		return mipmapLevel;
	}
	
	public int getOutputWidth() {
		return outputWidth;
	}

	public int getOutputHeight() {
		return outputHeight;
	}

	public ByteBuffer getOutputPixels() {
		return outputPixels;
	}

}
