package net.blixate.engine.transform;

import net.blixate.engine.vectors.Position2D;
import net.blixate.engine.vectors.Vector2;

/**
 * Represents a object inside virtual space.
 * 
 * Each object has a position, size and rotation.
 * 
 * Rotation of the object is optional, and not really easy to show.
 */
public class Transform2D implements Transform {

	Position2D position = new Position2D(0, 0);
	Vector2 size;
	Vector2 scale = new Vector2(1, 1);
	int rotation = 0;
	Vector2 rotationOffset = new Vector2(0, 0);
	
	public Transform2D() {}
	
	@Override
	public Position2D getPosition() {
		return position;
	}

	@Override
	public Vector2 getSize() {
		return size.multiply(scale);
	}

	@Override
	public int getRotation() {
		return rotation;
	}

	@Override
	public void setPosition(Position2D pos) {
		position = pos;
	}

	@Override
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	public void resize(Vector2 size) {
		this.size.add(size);
	}

	@Override
	public void rotate(double d) {
		rotation += d;
		rotation %= 360f;
	}

	@Override
	public void translate(Vector2 by) {
		position.add(by);
	}
	
	public void setScale(Vector2 scale) {
		this.scale = scale;
	}
	
	@Override
	public void scale(Vector2 scaleToSize) {
		scale.add(scaleToSize);
	}

	@Override
	public void setRotation(int degrees) {
		rotation = degrees;
	}

	@Override
	public Vector2 getScale() {
		return this.scale;
	}

	@Override
	public Vector2 getRotationOffset() {
		return rotationOffset;
	}

}
