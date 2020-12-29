package net.blixate.engine.visual;

import net.blixate.engine.transform.Transform2D;
import net.blixate.engine.vectors.Position2D;

public class Sprite2D extends Transform2D {
	
	public Sprite instance;
	
	public Sprite2D(Sprite instance){
		this.instance = instance;
	}
	
	public Sprite getSprite() {
		return this.instance;
	}
	
	public int getPixel(int x, int y) {
		return instance.pixels[Position2D.getPixelPosition(y, x, instance.getWidth())];
	}
	
}
