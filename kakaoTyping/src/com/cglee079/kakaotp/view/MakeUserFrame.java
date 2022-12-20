package com.cglee079.kakaotp.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cglee079.kakaotp.cswing.GButton;
import com.cglee079.kakaotp.cswing.GRadioButton;
import com.cglee079.kakaotp.io.UserIO;
import com.cglee079.kakaotp.util.ColorManager;
import com.cglee079.kakaotp.util.MainPosition;
import com.cglee079.kakaotp.util.PathManager;

public class MakeUserFrame extends JFrame {
	private final String PATH = PathManager.MAKEUSER_FRAME;
	
	private StartFrame startFrame;
	private CharacterChoicePanel characterChoicePanel;
	private UserInputPanel userInputPanel;
	private SubmitPanel submitPanel;

	public MakeUserFrame() {
		setSize(400, 320);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);// 크기 고정
		setUndecorated(true);
		this.getContentPane().setBackground(ColorManager.BASIC2);
		setVisible(true);

		setLocation(MainPosition.x - (this.getWidth()/2), MainPosition.y - (this.getHeight()/2));

		this.setShape(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 100, 100));
		setLayout(null);

		characterChoicePanel = new CharacterChoicePanel();
		characterChoicePanel.setLocation(0, 50);
		characterChoicePanel.setSize(400, 150);

		userInputPanel = new UserInputPanel();
		userInputPanel.setLocation(0, 200);
		userInputPanel.setSize(400, 50);

		submitPanel = new SubmitPanel();
		submitPanel.setLocation(0, 250);
		submitPanel.setSize(400, 50);

		add(characterChoicePanel);
		add(userInputPanel);
		add(submitPanel);
		revalidate();   //revalidate 2
	}
	
	public MakeUserFrame(StartFrame startFrame){
		this();
		this.startFrame = startFrame;
	}

	public String getSelectedCharater(){
		ButtonGroup chracterBtnGroup = characterChoicePanel.getChracterBtnGroup();
		String charaterID = null;
		
		Enumeration<AbstractButton> enums = chracterBtnGroup.getElements();
		while (enums.hasMoreElements()) {
			GRadioButton radiobtn = (GRadioButton) enums.nextElement();
			if (radiobtn.isSelected()){
				charaterID = radiobtn.getId();
			}
		}
		
		return charaterID;
	}
	
	public String getInsertedUsername(){
		return userInputPanel.getUsernameTextField().getText();
	}

	class CharacterChoicePanel extends JPanel {
		private GRadioButton[] choiceBtn;
		private ButtonGroup chracterBtnGroup;

		private CharacterChoicePanel() {
			setSize(400, 250);
			setBackground(null);

			chracterBtnGroup = new ButtonGroup();

			choiceBtn = new GRadioButton[3];
			choiceBtn[0] = new GRadioButton(PATH, "btn_muzi", 100, 100);
			choiceBtn[1] = new GRadioButton(PATH, "btn_lyan", 100, 100);
			choiceBtn[2] = new GRadioButton(PATH, "btn_apeach", 100, 100);

			for (int i = 0; i < 3; i++) {
				chracterBtnGroup.add(choiceBtn[i]);
				add(choiceBtn[i]);
			}
		}
		
		public ButtonGroup getChracterBtnGroup() {
			return chracterBtnGroup;
		}
	}

	class UserInputPanel extends JPanel {
		private JTextField usernameTextField;

		private UserInputPanel() {
			setLayout(new FlowLayout());
			setBackground(null);
			usernameTextField = new JTextField("", 15);
			add(usernameTextField);
		}

		public JTextField getUsernameTextField() {
			return usernameTextField;
		}
		
	}
	
	class SubmitPanel extends JPanel {
		private GButton[] submitBtn;

		private SubmitPanel() {
			setLayout(null);
			setBackground(null);
			makeBtn();

			for (int i = 0; i < 2; i++) {
				submitBtn[i].setLocation(90 + (i * 120), 0);
				add(submitBtn[i]);
			}
		}

		private void makeBtn() {
			submitBtn = new GButton[2];
			submitBtn[0] = new GButton(PATH, "btn_submit", 100, 35);
			submitBtn[1] = new GButton(PATH, "btn_cancel", 100, 35);

			for (int i = 0; i < 2; i++) {
				submitBtn[i].addActionListener(new SubmitActionListener());
			}
		}

		class SubmitActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String character = null;
				
				GButton btn = (GButton) e.getSource();
				if (btn.getId().equals("btn_submit")) {

					String characterID = null;
					characterID = getSelectedCharater();
					if (characterID == null) {
						JOptionPane.showMessageDialog(null, "캐릭터를 선택해주세요", "경고!", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if (characterID.equals("btn_muzi")){
						character = "MUZI";
					} else if (characterID.equals("btn_lyan")){
						character = "LYAN";
					} else if (characterID.equals("btn_apeach")){
						character = "APEACH";
					}

					String username = getInsertedUsername();
					if (username.equals("")) {
						JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "경고!", JOptionPane.WARNING_MESSAGE);
						return;
					}

					UserIO userManager = UserIO.getInstance();
					userManager.writeUser(character, username);					
					startFrame.updateUser();
				}

				MakeUserFrame.this.dispose();
			}
		}

	}

}
