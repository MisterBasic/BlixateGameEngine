package net.blixate.test;

import net.blixate.engine.BlixateEngine;
import net.blixate.engine.graphics.Window;
import net.blixate.engine.handlers.GameLoop;

public class Main {
	
	public static void main(String[] args) {
		BlixateEngine.createWindow(new Window(800, 600, "Graphics", 1f));
		GameLoop loop = new MyGameLoop();
		BlixateEngine.setGameLoop(loop);
		BlixateEngine.start();
	}
}
