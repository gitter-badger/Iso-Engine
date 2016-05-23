package exosoft.iso;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyObserver implements KeyListener {

	private static boolean[] keyStatus = new boolean[512];

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
		return keyStatus[index];
	}

	public void setKey(int index, boolean status) {
		keyStatus[index] = status;
	}
	
	public void clearKeys() {
		for (int i = 0; i < keyStatus.length; i++) {
			setKey(i, false);
		}
	}

}
