package com.cglee079.kakaotp.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cglee079.kakaotp.cswing.GButton;
import com.cglee079.kakaotp.play.Play;
import com.cglee079.kakaotp.util.ColorManager;
import com.cglee079.kakaotp.util.MainPosition;
import com.cglee079.kakaotp.util.PathManager;

public class PauseFrame extends JFrame {
	private final String PATH = PathManager.PAUSE_FRAME;
	private MainFrame mainFrame;
	private Play play;
	
	public PauseFrame() {
		setLayout(new FlowLayout());
		setSize(150, 180);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);// 크기 고정
		setUndecorated(true);
		setBackground(ColorManager.baseColor);
		setVisible(true);
		setShape(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 50, 50));
		setLocation(MainPosition.x - (this.getWidth()/2), MainPosition.y - (this.getHeight()/2));

		add(new ButtonPanel());
		revalidate();   //revalidate 3
	}
	
	public PauseFrame(MainFrame mainFrame, Play play){
		this();
		this.play = play;
		this.mainFrame = mainFrame;
	}

	class ButtonPanel extends JPanel {
		private GButton homeBtn;
		private GButton resumeBtn;
		private GButton exitBtn;

		private ButtonPanel() {
			setPreferredSize(new Dimension(200, 200));
			setBackground(null);
			
			homeBtn = new GButton(PATH, "btn_home", 120, 50);
			homeBtn.addActionListener(new ButtonActionListener());

			resumeBtn = new GButton(PATH, "btn_resume", 120, 50);
			resumeBtn.addActionListener(new ButtonActionListener());

			exitBtn = new GButton(PATH, "btn_exit", 120, 50);
			exitBtn.addActionListener(new ButtonActionListener());

			add(homeBtn);
			add(resumeBtn);
			add(exitBtn);
		}

		class ButtonActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				GButton btn = (GButton) e.getSource();
				
				switch (btn.getId()){
				case "btn_home" :
					mainFrame.drawHome();
					PauseFrame.this.dispose();
					break;
				case "btn_resume" :
					play.resumeGame();
					PauseFrame.this.dispose();
					break;
				case "btn_exit" :
					System.exit(0);
					break;
				}
				
			}

		}
	}
}
