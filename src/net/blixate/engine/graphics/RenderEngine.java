package net.blixate.engine.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import net.blixate.engine.BlixateEngine;
import net.blixate.engine.vectors.Position2D;
import net.blixate.engine.vectors.Vector2;
import net.blixate.engine.visual.Sprite;
import net.blixate.engine.visual.Sprite2D;

public class RenderEngine {
	
	// No need to worry about these, i was testing stuff.
	private static Font renderFont = new Font("SansSerif", Font.PLAIN, getFontSize());
	private static Stroke defaultStroke = new BasicStroke(0);
	private static int[] pixels;
	
	private static Random random = new Random();
	
	private static int screenWidth;
	private static int screenHeight;
	
	private static Graphics2D graphics; // I'm lazy asf
	
	private RenderEngine() {}
	
	public static void setup(Window window) {
		screenWidth = window.getWidth();
		screenHeight = window.getHeight();
		pixels = ((DataBufferInt) window.getImage().getRaster().getDataBuffer()).getData();
		graphics = window.getImage().createGraphics();
	}
	
	public static void setPixel(int x, int y, int color) {
		if(x >= screenWidth || x < 0 || y >= screenHeight || y < 0)
			return;
		if ((color >> 24) == 0)
			return;
		setPixel(Position2D.getPixelPosition(x, y, BlixateEngine.window().getWidth()), color);
	}

	public static void setPixel(int index, int color) {
		if(index >= pixels.length) {
			return;
		}
		if(index < 0) {
			return;
		}
		pixels[index] = color;
	}
	
	/**
	 * Get a pixel at a location in the screen.
	 * 
	 * @return Color of pixel
	 */
	public static int getPixel(int index) {
		return pixels[index];
	}
	
	public static int randomNoise() {
		int brightness = random.nextInt() & 0xFF;
		int value = ((brightness & 0xFF) << 24) |
					((brightness & 0xFF) << 16) |
					((brightness & 0xFF) << 8)  |
							(brightness & 0xFF);
		return value;
	}

	public static void drawRandomNoise() {
		for(int i = 0; i < pixels.length; i ++)
			pixels[i] = randomNoise();
	}
	
	public static void drawTriangle(Vector2 pos1, Vector2 pos2, Vector2 pos3) {
		graphics.setColor(Color.white);
		drawLine(pos1, pos2);
		drawLine(pos2, pos3);
		drawLine(pos3, pos1);
	}
	
	public static void drawTriangle(Position2D pos1, Position2D pos2, Position2D pos3, int color) {
		graphics.setColor(new Color(color));
		drawLine(pos1, pos2);
		drawLine(pos2, pos3);
		drawLine(pos3, pos1);
	}
	
	public static void drawCircle(Position2D pos, int radius, int color) {
		drawEllipse(pos.getX(), pos.getY(), radius, radius, color, false);
	}
	
	public static void drawCircle(Position2D pos, int radius, int color, boolean filled) {
		drawEllipse(pos.getX(), pos.getY(), radius, radius, color, filled);
	}
	
	public static void drawEllipse(Position2D pos, Vector2 size, int color, boolean filled) {
		drawEllipse(pos.getX(), pos.getY(), size.getX(), size.getY(), color, filled);
	}
	
	private static void drawEllipse(int x, int y, int width, int height, int color, boolean filled) {
		graphics.setColor(new Color(color));
		float scale = BlixateEngine.window().getScale();
		if(filled) graphics.fillOval(x, y, width, height);
		else graphics.drawOval((int)(x / scale), (int)(y / scale), width, height);
	}
	
	public static void drawNoiseBox(Position2D pos, Vector2 size) {
		if(pos.getX() > screenWidth || pos.getY() > screenHeight)
			return;
		if(pos.getX() + size.getX() < 0 || pos.getY() + size.getY() < 0)
			return;
		int[] offscreenMath = offscreenMath(pos, size);
		int bx = offscreenMath[0];
		int by = offscreenMath[1];
		int newWidth = offscreenMath[2];
		int newHeight = offscreenMath[3];
		for(int y = by; y < by + newHeight; y ++)
			for(int x = bx; x < bx + newWidth; x ++)
				setPixel(Position2D.getPixelPosition(x, y, BlixateEngine.window().getWidth()), randomNoise());
	}
	
	public static void drawRandomColorNoise() {
		for(int i = 0; i < pixels.length; i ++)
			pixels[i] = random.nextInt();
	}
	
	public static void drawImage(Sprite image, Vector2 pos, boolean flip) {
		int bx, by, newWidth, newHeight;
		bx = 0;
		by = 0;
		newWidth = image.getWidth();
		newHeight = image.getHeight();
		
		// The most confusing draw i've ever seen...
		for(int y = bx; y < newHeight; y++)
			for(int x = by; x < newWidth; x++)
				setPixel(y + pos.getY(), x + pos.getX(), image.getPixel(x, y));
	}
	
	public static void drawImageCentered(Sprite image, Position2D pos) {
		drawImage(image, pos.subtract(image.getSize().divide(new Vector2(2, 2))), false);
	}
	
	public static void drawLine(Vector2 pos1, Vector2 pos2, int color) {
		graphics.setColor(new Color(color));
		drawLine(pos1, pos2);
	}
	
	private static void drawLine(Vector2 pos1, Vector2 pos2) {
		graphics.drawLine(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
	}
	
	public static void drawLineWithThickness(Vector2 pos1, Vector2 pos2, int thicc, int color) {
		graphics.setStroke(new BasicStroke(thicc));
		drawLine(pos1, pos2, color);
		graphics.setStroke(defaultStroke);
	}
	
	public static void drawSprite2D(Sprite2D sprite) {
		// From StackOverflow, tweaked to be more... good
		BufferedImage image = sprite.getSprite().getBufferedImage();
		double locationX = sprite.getSprite().getWidth() / 2;
		double locationY = sprite.getSprite().getHeight() / 2;
		AffineTransform tx = null;
		AffineTransformOp op = null;
		if(sprite.getRotation() > 0) {
			tx = AffineTransform.getRotateInstance(Math.toRadians(sprite.getRotation()), locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		}
		if(op == null) {
			graphics.drawImage(image, sprite.getPosition().getX(), sprite.getPosition().getY(), null);
		}else {
			graphics.drawImage(op.filter(image, null), sprite.getPosition().getX(), sprite.getPosition().getY(), null);
		}
	}
	
	public static int getFontSize() {
		return (int)BlixateEngine.window().getScale() * 12;
	}
	
	public static void drawText(Position2D pos, String text, int color) {
		graphics.setColor(new Color(color));
		graphics.setFont(renderFont);
		graphics.drawString(text, pos.getX(), pos.getY() + getFontSize());
	}
	
	public static void drawBox(Position2D pos, Vector2 size, int color) {
		int[] offscreenMath = offscreenMath(pos, size);
		int bx = offscreenMath[0];
		int by = offscreenMath[1];
		int newWidth = offscreenMath[2];
		int newHeight = offscreenMath[3];
		for(int y = by; y < by + newHeight; y ++)
			for(int x = bx; x < bx + newWidth; x ++)
				setPixel(x, y, color);
	}
	
	public static void clear() {
		fillScreen(Color.black);
	}
	
	public static void fillScreen(Color color) {
		fillScreen(color.getRGB());
	}
	
	public static void fillScreen(int color) {
		for(int i = 0; i < pixels.length; i ++)
			pixels[i] = color;
	}
	
	public static int getPixelCount() {
		return pixels.length;
	}
	
	public static int[] offscreenMath(Vector2 pos, Vector2 size) {
		int bx = pos.getX();
		int by = pos.getY();
		int newWidth = size.getX();
		int newHeight = size.getY();
		if(bx + newWidth > screenWidth)
			newWidth -= ((bx + newWidth) - screenWidth);
		if(by + newHeight > screenHeight)
			newHeight -= ((by + newHeight) - screenHeight);
		return new int[] { bx, by, newWidth, newHeight};
	}
}
