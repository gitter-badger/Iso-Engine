package exosoft.iso;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import exosoft.iso.Environment;
import exosoft.iso.Framework;
import exosoft.iso.Object;
import exosoft.iso.Sprite.SheetType;
import exosoft.util.KeyObserver;
import exosoft.util.Window;
import kuusisto.tinysound.TinySound;

@SuppressWarnings("serial")
public class Test extends Framework {
	static Character player;

	public static void main(String[] uselessbullshit) {
		TinySound.init();
		logicRate = 120;
		drawRate = 60;
		sheet = new Sheet();
		keywatch = new KeyObserver();
		map = new Environment();
		player = new Character(SheetType.HORIZONTAL, "resources/sprites/player.png", 175, 161, keywatch);
		player.setLocation(0, 0);
		player.setVelocity(0);
		map.addObject(new Object(new Point(250, 300), new Point(720, 300), new Point(720, 325), new Point(250, 325)));
		map.addObject(new Object(new Point(50, 600), new Point(1080, 600), new Point(1080, 625), new Point(50, 625)));
		map.spawnEntity(player);

		drawTimer = new Timer(1000 / drawRate, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				player.visual();
				sheet.repaint();
			}
		});
		logicTimer = new Timer(1000 / logicRate, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.movement();
				player.physics();
				map.execute();
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Window window = new Window("Iso-Test", 1280, 720);
				window.add(sheet);
				window.setFocusable(true);
				window.addKeyListener(keywatch);
			}
		});
		logicTimer.start();
		drawTimer.start();
	}

	static class Sheet extends JPanel {
		@Override
		public void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			Graphics2D g = (Graphics2D) g1;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(player.getSprite(player.getSpriteNum()), player.getIntX(), player.getIntY(),
					null);
			g.setColor(Color.red);
			g.draw(player.getBounds());
			g.setColor(Color.blue);
			g = map.drawObjects(g);
			int x = 50;
			int y = 50;
			int x2 = 120;
			int y2 = 90;
			g.drawLine(x, y, x2, y2);
			g.setColor(Color.GREEN);
			int xvector = x - x2;
			int yvector = y - y2;
			g.drawLine(y2 - xvector, x2 - yvector, y + xvector / 2, x + yvector / 2);
			g.dispose();
		}
	}

}