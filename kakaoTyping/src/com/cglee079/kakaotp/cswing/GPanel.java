package com.cglee079.kakaotp.cswing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GPanel extends JPanel {
	private ImageIcon imgIcon;

	public GPanel(String path, String filename, int width, int height) {
		imgIcon = new ImageIcon(path + filename + ".png");
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(imgIcon.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}
}