package net.blixate.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public static final int K_A              = 0x41;
    public static final int K_B              = 0x42;
    public static final int K_C              = 0x43;
    public static final int K_D              = 0x44;
    public static final int K_E              = 0x45;
    public static final int K_F              = 0x46;
    public static final int K_G              = 0x47;
    public static final int K_H              = 0x48;
    public static final int K_I              = 0x49;
    public static final int K_J              = 0x4A;
    public static final int K_K              = 0x4B;
    public static final int K_L              = 0x4C;
    public static final int K_M              = 0x4D;
    public static final int K_N              = 0x4E;
    public static final int K_O              = 0x4F;
    public static final int K_P              = 0x50;
    public static final int K_Q              = 0x51;
    public static final int K_R              = 0x52;
    public static final int K_S              = 0x53;
    public static final int K_T              = 0x54;
    public static final int K_U              = 0x55;
    public static final int K_V              = 0x56;
    public static final int K_W              = 0x57;
    public static final int K_X              = 0x58;
    public static final int K_Y              = 0x59;
    public static final int K_Z              = 0x5A;
	
	static Keyboard INSTANCE;
	
	
	public static final int KEY_COUNT = 256;
	
	private static boolean[] keys = new boolean[KEY_COUNT];
	private static boolean[] keysLast = new boolean[KEY_COUNT];
	private static String typedMessage = "";
	
	Keyboard() {}
	
	public static boolean isKeyPressed(int keyCode) {
		return keys[keyCode];
	}
	
	public static boolean isKeyJustReleased(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	public static boolean isKeyJustPressed(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public static String getTypedMessage() {
		return typedMessage;
	}
	
	public static void clearTypedMessage() {
		typedMessage = "";
	}
	
	public static void update() {
		for(int i = 0; i < KEY_COUNT; i++) {
			keysLast[i] = keys[i];
		}
	}

	public void keyTyped(KeyEvent e) {
		typedMessage += String.valueOf(e.getKeyChar());
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}
