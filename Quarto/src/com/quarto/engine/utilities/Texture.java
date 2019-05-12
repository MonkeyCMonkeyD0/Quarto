package com.quarto.engine.utilities;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.stb.STBImageResize.STBIR_ALPHA_CHANNEL_NONE;
import static org.lwjgl.stb.STBImageResize.STBIR_COLORSPACE_SRGB;
import static org.lwjgl.stb.STBImageResize.STBIR_EDGE_CLAMP;
import static org.lwjgl.stb.STBImageResize.STBIR_FILTER_MITCHELL;
import static org.lwjgl.stb.STBImageResize.STBIR_FLAG_ALPHA_PREMULTIPLIED;
import static org.lwjgl.stb.STBImageResize.stbir_resize_uint8_generic;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.system.MemoryStack;

import com.quarto.engine.managers.FileManager;

public class Texture {

	private int texture;
	private int comp;
	private String path;
	private ByteBuffer byteBuffer;
	private Vector2D size;
	private boolean loaded;
	private ArrayList<TextureProperties> properties = new ArrayList<TextureProperties>();
	
	public Texture(String path){
		this.setPath(path);
	}
	
	public void onCreate() {
		texture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        int format;
        if (comp == 3) {
            format = GL_RGB;
        } else {
            format = GL_RGBA;
        }

        glTexImage2D(GL_TEXTURE_2D, 0, format, getWidth(), getHeight(), 0, format, GL_UNSIGNED_BYTE, byteBuffer);
        
        TextureProperties textureProperties;
        for(int i = 0; i < getProperties().size(); i++) {
        	textureProperties = getProperties().get(i);
        	glTexImage2D(GL_TEXTURE_2D, textureProperties.getMipmapLevel(), format, textureProperties.getOutputWidth(), textureProperties.getOutputHeight(), 0, format, GL_UNSIGNED_BYTE, textureProperties.getOutputPixels());
        }
        glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void onLoad() {
		//GameEngine.Log("Loading texture " + getPath() + "!");
		
		try {
			byteBuffer = FileManager.ioResourceToByteBuffer(getPath(), 8 * 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer w    = stack.mallocInt(1);
            IntBuffer h    = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);
            
            if (!stbi_info_from_memory(byteBuffer, w, h, c)) {
                throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
            }
            byteBuffer = stbi_load_from_memory(byteBuffer, w, h, c, 0);
            if (byteBuffer == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            setSize(new Vector2D(w.get(0), h.get(0)));
            comp = c.get(0);
        }

        if(comp != 3)
            premultiplyAlpha();
        
        ByteBuffer input_pixels = byteBuffer;
        ByteBuffer output_pixels = null;
        int input_w = getWidth();
        int input_h = getHeight();
        int output_w = 0;
        int output_h = 0;
        int mipmapLevel = 0;
        while (1 < input_w || 1 < input_h) {
            output_w = Math.max(1, input_w >> 1);
            output_h = Math.max(1, input_h >> 1);

            output_pixels = memAlloc(output_w * output_h * comp);
            stbir_resize_uint8_generic(
                input_pixels, input_w, input_h, input_w * comp,
                output_pixels, output_w, output_h, output_w * comp,
                comp, comp == 4 ? 3 : STBIR_ALPHA_CHANNEL_NONE, STBIR_FLAG_ALPHA_PREMULTIPLIED,
                STBIR_EDGE_CLAMP,
                STBIR_FILTER_MITCHELL,
                STBIR_COLORSPACE_SRGB
            );

            if (mipmapLevel == 0) {
                stbi_image_free(byteBuffer);
            } else {
                memFree(input_pixels);
            }

            addProperty(new TextureProperties(++mipmapLevel, output_w, output_h, output_pixels));

            input_pixels = output_pixels;
            input_w = output_w;
            input_h = output_h;
        }
        if (mipmapLevel == 0) {
            stbi_image_free(byteBuffer);
        } else {
            memFree(input_pixels);
        }
        
        this.setLoaded(true);
        this.onCreate();
	}
	
	private void premultiplyAlpha() {
        int stride = getWidth() * 4;
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int i = y * stride + x * 4;

                float alpha = (byteBuffer.get(i + 3) & 0xFF) / 255.0f;
                byteBuffer.put(i + 0, (byte) Math.round(((byteBuffer.get(i + 0) & 0xFF) * alpha)));
                byteBuffer.put(i + 1, (byte) Math.round(((byteBuffer.get(i + 1) & 0xFF) * alpha)));
                byteBuffer.put(i + 2, (byte) Math.round(((byteBuffer.get(i + 2) & 0xFF) * alpha)));
            }
        }
	}
	
	public int getTexture() {
		return texture;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public int getWidth() {
		return (int) getSize().getX();
	}

	public void setWidth(int textureWidth) {
		getSize().setX(textureWidth);
	}

	public int getHeight() {
		return (int) getSize().getY();
	}

	public void setHeight(int textureHeight) {
		getSize().setY(textureHeight);
	}

	public ByteBuffer getByteBuffer() {
		return byteBuffer;
	}

	public void setByteBuffer(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public Vector2D getSize() {
		return size;
	}

	public void setSize(Vector2D size) {
		this.size = size;
	}

	public ArrayList<TextureProperties> getProperties() {
		return properties;
	}

	public void addProperty(TextureProperties property) {
		this.properties.add(property);
	}

}
