package exosoft.iso;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import exosoft.util.Window;

public class Framework {
	protected static Environment gameWorld;
	protected static Entity player;
	protected static Window window;
	protected static JPanel sheet;
	protected static JPanel console;
	protected static JTextField consoleInput;
	protected static KeyObserver keywatch;
	protected static int gameFrequency;
	protected static int drawRate;
	protected static int metaFrequency;
	protected static boolean drawFPS;
	protected static double framerate;
	protected static volatile Timer visualHandler;
	protected static volatile Timer gameHandler;
	protected static volatile Timer metaHandler;
	private static DecimalFormat framerateFormat;

	protected static void initiateGame() {
		metaFrequency = 120;
		gameFrequency = 120;
		drawRate = 60;
		keywatch = new KeyObserver();
		gameWorld = new Environment();
	}

	protected static void initiateThreads() {
		framerateFormat = new DecimalFormat("#.00");
		metaHandler = new Timer(1000 / metaFrequency, new ActionListener() {
			boolean consoleActive = true;
			boolean pauseActionAvailable = true;
			boolean consoleActionAvailable = true;

			@Override
			public void actionPerformed(ActionEvent event) {
				detectPauseAction();
				if (consoleActive) {
					detectConsoleAction();
				}
			}

			private synchronized void detectConsoleAction() {
				if (keywatch.getKey(KeyEvent.VK_BACK_QUOTE) && consoleActionAvailable) {
					console.setVisible(true);
					consoleInput.setVisible(!consoleInput.isVisible());
					console.requestFocusInWindow();
					consoleInput.requestFocusInWindow();
				}
				consoleActionAvailable = !keywatch.getKey(KeyEvent.VK_BACK_QUOTE);
			}

			private synchronized void detectPauseAction() {
				if (keywatch.getKey(KeyEvent.VK_ESCAPE) && pauseActionAvailable) {
					if (gameHandler.isRunning()) {
						gameHandler.stop();
					} else {
						gameHandler.start();
					}
				}
				pauseActionAvailable = !keywatch.getKey(KeyEvent.VK_ESCAPE);
			}
		});

		gameHandler = new Timer(1000 / gameFrequency, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameWorld.execute();
			}
		});

		visualHandler = new Timer(1000 / drawRate, new ActionListener() {
			long logTime = System.nanoTime();

			public void actionPerformed(ActionEvent e) {
				calculateFramerate();
				gameWorld.visual();
				sheet.repaint();
			}

			private void calculateFramerate() {
				framerate = ((100 * (1 / (double) (System.nanoTime() - logTime))) * 1e7);
				logTime = System.nanoTime();
			}
		});

		metaHandler.start();
		gameHandler.start();
		visualHandler.start();
	}

	@SuppressWarnings("serial")
	protected static void initiateWindow() {
		window = new Window("Zerfall", 1280, 720);
		window.setFocusable(true);
		window.addKeyListener(keywatch = new KeyObserver());
		window.add(sheet = new JPanel() {
			@Override
			public void paintComponent(Graphics g1) {
				super.paintComponent(g1);
				Graphics2D g = (Graphics2D) g1;
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g = gameWorld.drawWorld(g);
				g.drawString(framerateFormat.format(framerate), 10, 10);
			}
		});
		sheet.setLayout(new CardLayout());
		sheet.setSize(1280, 720);
		sheet.setVisible(true);
	}

	protected static void initiateConsole() {
		sheet.add(console = new JPanel());
		console.setRequestFocusEnabled(true);
		console.setLayout(new GridBagLayout());
		console.setOpaque(false);
		console.setVisible(true);
		console.add(consoleInput = new JTextField(50));
		consoleInput.setHorizontalAlignment(JTextField.LEFT);
		consoleInput.setVisible(false);
		consoleInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (consoleInput.getText() != null) {
					readConsoleInput(consoleInput.getText());
					consoleInput.setText("");
				}
			}
		});
		consoleInput.getInputMap().put(KeyStroke.getKeyStroke('`'), "exitConsole");
		@SuppressWarnings("serial")
		Action exitConsole = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				consoleInput.setText("");
				consoleInput.setVisible(false);
				consoleInput.transferFocus();
				console.transferFocus();
			}

		};
		consoleInput.getActionMap().put("exitConsole", exitConsole);
		console.revalidate();
		sheet.revalidate();
		console.repaint();
		sheet.repaint();
	}

	private static void readConsoleInput(String data) {
		String[] splitData = data.split(" ");
		if (splitData.length == 2) {
			switch (splitData[0]) {
			case "devmode":
				switch (splitData[1]) {
				case "on":
					gameWorld.setDevmode(true);
					System.out.println("devmode on");
					break;
				case "off":
					gameWorld.setDevmode(false);
					System.out.println("devmode off");
					break;
				case "toggle":
					gameWorld.setDevmode(!gameWorld.isDevmode());
					System.out.println("devmode toggled");
					break;
				default:
					System.err.println("Invalid modifier. Valid modifiers: {on, off, toggle}");
				}
				break;
			case "drawfps":
				switch (splitData[1]) {
				case "on":
					drawFPS = true;
					System.out.println("drawfps on");
					break;
				case "off":
					drawFPS = false;
					System.out.println("drawfps off");
					break;
				case "toggle":
					drawFPS = !drawFPS;
					System.out.println("drawfps toggled");
					break;
				default:
					System.err.println("Invalid modifier. Valid modifiers: {on, off, toggle}");
					break;
				}
				break;
			case "drawrate":
				if (Integer.parseInt(splitData[1]) > 0) {
					drawRate = Integer.parseInt(splitData[1]);
					visualHandler.setDelay(1000 / drawRate);
					System.out.println("drawrate set to ".concat(Integer.toString(drawRate)));
				} else if (Integer.parseInt(splitData[1]) <= 0) {
					System.err.println("Please provide an integer greater than 0");
				}
				break;
			case "antialias":
				switch(splitData[1]) {
				case "on":
					gameWorld.setAntialiasmode(true);
					System.out.println("antialias on");
					break;
				case "off":
					gameWorld.setAntialiasmode(false);
					System.out.println("antialias off");
					break;
				case "toggle":
					gameWorld.setAntialiasmode(!gameWorld.isAntialiasmode());
					System.out.println("antialias toggled");
					break;
				default:
					System.err.println("Invalid modifier. Valid modifiers: {on, off, toggle}");
					break;
				}
				break;
			default:
				System.err.println("Command unrecognized");
				break;
			}
		} else if (splitData.length > 2) {
			System.err.println("Too many arguments given. Maximum of two arguments.");
		} else if (splitData.length < 2) {
			System.err.println("Not enough arguments given. Please specify a command and a modifier");
		}
	}
}
