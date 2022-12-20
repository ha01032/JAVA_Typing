package com.cglee079.kakaotp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.cglee079.kakaotp.cswing.GButton;
import com.cglee079.kakaotp.cswing.GPanel;
import com.cglee079.kakaotp.cswing.GRadioButton;
import com.cglee079.kakaotp.io.UserIO;
import com.cglee079.kakaotp.model.User;
import com.cglee079.kakaotp.play.Play;
import com.cglee079.kakaotp.util.ColorManager;
import com.cglee079.kakaotp.util.MainPosition;
import com.cglee079.kakaotp.util.PathManager;

public class StartFrame extends JFrame {
	private final String PATH = PathManager.START_FRAME;
	private MainFrame mainFrame;
	private UserListPanel userListPanel;
	private LevelListPanel levelListPanel;
	private SubmitPanel submitPanel;

	public StartFrame(){
		setSize(400, 250);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);// 크기 고정
		setUndecorated(true);
		setVisible(true);
		setContentPane(new StartPanel());
		getContentPane().setBackground(ColorManager.BASIC2);
		
		setShape(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 100, 100));
		setLocation(MainPosition.x - (this.getWidth()/2), MainPosition.y - (this.getHeight()/2));
	}
	
	public StartFrame(MainFrame mainFrame){
		this();
		this.mainFrame = mainFrame;
		
		updateUser();
	}
	
	public void updateUser(){
		JComboBox<String>  userComboBox = userListPanel.getUserComboBox();
		
		userComboBox.removeAllItems();
		userComboBox.addItem(null);
		
		UserIO userIO = UserIO.getInstance();
		ArrayList<User> users = userIO.readUser();
		
		User user = null;
		int size = users.size();
		for(int i = 0; i < size ; i++){
			user = users.get(i);
			userComboBox.addItem(user.getCharacter() + "\t" + user.getUsername());
		}
	}
	
	public User getSelectedUser() {
		JComboBox<String>  userComboBox = userListPanel.getUserComboBox();
		
		String str = (String) userComboBox.getSelectedItem();
		if(str == null){
			return null;
		}
		
		String[] spliter = str.split("\t");
		
		String character = spliter[0];
		String username = spliter[1];
		
		return new User(username, character);
	}
	
	public String getSelectedLevel(){
		ButtonGroup levelBtnGroup = levelListPanel.getLevelBtnGroup();
		GRadioButton radiobtn = null;
		String levelID = null;
		
		Enumeration<AbstractButton> enums = levelBtnGroup.getElements();
		while (enums.hasMoreElements()) {
			radiobtn = (GRadioButton) enums.nextElement();
			if (radiobtn.isSelected()) {
				levelID = radiobtn.getId();
			}
		}
		
		return levelID;
	}

	class StartPanel extends JPanel {
		private StartPanel(){
			setLayout(null);
			userListPanel = new UserListPanel(PATH, "bg_userlist", 300, 40);
			userListPanel.setLocation(40, 50);
			add(userListPanel);

			levelListPanel = new LevelListPanel();
			levelListPanel.setSize(400, 70);
			levelListPanel.setLocation(0, 110);

			submitPanel = new SubmitPanel(userListPanel, levelListPanel);
			submitPanel.setSize(400, 50);
			submitPanel.setLocation(50, 180);

			add(levelListPanel, BorderLayout.CENTER);
			add(submitPanel, BorderLayout.SOUTH);
		}
	}

	class LevelListPanel extends JPanel {
		private GRadioButton levelbtn[];
		private ButtonGroup levelBtnGroup;

		private LevelListPanel() {
			setBackground(null);
			
			levelBtnGroup = new ButtonGroup();
			levelbtn = new GRadioButton[3];
			levelbtn[0] = new GRadioButton(PATH, "btn_level1", 70, 35);
			levelbtn[1] = new GRadioButton(PATH, "btn_level2", 70, 35);
			levelbtn[2] = new GRadioButton(PATH, "btn_level3", 70, 35);

			for (int i = 0; i < 3; i++) {
				levelBtnGroup.add(levelbtn[i]);
				add(levelbtn[i]);
			}
			
		}

		public ButtonGroup getLevelBtnGroup() {
			return levelBtnGroup;
		}

	}

	class UserListPanel extends GPanel {
		private JComboBox<String> userComboBox;

		private UserListPanel(String path, String filename, int width, int height) {
			super(path, filename, width, height);
			setLayout(null);
			setBackground(null);
			
			userComboBox = new JComboBox<String>();
			userComboBox.setSize(150, 20);
			userComboBox.setLocation(70, 10);
			userComboBox.setBackground(Color.WHITE);
			userComboBox.setSelectedIndex(-1);
			userComboBox.setSelectedItem(-1);

			add(userComboBox);

			GButton wordPlusBtn = new GButton(PATH, "btn_userplus", 30, 30);
			wordPlusBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					new MakeUserFrame(StartFrame.this);
				}
			});
			
			wordPlusBtn.setLocation(240, 5);
			add(wordPlusBtn);
		}

		public JComboBox<String> getUserComboBox() {
			return userComboBox;
		}

	}
	
	class SubmitPanel extends JPanel {
		private UserListPanel userListPanel;
		private LevelListPanel levelListPanel;
		private GButton[] submitBtn;

		private SubmitPanel() {
			setLayout(null);
			setBackground(null);
			
			submitBtn = new GButton[2];
			submitBtn[0] = new GButton(PATH, "btn_submit", 100, 35);
			submitBtn[1] = new GButton(PATH, "btn_cancel", 100, 35);

			for (int i = 0; i < 2; i++) {
				submitBtn[i].addActionListener(new SubmitAction());
			}

			for (int i = 0; i < 2; i++) {
				submitBtn[i].setLocation(40 + (i * 120), 0);
				add(submitBtn[i]);
			}
		}
		
		private SubmitPanel(UserListPanel userListPanel, LevelListPanel levelListPanel){
			this();
			this.userListPanel = userListPanel;
			this.levelListPanel = levelListPanel;
		}

		class SubmitAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				GButton btn = (GButton) e.getSource();
				if (btn.getId() == "btn_submit") {
					
					User user = getSelectedUser();
					if (user == null) {
						JOptionPane.showMessageDialog(null, "유저를 선택해주세요", "경고!", JOptionPane.WARNING_MESSAGE);
						return;
					}
						
					String character = user.getCharacter();
					switch (character){
					case "MUZI":
						ColorManager.baseColor 		= ColorManager.MUZI;
						PathManager.characterPath 	= PathManager.MUZI;
						break;
					case "APEACH":
						ColorManager.baseColor 		= ColorManager.APEACH;
						PathManager.characterPath	= PathManager.APEACH;
						break;
					case "LYAN":
						ColorManager.baseColor 		= ColorManager.LYAN;
						PathManager.characterPath 	= PathManager.LYAN;
						break;
					}

					String levelID = getSelectedLevel();
					int level = 0;
					double speed = 0;
					
					if (levelID == null) {
						JOptionPane.showMessageDialog(null, "레벨을 선택해주세요", "경고!", JOptionPane.WARNING_MESSAGE);
						return;
					}

					switch (levelID){
					case "btn_level1":
						level = 1;
						speed = 5;
						break;
					case "btn_level2":
						level = 5;
						speed = 10;
						break;
					case "btn_level3":
						level = 10;
						speed = 15;
						break;
					}
					
					Play play = new Play(user, level, speed);
					mainFrame.setContentPane(new PlayPanel(mainFrame, play));
				}
				
				StartFrame.this.dispose();

			}
		}
	}

}
