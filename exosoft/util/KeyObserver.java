package exosoft.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class KeyObserver implements KeyListener {
	
	private static boolean[] keys = new boolean[512];

	@Override
	public void keyTyped(KeyEvent e) {
		setKey(e.getKeyCode(), true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		setKey(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		setKey(e.getKeyCode(), false);
	}
	
	public boolean getKey(int index) {
		return keys[index];
	}

	public void setKey(int index, boolean status) {
		keys[index] = status;
	}

}
