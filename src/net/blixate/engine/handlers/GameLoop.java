package net.blixate.engine.handlers;

public interface GameLoop {
	
	public default void setup() {}
	
	public void loop(double deltaTime);
	
	public default void finish() {}
	
}
