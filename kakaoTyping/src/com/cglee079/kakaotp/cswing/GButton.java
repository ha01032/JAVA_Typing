package com.cglee079.kakaotp.cswing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.cglee079.kakaotp.sound.SoundPlayer;

public class GButton extends JButton {
	String path;
	String id;

	public String getPath() {
		return path;
	}

	public String getId() {
		return id;
	}

	public GButton(String path, String filename, int width, int height) {
		this.path = path;
		this.id = filename;

		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setSize(width, height);

		setIcon(new ImageIcon(path + filename + ".png"));
		setRolloverIcon(new ImageIcon(path + filename + "_hover" + ".png"));
		
		addMouseListener(new BtnMouseListener());
	}
	
	class BtnMouseListener extends MouseAdapter{
		public void mouseEntered(MouseEvent e){
//sound-off 6
			//			SoundPlayer.play("btn_hover.wav");			
		}
		public void mousePressed(MouseEvent e){
//sound-off 7
			//			SoundPlayer.play("btn_click.wav");
		}
	}

}