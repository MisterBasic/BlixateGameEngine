package net.blixate.engine.graphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Window implements WindowListener{
	
	protected JFrame frame;
	private Canvas canvas;
	private BufferedImage image;
	private BufferStrategy bs;
	private Graphics graphics;
	
	private int width;
	private int height;
	private String title;
	private float scale;
	
	private int fps = 0;
	
	private boolean isClosed;
	private double deltaTime;
	private double lastTime;
	private double firstTime;
	private double frameTimer;
	private int frameCounter;
	
	/**
	 * Create an instance of a {@code Window}
	 */
	
	public Window(int width, int height, String title, float scale) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.scale = scale;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.canvas = new Canvas();
		Dimension d = new Dimension();
		d.setSize((int) getWidth() * this.scale, (int) getHeight() * this.scale);
		this.canvas.setPreferredSize(d);
		this.canvas.setMaximumSize(d);
		this.canvas.setMinimumSize(d);
		this.setupFrame();
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		graphics = bs.getDrawGraphics();
	}
	
	public Window(int width, int height, String title) {
		this(width, height, title, 1f);
	}
	
	private void setupFrame() {
		frame = new JFrame(title);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		ImageIcon image = new ImageIcon("./icon.bmp");
		frame.setIconImage(image.getImage());
	}
	
	public void update() {
		graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
		fpsUpdate();
	}
	
	public double now() {
		return (System.nanoTime()) / 1000000000.0;
	}
	
	private void fpsUpdate() {
		// Elapsed Time / Delta Time
		this.firstTime = now();
		this.deltaTime = firstTime - lastTime;
		this.lastTime = firstTime;
		
		// FPS counter
		this.frameTimer += deltaTime;
		this.frameCounter += 1;
		if(this.frameTimer > 1) {
			fps = this.frameCounter;
			this.frameCounter = 0;
			this.frameTimer = 0;
		}
	}
	
	public double getElapsedTime() {
		return deltaTime;
	}
	
	public boolean isClosed() {
		return this.isClosed;
	}
	
	public void destroy() {
		this.frame.dispose();
	}

	public int getWidth() {
		return width;
	}

	public String getTitle() {
		return title;
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	public Graphics getGraphics(){
		return this.graphics;
	}
	
	protected BufferedImage getImage() {
		return this.image;
	}

	public void setTitle(String title) {
		this.title = title;
		frame.setTitle(this.title);
	}

	public int getHeight() {
		return height;
	}

	public int getFPS() {
		return fps;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public float getScale() {
		return this.scale;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		isClosed = false;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		isClosed = true;
		destroy();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
}
