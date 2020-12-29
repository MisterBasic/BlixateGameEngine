package net.blixate.engine;

import net.blixate.engine.graphics.RenderEngine;
import net.blixate.engine.graphics.Window;
import net.blixate.engine.handlers.GameLoop;
import net.blixate.engine.input.InputHandler;

public class BlixateEngine {
	private static int targetFps = 60;
	private static int state = 0;
	private static Thread gameThread;
	private static Window window;
	private static GameLoop gameLoop;
	private static InputHandler inputHandler = new InputHandler();
	
	private BlixateEngine() {}
	
	public static void start() {
		if(state != 1) {
			if(state == 0)
				printError("Must call createWindow() before start().");
			else if(state == 2)
				printError("Cannot call start() twice.");
			return;
		}
		/*
		 * Create our listeners, so we can get input from the Keyboard and Mouse
		 */
		// Keyboard Listener
		window().getCanvas().addKeyListener(inputHandler.getKeyboard()); 
		// mouse listeners
		window().getCanvas().addMouseListener(inputHandler.getMouse());
		window().getCanvas().addMouseMotionListener(inputHandler.getMouse());
		window().getCanvas().addMouseWheelListener(inputHandler.getMouse());
		if(gameLoop != null) {
			if(state == 3) {
				printError("GameLoop already running.");
			}
			createGameLoop(gameLoop);
		}
		state = 2;
	}
	
	private static void createGameLoop(GameLoop gameLoop) {
		gameThread = new Thread(new Runnable() {
			public void run() {
				gameLoop.setup();
				while(!window().isClosed()) {
					double elapsedTime = window().getElapsedTime();
					gameLoop.loop(elapsedTime);
				}
				gameLoop.finish();
			}
		});
		gameThread.start();
		state = 3;
	}
	
	public static void createWindow(Window gameWindow) {
		if(state == 1) {
			printError("createWindow() can only be called once.");
			return;
		}
		window = gameWindow;
		RenderEngine.setup(window);
		state = 1;
	}
	
	public static Window window() {
		return window;
	}

	public static void setGameLoop(GameLoop handler) {
		gameLoop = handler;
	}
	
	public static int getFPS() {
		return window.getFPS();
	}

	public int getPixelCount() {
		return RenderEngine.getPixelCount();
	}
	
	public static int getTargetFps() {
		return targetFps;
	}
	
	public static Thread getGameThread() {
		return gameThread;
	}

	public static void setTargetFps(int target) {
		targetFps = target;
	}
	
	private static void printError(String errorString) {
		System.err.println(errorString);
	}

	
}
