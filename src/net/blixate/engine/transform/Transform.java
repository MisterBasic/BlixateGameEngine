package net.blixate.engine.transform;

import net.blixate.engine.vectors.Position2D;
import net.blixate.engine.vectors.Vector2;

public interface Transform {
	
	public Position2D getPosition();
	public Vector2 getSize();
	public int getRotation();
	public Vector2 getScale();
	public Vector2 getRotationOffset();
	
	public void setPosition(Position2D pos);
	public void translate(Vector2 by);
	public void setSize(Vector2 size);
	public void resize(Vector2 size);
	public void setScale(Vector2 scale);
	public void scale(Vector2 scaleToSize);
	public void setRotation(int degrees);
	public void rotate(double d);
	
}
