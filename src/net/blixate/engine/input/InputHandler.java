package net.blixate.engine.input;

public class InputHandler {
	
	private static int instanceCount = 0;
	
	private Mouse mouse;
	private Keyboard keyboard;
	
	public InputHandler() {
		if(instanceCount > 0)
			return; // Do not let another instance of Mouse and Keyboard be created.
		mouse = new Mouse();
		Mouse.INSTANCE = mouse;
		keyboard = new Keyboard();
		Keyboard.INSTANCE = keyboard;
		instanceCount++;
	}
	public Mouse getMouse() {
		return mouse;
	}
	public Keyboard getKeyboard() {
		return keyboard;
	}
}
