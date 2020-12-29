package net.blixate.engine.vectors;

import net.blixate.engine.BlixateEngine;

public class Position2D extends Vector2 {
	
	public Position2D(float x, float y) {
		super(x, y);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public int getPixelPosition() {
		return getPixelPosition(this.x, this.y, BlixateEngine.window().getWidth());
	}
	
	public static int getPixelPosition(float x, float y, float width) {
		return (int) (x * width + y);
	}

	public Position2D offset(int offx, int offy) {
		return new Position2D(this.x + offx, this.y + offy);
	}

	public Vector2 negative() {
		return new Vector2(-this.x, -this.y);
	}

	public Vector2 flipAxis() {
		return new Vector2(this.y, this.x);
	}
}
