package exosoft.iso;

import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Framework {
	protected static Environment map;
	protected static Character player;
	protected static JPanel sheet;
	protected static KeyListener keywatch;
	protected static int logicRate;
	protected static int drawRate;
	protected static volatile Timer drawTimer;
	protected static volatile Timer logicTimer;
}
