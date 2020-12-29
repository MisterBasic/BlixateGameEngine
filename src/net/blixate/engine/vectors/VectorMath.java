package net.blixate.engine.vectors;

public class VectorMath {
	
	private VectorMath() {}
	
	public static Vector2 normal(float x, float y) {
		double l = x * x + y * y;
		if (l != 0) {
			l = Math.sqrt(l);
			x /= l;
			y /= l;
		}
		return new Vector2(x, y);
	}
	
	public static double distance(Vector2 vec1, Vector2 vec2) {
		return Math.sqrt((vec1.x - vec2.x) * (vec1.x - vec2.x) + (vec1.y - vec2.y) * (vec1.y - vec2.y));
	}
}
