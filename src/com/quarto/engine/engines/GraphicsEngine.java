package com.quarto.engine.engines;
import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import com.quarto.engine.GameEngine;
import com.quarto.engine.core.Engine;

public class GraphicsEngine extends Engine{
	
	private long window;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		GameEngine.Log("Hello LWJGL " + Version.getVersion() + "!");

		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow((int) (getGameEngine().getWindowSize().getX() * getGameEngine().getPixelsPerMeter()), (int) (getGameEngine().getWindowSize().getY() * getGameEngine().getPixelsPerMeter()), "Quarto - équipe 1 du projet INFO1", NULL, NULL);
		this.getGameEngine().setWindow(window);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(0);

		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, (int) getGameEngine().getWindowSize().getX() * getGameEngine().getPixelsPerMeter(), (int) getGameEngine().getWindowSize().getY() * getGameEngine().getPixelsPerMeter(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        try {
            BufferedImage bi = ImageIO.read(new File("res/img/icons/32x32.png"));
            byte[] iconData = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
            ByteBuffer ib = createByteBuffer(iconData.length);
            ib.put(iconData);
            ib.flip();
            
            GLFWImage.Buffer gb = GLFWImage.create(1);
            gb.put(0, GLFWImage.create().set(32, 32, ib));

            glfwSetWindowIcon(window, gb);
        }catch (Exception e){
        	e.printStackTrace();
        }
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		if(!glfwWindowShouldClose(window)){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            glfwPollEvents();
            this.getGameEngine().getCurrentScene().onDraw(delta, getGameEngine().getPixelsPerMeter());
            for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
				if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i)
					if(this.getGameEngine().getCurrentScene().getSceneObjects().get(i).isVisible())
						this.getGameEngine().getCurrentScene().getSceneObjects().get(i).onDraw(delta, getGameEngine().getPixelsPerMeter());
            for (int i = 0; i < this.getGameEngine().getCurrentScene().getParticles().size(); i++)
				if(this.getGameEngine().getCurrentScene().getParticles().size() > i)
					if(this.getGameEngine().getCurrentScene().getParticles().get(i).isVisible())
						this.getGameEngine().getCurrentScene().getParticles().get(i).onDraw(delta, getGameEngine().getPixelsPerMeter());
			glfwSwapBuffers(window);
			glfwPollEvents();
		}else{
			this.getGameEngine().onDestroy();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}
