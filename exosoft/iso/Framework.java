package exosoft.iso;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Framework {
	protected static Environment gameWorld;
	protected static Character player;
	protected static JPanel sheet;
	protected static KeyObserver keywatch;
	protected static int gameFrequency;
	protected static int drawRate;
	protected static int metafrequency;
	protected static volatile Timer visualHandler;
	protected static volatile Timer gameHandler;
	protected static volatile Timer metaHandler;
	protected static JTextField consoleInput;
	protected static JPanel console;
	protected static Window window;
	protected static JPanel sheet;
	
	protected void initiateGame() {
		metaFrequency = 120;
		gameFrequency = 120;
		drawRate = 60;
		keywatch = new KeyObserver();
		gameWorld = new Environment();
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
}
