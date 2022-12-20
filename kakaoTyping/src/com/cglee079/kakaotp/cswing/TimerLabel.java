package com.cglee079.kakaotp.cswing;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TimerLabel extends JLabel{
	public TimerLabel(ImageIcon imageIcon){
		super(imageIcon);
		setVisible(false);
	}
	
	public void action(int time){
		setVisible(true);
		Timer t = new Timer(false);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				setVisible(false);
			}
		}, time);
	}

}