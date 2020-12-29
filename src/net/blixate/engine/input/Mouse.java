package net.blixate.engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.blixate.engine.BlixateEngine;
import net.blixate.engine.vectors.Position2D;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	static Mouse INSTANCE;
	
	private static Position2D mousePosition = new Position2D(0, 0);
	private static boolean[] buttonStates = new boolean[5];
	private static int scrollWheelState = 0;
	
	Mouse() {}
	
	public static Position2D getMousePosition() {
		return mousePosition;
	}
	
	public static Position2D getMousePosition(float offsetX, float offsetY) {
		return mousePosition.offset((int)offsetX, (int)offsetY);
	}
	
	public static int getX() {
		return mousePosition.getX();
	}
	public static int getY() {
		return mousePosition.getY();
	}
	
	public static boolean isButtonPressed(int buttonId) {
		return buttonStates[buttonId];
	}
	
	public static int getScroll() {
		return scrollWheelState;
	}
	
	public void mouseDragged(MouseEvent e) {mouseMoved(e);}
	public void mouseMoved(MouseEvent e) {
		mousePosition.setX((int)(e.getX() / BlixateEngine.window().getScale()));
		mousePosition.setY((int)(e.getY() / BlixateEngine.window().getScale()));
	}
	public void mousePressed(MouseEvent e) 
		{buttonStates[e.getButton()] = true;}
	public void mouseReleased(MouseEvent e) {buttonStates[e.getButton()] = false;}
	public void mouseWheelMoved(MouseWheelEvent e) {scrollWheelState = e.getScrollAmount();}
	public void mouseEntered(MouseEvent e) {} // Don't care about this event
	public void mouseExited(MouseEvent e) {} // Don't care about this event
	public void mouseClicked(MouseEvent e) {} // Don't care about this event
}
