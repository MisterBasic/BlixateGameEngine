package net.blixate.engine.vectors;

public class Vector2 {
	float x, y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	
	public double distanceTo(Vector2 vec) {
		return VectorMath.distance(this, vec);
	}
	
	public void normalize() {
		Vector2 normal = this.normal();
		this.x = normal.x;
		this.y = normal.y;
	}
	
	public Vector2 normal() {
		return VectorMath.normal(this.x, this.y);
	}
	
	public Vector2 add(Vector2 vec) {
		return new Vector2(this.x + vec.x, this.y + vec.y);
	}
	
	public Vector2 subtract(Vector2 vec) {
		return new Vector2(this.x - vec.x, this.y - vec.y);
	}
	
	public Vector2 multiply(Vector2 vec) {
		return new Vector2(this.x * vec.x, this.y * vec.y);
	}
	
	public Vector2 divide(Vector2 vec) {
		return new Vector2(this.x / vec.x, this.y / vec.y);
	}
	public Vector2 squared() {
		return new Vector2(this.x * this.x, this.y * this.y);
	}
}
