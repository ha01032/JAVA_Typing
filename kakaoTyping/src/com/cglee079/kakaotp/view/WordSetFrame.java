package com.cglee079.kakaotp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.cglee079.kakaotp.cswing.GButton;
import com.cglee079.kakaotp.cswing.GPanel;
import com.cglee079.kakaotp.dict.UserDictionary;
import com.cglee079.kakaotp.font.GameFontP;
import com.cglee079.kakaotp.io.UserIO;
import com.cglee079.kakaotp.model.User;
import com.cglee079.kakaotp.util.ColorManager;
import com.cglee079.kakaotp.util.MainPosition;
import com.cglee079.kakaotp.util.PathManager;

//AllWordList 프레임
public class WordSetFrame extends JFrame {
	private final String PATH = PathManager.WORDSET_FRAME;
	private WordListPanel wordListPanel;
	private UserListPanel userListPanel;
	private SetButtonPanel setButtonPanel;
	private SubmitButtonPanel submitButtonPanel;

	public WordSetFrame() {
		setLayout(null);
		setSize(600, 350);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);// 크기 고정
		setUndecorated(true);
		getContentPane().setBackground(ColorManager.BASIC2);
		setVisible(true);
		setShape(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 100, 100));
		setLocation(MainPosition.x - (this.getWidth()/2), MainPosition.y - (this.getHeight()/2));
		
		wordListPanel = new WordListPanel();
		wordListPanel.setLocation(30, 20);
		wordListPanel.setSize(250, 305);

		userListPanel = new UserListPanel(PATH, "bg_userlist", 250, 40);
		userListPanel.setLocation(300, 40);

		setButtonPanel = new SetButtonPanel();
		setButtonPanel.setLocation(300, 120);
		setButtonPanel.setSize(250, 150);

		submitButtonPanel = new SubmitButtonPanel();
		submitButtonPanel.setLocation(300, 270);
		submitButtonPanel.setSize(300, 100);

		add(wordListPanel);
		add(userListPanel);
		add(setButtonPanel);
		add(submitButtonPanel);

		updateUser();
	}

	public void updateUser(){
		JComboBox<String>  userComboBox = userListPanel.getUserComboBox();
		
		userComboBox.removeAllItems();
		userComboBox.addItem(null);
		
		UserIO userManager = UserIO.getInstance();
		ArrayList<User> users = userManager.readUser();
		
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
		String character= spliter[0];
		String username = spliter[1];
		
		
		return new User(username, character);
	}
	
	public void loadDictionary(String username) {
		DefaultTableModel model = wordListPanel.getWordListTable().getModel();
		String content[] = new String[3];
		
		int rowCnt = model.getRowCount();
		for (int i = 0 ; i < rowCnt; i++){
			model.removeRow(0);
		}
		
		UserDictionary dictionary = new UserDictionary(username);
		
		// WordList에 모든 단어 add
		for (int index = 0; index < dictionary.getNumOfWord(); index++) {
			content[0] = dictionary.getWord(index);
			content[1] = dictionary.render(content[0]);
			content[2] = dictionary.getSuccess(content[0]).toString();
			model.insertRow(0, content);
		}

	}
	
	class WordListPanel extends JPanel {
		private WordListTable wordListTable;

		private WordListPanel() {
			setBackground(null);
			JScrollPane scroll = new JScrollPane(wordListTable = new WordListTable());

			scroll.setPreferredSize(new Dimension(230, 300));
			scroll.setAlignmentX(CENTER_ALIGNMENT);

			add(scroll);
		}
		
		public WordListTable getWordListTable() {
			return wordListTable;
		}

		class WordListTable extends JTable {
			private DefaultTableModel model;

			WordListTable() {
				setFont(new GameFontP(13));
				
				// Table에 add할수 있도록
				String[] header = { "한글", "영어", "성공횟수" };
				model = new DefaultTableModel(header, 0);
				setModel(model);
			}

			// 더블클릭수정불가
			public boolean isCellEditable(int i, int c) {
				return false;
			}

			public DefaultTableModel getModel() {
				return model;
			}
			
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
			userComboBox.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					if (e.getItem() != null) {
						String userInfo = (String) e.getItem();
						String[] spliter = userInfo.split("\t");
						String username = spliter[1];

						loadDictionary(username);
					}
				}
			});
			
			add(userComboBox);
		}
		
		public JComboBox<String> getUserComboBox() {
			return userComboBox;
		}

	}

	class SetButtonPanel extends JPanel {
		private GButton wordPlusBtn;
		private GButton successResetBtn;

		private SetButtonPanel() {
			setBackground(null);
			
			wordPlusBtn = new GButton(PATH, "btn_wordplus", 100, 40);
			wordPlusBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new WordPlusFrame(WordSetFrame.this, getSelectedUser());
				}
			});
			successResetBtn = new GButton(PATH, "btn_successreset", 100, 40);
			successResetBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					User user = getSelectedUser();
					String username = user.getUsername();
					UserDictionary userDictionary = new UserDictionary(username);
					userDictionary.successReset();
					userDictionary.writeWordUserDictionary();
					
					loadDictionary(username);
					
					JOptionPane.showMessageDialog(WordSetFrame.this, "성공횟수가 초기화 되었습니다", "확인",JOptionPane.INFORMATION_MESSAGE);
				}
			});
			
			add(wordPlusBtn);
			add(successResetBtn);
		}
	}

	class SubmitButtonPanel extends JPanel {
		private GButton submitBtn;
		private GButton concealBtn;

		private SubmitButtonPanel() {
			setBackground(null);
			setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			
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
				if (btn.getId() == "btn_submit");
				else if (btn.getId() == "btn_cancel");

				WordSetFrame.this.dispose();
			}
		}
	}

}
