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
}
