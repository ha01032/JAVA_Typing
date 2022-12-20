package com.cglee079.kakaotp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.cglee079.kakaotp.cswing.GButton;
import com.cglee079.kakaotp.cswing.GPanel;
import com.cglee079.kakaotp.dict.UserDictionary;
import com.cglee079.kakaotp.font.GameFontP;
import com.cglee079.kakaotp.model.User;
import com.cglee079.kakaotp.util.ColorManager;
import com.cglee079.kakaotp.util.MainPosition;
import com.cglee079.kakaotp.util.PathManager;

public class WordPlusFrame extends JFrame {
	private final String PATH = PathManager.WORDPLUS_FRAME;
	private InputPanel inputPanel;
	private WordSetFrame wordSetFrame;
	private User user;
	private JTextArea koreanInput;
	private JTextArea englishInput;

	public WordPlusFrame(WordSetFrame wordListTable, User user) {
		this.user = user;
		this.wordSetFrame = wordListTable;

		setSize(800, 350);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
		setShape(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 100, 100));
		setLocation(MainPosition.x - (this.getWidth() / 2), MainPosition.y - (this.getHeight() / 2));
		getContentPane().setBackground(ColorManager.BASIC3);

		GPanel inputWordPanel = new GPanel(PATH, "bg_inputwordpanel", 400, 70);
		add(inputWordPanel, BorderLayout.NORTH);

		inputPanel = new InputPanel();
		inputPanel.setPreferredSize(new Dimension(800, 210));
		add(inputPanel, BorderLayout.CENTER);

		SubmitPanel submitPanel = new SubmitPanel();
		submitPanel.setPreferredSize(new Dimension(400, 70));
		add(submitPanel, BorderLayout.SOUTH);
		revalidate();   //revalidate 5

	}

	class InputPanel extends JPanel {
		InputPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
			setBackground(null);

			koreanInput = new JTextArea();
			koreanInput.setPreferredSize(new Dimension(130, 200));
			koreanInput.setText("keyword");
			koreanInput.setFont(new GameFontP(15));

			englishInput = new JTextArea();
			englishInput.setPreferredSize(new Dimension(600, 200));
			englishInput.setText("code");
			englishInput.setFont(new GameFontP(15));

			add(koreanInput);
			add(englishInput);

		}
	}

	class SubmitPanel extends JPanel {
		private GButton submitBtn;
		private GButton concealBtn;

		private SubmitPanel() {
			setBackground(null);

			submitBtn = new GButton(PATH, "btn_submit", 100, 35);
			submitBtn.addActionListener(new SubmitAction());

			concealBtn = new GButton(PATH, "btn_cancel", 100, 35);
			concealBtn.addActionListener(new SubmitAction());

			add(submitBtn);
			add(concealBtn);
			
		}

		class SubmitAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				GButton btn = (GButton) e.getSource();
				
				switch(btn.getId()) {
				case "btn_submit" :
					String korean[] = splite(koreanInput.getText());
					String english[] = splite(englishInput.getText());

					

					String username = user.getUsername();
					UserDictionary userDictionary = new UserDictionary(username);
					for (int i = 0; i < korean.length; i++){
						userDictionary.add(korean[i], english[i], 0);
					}

					userDictionary.writeWordUserDictionary();
					
					wordSetFrame.loadDictionary(username);

					JOptionPane.showMessageDialog(WordPlusFrame.this, "단어가 추가되었습니다", "확인", JOptionPane.INFORMATION_MESSAGE);
					break;
					
				case "btn_cancel":
					break;
				}

				WordPlusFrame.this.dispose();
			}

			public String[] splite(String text) {
				String[] spliter;
				spliter = text.split("\n");
				return spliter;
			}
		}

	}
}
