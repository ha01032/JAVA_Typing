package com.cglee079.kakaotp.cswing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import com.cglee079.kakaotp.sound.SoundPlayer;

public class GRadioButton extends JRadioButton {
	private String path;
	private String id;

	public String getPath() {
		return path;
	}

	public String getId() {
		return id;
	}

	public GRadioButton(String path, String filename, int width, int height) {
		this.path = path;
		this.id = filename;

		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setSize(width, height);

		setIcon(new ImageIcon(path + filename + ".png"));
		setSelectedIcon(new ImageIcon(path + filename + "_selected" + ".png"));
		setRolloverIcon(new ImageIcon(path + filename + "_hover" + ".png"));
		
		addMouseListener(new BtnMouseListener());
	}
	
	class BtnMouseListener extends MouseAdapter{
		public void mouseEntered(MouseEvent e){
//sound-off 8
			//			SoundPlayer.play("btn_hover.wav");			
		}
		public void mousePressed(MouseEvent e){
//sound-off 9
			//			SoundPlayer.play("btn_click.wav");
		}
	}
	
}