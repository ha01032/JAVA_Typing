package com.cglee079.kakaotp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cglee079.kakaotp.cswing.GButton;
import com.cglee079.kakaotp.cswing.GPanel;
import com.cglee079.kakaotp.font.GameFontB;
import com.cglee079.kakaotp.font.GameFontP;
import com.cglee079.kakaotp.io.ScoreIO;
import com.cglee079.kakaotp.model.Score;
import com.cglee079.kakaotp.util.ColorManager;
import com.cglee079.kakaotp.util.MainPosition;
import com.cglee079.kakaotp.util.PathManager;
import com.cglee079.kakaotp.view.PauseFrame.ButtonPanel.ButtonActionListener;

public class ScoreFrame extends JFrame {
	private final String PATH = PathManager.SCORE_FRAME;
	private Score score;
	private int level;
	private MainFrame mainFrame;  //*
	
	public ScoreFrame() {
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setBackground(ColorManager.BASIC);
		setLocation(MainPosition.x - (this.getWidth()/2), MainPosition.y - (this.getHeight()/2));
	}
	
	public ScoreFrame(Score score, int level, MainFrame mainFrame){ //*
		this();
		this.score = score;
		this.level = level;
		this.mainFrame = mainFrame; //*

		GPanel northPanel = new GPanel(PATH, "bg_northpanel", 800, 60);
		CenterPanel centerPanel = new CenterPanel();
		ButtonPanel buttonPanel = new ButtonPanel();
		
		add(centerPanel, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		revalidate();   //revalidate 4
	}
	
	
	
	
	//*******************************************
	class ButtonPanel extends JPanel {
		private GButton homeBtn;
		private GButton exitBtn;

		private ButtonPanel() {
			setPreferredSize(new Dimension(100, 55));
			setBackground(null);
			
			homeBtn = new GButton("resources/images/PauseFrame/", "btn_home", 120, 50);
			homeBtn.addActionListener(new ButtonActionListener());

			exitBtn = new GButton("resources/images/PauseFrame/", "btn_exit", 120, 50);
			exitBtn.addActionListener(new ButtonActionListener());

			add(homeBtn);
			add(exitBtn);
		}

		class ButtonActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				GButton btn = (GButton) e.getSource();
				
				switch (btn.getId()){
				case "btn_home" :
					mainFrame.drawHome();
					ScoreFrame.this.dispose();
					break;
					
				case "btn_exit" :
					System.exit(0);
					break;
				}
				
			}

		}
	}
	
	//**********************************************************
	

	
	
	
	class CenterPanel extends JPanel {
		private GradePanel gradePanel;
		private MyGradePanel myGradePanel;
		
		private CenterPanel() {
			setVisible(true);
			setBackground(ColorManager.baseColor);
			setLayout(null);
			setPreferredSize(new Dimension(500, 420));

			gradePanel = new GradePanel(PATH, "frame", 390, 410);
			myGradePanel = new MyGradePanel(PATH, "bg_mygradepanel", 320, 290);
			
			add(gradePanel);
			add(myGradePanel);

			String myCharacter = score.getCharacter(); // 캐릭터 이미지
			ImageIcon myCharacterImageIcon = new ImageIcon(PATH + "icon_" + myCharacter + "_score.gif");
			JLabel myCharacterLabel = new JLabel(myCharacterImageIcon);
			myCharacterLabel.setLocation(630, 300);
			myCharacterLabel.setSize(130, 130);

			add(myCharacterLabel);
		}

		class GradePanel extends GPanel {
			private final int NUM_OF_THROPY = 4;// 전체 화면에 표시할 등수 표시 갯수

			private GradePanel(String path, String filename, int width, int height) {
				super(path, filename, width, height);

				setVisible(true);
				setBackground(Color.white);
				setLayout(null);
				setLocation(30, 30);
				
				drawGrade();
			}

			private void drawGrade() {
				ScoreIO scoreManager = ScoreIO.getInstance();
				ArrayList<Score> scores = scoreManager.getScores();
				Score score = null;
				
				ImageIcon[] images	 = new ImageIcon[NUM_OF_THROPY];
				ImageIcon[] gradeImg = new ImageIcon[NUM_OF_THROPY];

				JLabel[] faceLabel 	= new JLabel[NUM_OF_THROPY];
				JLabel[] scoreLabel = new JLabel[NUM_OF_THROPY];
				JLabel[] gradeLabel = new JLabel[NUM_OF_THROPY];
				JLabel[] nameLabel 	= new JLabel[NUM_OF_THROPY];

				String name = "";// 이름 저장
				String character = "";// 캐릭터 타입 저장

				for (int i = 0; i < NUM_OF_THROPY; i++) {
					score = scores.get(i);
					
					gradeImg[i] = new ImageIcon(PATH + "icon_trophy.png");
					gradeLabel[i] = new JLabel(gradeImg[i]);

					name = score.getUsername();

					character = score.getCharacter();
					images[i] = new ImageIcon(PATH + "icon_" + character + "_face.png");// faceType 별로 이모티콘  분류
					faceLabel[i] = new JLabel(images[i]);

					scoreLabel[i] = new JLabel(score.getPoint().toString());
					scoreLabel[i].setSize(100, 100);
					scoreLabel[i].setLocation(300, i * 100);
					scoreLabel[i].setFont(new GameFontB(15));

					faceLabel[i].setSize(100, 100);
					faceLabel[i].setLocation(10, i * 100);

					gradeLabel[i].setSize(100, 100);
					gradeLabel[i].setLocation(85, i * 100);

					nameLabel[i] = new JLabel(name);
					nameLabel[i].setSize(100, 100);
					nameLabel[i].setLocation(180, i * 100);
					nameLabel[i].setFont(new GameFontB(15));

					add(faceLabel[i]);
					add(gradeLabel[i]);
					add(scoreLabel[i]);
					add(nameLabel[i]);
				}
			}
		}

		class MyGradePanel extends GPanel {
			private MyGradePanel(String path, String filename, int width, int height) {
				super(path, filename, width, height);

				setVisible(true);
				setBackground(Color.white);
				setLayout(new GridLayout(3, 1));
				setLocation(450, 30);
				
				String myName 	= score.getUsername();
				int myScore 	= score.getPoint();
				int myLevel 	= level;

				JLabel myScoreLabel = new JLabel(myScore + "");
				setGradeLabel(myScoreLabel, 250, 60, 50);

				JLabel myNameLabel = new JLabel("NAME : " + myName);
				setGradeLabel(myNameLabel, 150, 50, 20);

				JLabel myLevelLabel = new JLabel("LEVEL : " + myLevel);
				setGradeLabel(myLevelLabel, 100, 50, 20);
				
			}

			private void setGradeLabel(JLabel source, int x, int y, int fontSize) {
				source.setSize(x, y);
				source.setForeground(ColorManager.baseColor);
				source.setHorizontalAlignment(JLabel.CENTER);
				source.setVerticalAlignment(JLabel.CENTER);
				source.setFont(new GameFontP(fontSize));
				add(source);
			}
		}// MyGradePanel end

	}

}
