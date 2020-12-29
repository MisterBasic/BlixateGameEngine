package net.blixate.engine.visual;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.blixate.engine.vectors.Position2D;
import net.blixate.engine.vectors.Vector2;

public class Sprite {
	
	Vector2 size;
	int[] pixels;
	BufferedImage image;
	
	public Sprite(String fileName) {
		this(fileName, new Vector2(0, 0), new Vector2(0, 0));
	}
	public Sprite(String fileName, Vector2 start, Vector2 end) {
		this(fileName, start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	public Sprite(String fileName, int x, int y, int width, int height) {
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(i != null) {
			this.size = new Vector2(i.getWidth(), i.getHeight());
			this.pixels = i.getRGB(x, y, this.getWidth()-width, this.getHeight()-height, null, 0, this.getWidth());
			this.image = i;
		}
	}
	
	public Sprite(BufferedImage i, int x, int y, int width, int height) {
		this.size = new Vector2(i.getWidth(), i.getHeight());
		this.pixels = i.getRGB(x, y, this.getWidth()-width, this.getHeight()-height, null, 0, this.getWidth());
	}
	
	public int getWidth() {
		return this.getSize().getX();
	}

	public int getHeight() {
		return this.getSize().getY();
	}

	public void setPixel(Position2D pos, int color) {
		setPixel(pos.getPixelPosition(), color);
	}

	public void setPixel(int index, int color) {
		if(index >= this.pixels.length || index < 0)
			return;
		this.pixels[index] = color;
	}

	public int[] getPixels() {
		return pixels;
	}

	public int getPixel(int x, int y) {
		return this.pixels[Position2D.getPixelPosition(y, x, this.getWidth())];
	}
	public Vector2 getSize() {
		return this.size;
	}
	public BufferedImage getBufferedImage() {
		return image;
	}
}
