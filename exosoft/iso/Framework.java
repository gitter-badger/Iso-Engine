package exosoft.iso;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		metaFrequency = 30;
		gameFrequency = 60;
		drawRate = 60;
		keywatch = new KeyObserver();
		gameWorld = new Environment();
	}

	protected static void initiateThreads() {
		framerateFormat = new DecimalFormat("#.00");
		metaHandler = new Timer(1000 / metaFrequency, new ActionListener() {
			boolean pauseActionAvailable = true;

			@Override
			public void actionPerformed(ActionEvent event) {
				detectPauseAction();
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

	protected static void initiateWindow() {
		window = new Window("Zerfall", 1280, 720);
		window.setFocusable(true);
		window.addKeyListener(keywatch = new KeyObserver());
		window.add(sheet = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7840979712281271537L;

			@Override
			public void paintComponent(Graphics g1) {
				super.paintComponent(g1);
				Graphics2D g = (Graphics2D) g1;
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g = gameWorld.drawWorld(g);
				if (drawFPS) {
					g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
					g.drawString(framerateFormat.format(framerate), 10, 10);
				}
			}
		});
		sheet.setLayout(new CardLayout());
		sheet.setSize(1280, 720);
		sheet.requestFocus();
	}

	protected static void initiateConsole() {
		sheet.add(console = new JPanel());
		console.setOpaque(false);

		console.add(consoleInput = new JTextField(50));
		consoleInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (consoleInput.getText() != null) {
					readConsoleInput(consoleInput.getText());
					consoleInput.setText("");
				}
			}
		});
		sheet.getInputMap().put(KeyStroke.getKeyStroke('`'), "openConsole");
		sheet.getActionMap().put("openConsole", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5717123701261130979L;

			public void actionPerformed(ActionEvent e) {
				consoleInput.setVisible(true);
				consoleInput.requestFocusInWindow();
				keywatch.clearKeys();
			}
		});

		consoleInput.getInputMap().put(KeyStroke.getKeyStroke('`'), "exitConsole");
		Action exitConsole = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2212449898139510841L;

			@Override
			public void actionPerformed(ActionEvent e) {
				consoleInput.setText("");
				consoleInput.setVisible(false);
				sheet.requestFocusInWindow();
			}

		};
		consoleInput.getActionMap().put("exitConsole", exitConsole);
		console.revalidate();
		sheet.revalidate();
	}

	public static void finalizeWindow() {
		window.setVisible(true);
		sheet.setVisible(true);
		console.setVisible(true);
		consoleInput.setVisible(false);
		window.requestFocus();
	}

	/**
	 * Reads input from the console, and alters variables during runtime based
	 * on the input. In the future, developers will be able to add their own
	 * cases to the switch.
	 * 
	 * @author GingerDeadshot
	 * @param data
	 */
	private static void readConsoleInput(String consoleInput) {
		String[] splitData = consoleInput.split(" ");
		String command;
		String modifier;
		if (splitData.length == 2) {
			command = splitData[0];
			modifier = splitData[1];
			switch (command) {
			case "devmode":
				switch (modifier) {
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
				switch (modifier) {
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
				if (Integer.parseInt(modifier) > 0) {
					drawRate = Integer.parseInt(modifier);
					visualHandler.setDelay(1000 / drawRate);
					System.out.println("drawrate set to ".concat(modifier));
				} else {
					System.err.println("Please provide an integer greater than 0");
				}
				break;
			case "antialias":
				switch (splitData[1]) {
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
			case "gravity":
				if (Double.parseDouble(splitData[1]) > 0) {
					gameWorld.setGravity(Double.parseDouble(splitData[1]));
					System.out.println("gravity set to ".concat(splitData[1]));
				} else if (Double.parseDouble(splitData[1]) <= 0) {
					System.err.println("Please provide a decimal greater than 0");
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
