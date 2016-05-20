package exosoft.util;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Custom JFrame that can hold a JPanel for drawing. Has all the features of a
 * JFrame, with some customizations made.
 * 
 * @extends JFrame
 */
public class Window extends JFrame {
	private static final long serialVersionUID = -8208101772064469647L;

	public Window(String title, int width, int height) {
		this();
		setTitle(title);
		setSize(width, height);
	}

	public Window(String title, int width, int height, JPanel panel) {
		this();
		setTitle(title);
		setSize(width, height);
		add(panel);
	}

	public Window(int width, int height) {
		this();
		setSize(width, height);
	}

	public Window(String title) {
		this();
		setTitle(title);
	}

	public Window() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}