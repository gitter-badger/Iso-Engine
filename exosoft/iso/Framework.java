package exosoft.iso;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Framework {
	protected static Environment gameWorld;
	protected static Character player;
	protected static Window window;
	protected static JPanel sheet;
	protected static JPanel console;
	protected static JTextField consoleInput;
	protected static KeyObserver keywatch;
	protected static int gameFrequency;
	protected static int drawRate;
	protected static int metafrequency;
	protected static boolean drawFPS;
	protected static double framerate;
	protected static volatile Timer visualHandler;
	protected static volatile Timer gameHandler;
	protected static volatile Timer metaHandler;
	
	protected static void initiateGame() {
		metaFrequency = 120;
		gameFrequency = 120;
		drawRate = 60;
		keywatch = new KeyObserver();
		gameWorld = new Environment();
	}
	
	protected static void initiateThreads() {
		metaHandler = new Timer(1000 / 120, new ActionListener() {
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
					consoleInput.setVisible(!consoleInput.isVisible());
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
				player.movement();
				player.physics();
				gameWorld.execute();
			}
		});
		
		visualHandler = new Timer(1000 / drawRate, new ActionListener() {
			long logTime = system.nanoTime();
			
			public void actionPerformed(ActionEvent e) {
				calculateFramerate();
				player.visual();
				sheet.repaint();
			}
			
			private void calculateFramerate(long currentTime) {
				framerate = (System.nanoTime() - logTime).doubleValue() * 1000;
				logTime = System.nanoTime();
			} 
		});
	}
	
	protected void initiateWindow() {
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
				g.drawString(10, 10, framerate);
			}
		});
		sheet.setLayout(new CardLayout());
		sheet.setSize(1280, 720);
		sheet.setVisible(true);
	}
	
	protected void initiateConsole() {
		sheet.add(console = new JPanel());
		console.setLayout(new GridBagLayout());
		console.setOpaque(false);
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
				switch(splitData[1]) {
				case "on":
					drawFPS = true;
					break;
				case "off":
					drawFPS = false;
					break;
				case "toggle":
					drawFPS = !drawFPS;
					break;
				default:
					System.err.println("Invalid modifier. Valid modifiers: {on, off, toggle}");
					break;
				}
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
