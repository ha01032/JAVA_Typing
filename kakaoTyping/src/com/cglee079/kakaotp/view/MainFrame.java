package com.cglee079.kakaotp.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.cglee079.kakaotp.cswing.GButton;
import com.cglee079.kakaotp.sound.SoundPlayer;
import com.cglee079.kakaotp.util.MainPosition;
import com.cglee079.kakaotp.util.PathManager;

public class MainFrame extends JFrame{
	private final String PATH = PathManager.MAIN_FRAME;
	private HomePanel homePanel;
	private Point mouseClickedLocation = new Point(0, 0);
	
	public MainFrame(){
		setSize(800,550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);//크기 고정
		setUndecorated(true);
		setVisible(true);		
		setShape(new RoundRectangle2D.Float(0,0,this.getWidth(),this.getHeight(),30,30));
		
		Dimension frameSize = getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();		
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		
		MainPosition.x = (MainFrame.this.getWidth()/2) + (this.getLocationOnScreen().x -  MainFrame.this.mouseClickedLocation.x);
		MainPosition.y = (MainFrame.this.getHeight()/2) + (this.getLocationOnScreen().y -  MainFrame.this.mouseClickedLocation.y);
		
		this.addMouseListener(new MouseAdapter() {
			  public void mousePressed(MouseEvent e) {
			        MainFrame.this.mouseClickedLocation.x = e.getX();
			        MainFrame.this.mouseClickedLocation.y = e.getY();
			    }
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
		    public void mouseDragged(MouseEvent e) {
		    	MainFrame.this.setLocation(e.getLocationOnScreen().x -  MainFrame.this.mouseClickedLocation.x,
		    			 e.getLocationOnScreen().y -  MainFrame.this.mouseClickedLocation.y);
		    	
		        MainPosition.x = (MainFrame.this.getWidth()/2) + (e.getLocationOnScreen().x -  MainFrame.this.mouseClickedLocation.x);
		        MainPosition.y = (MainFrame.this.getHeight()/2) + (e.getLocationOnScreen().y -  MainFrame.this.mouseClickedLocation.y);
		    }
		});
		//sound-off 1
//		Thread bgmTh = new Thread(){                     
//			public void run(){
//				while(true){
//					SoundPlayer.play("bgm.wav");
//					try{
//						sleep(1000*130);
//					}catch(InterruptedException e){
//						return;
//					}
//				}
//			}
//		};
//		bgmTh.start();
		
		drawHome();
		createMenuBar();
		revalidate();   //revalidate 1
	}
	
	public void drawHome(){
		homePanel = new HomePanel();
		this.setContentPane(homePanel);
		this.revalidate();
	}

	private void createMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem exit = new JMenuItem("exit");
		JMenuItem version = new JMenuItem("Version");
		JMenuItem developer = new JMenuItem("Developer");

		menuBar.setPreferredSize(new Dimension(800, 30));
		
		// 파일 메뉴 생성	
		fileMenu.add(exit);

		// 파일 메뉴 단축키 설정
		exit.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));

		// add Listener
		version.addActionListener(new MenuActionListener());
		developer.addActionListener(new MenuActionListener());
		exit.addActionListener(new MenuActionListener());
		
		// 도움 메뉴 생성
		helpMenu.add(version);
		helpMenu.add(developer);

		// 메뉴를 메뉴바에 등록
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		// 메뉴바 추가
		setJMenuBar(menuBar);
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			switch(cmd){
			case "Version":
				JOptionPane.showMessageDialog(null, "version. 1.00\n2016.06.16", "Version",JOptionPane.INFORMATION_MESSAGE);
				break;				
			case "Developer" :
				JOptionPane.showMessageDialog(null, "Hansung.Univ\nComputer Engneering\n\nLee Changoo / Seo Songi","Developer", JOptionPane.INFORMATION_MESSAGE);
				break;				
			case "exit":
				System.exit(0);
				break;
			}
		}
	}
	
	class HomePanel extends JPanel{
		
		private HomePanel(){
			setLayout(null);
			
			GButton btn[] = new GButton[4];
			btn[0] = new GButton(PATH, "btn_start", 100, 35);
			btn[1] = new GButton(PATH, "btn_wordset", 100, 35);
			btn[2] = new GButton(PATH, "btn_help", 100, 35);
			btn[3] = new GButton(PATH, "btn_exit", 100, 35);
			
			for(int i = 0; i < 4; i++){
				btn[i].addActionListener(new MenuActionListener());
			}
			
			for(int i = 0; i < 4; i++){
				btn[i].setLocation(330, 280+(i*40));
				add(btn[i]);
			}		
		}
		
		class MenuActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				GButton btn = (GButton)e.getSource();
				
				switch (btn.getId()){
				case "btn_start":
					 new StartFrame(MainFrame.this);
					break;
				case "btn_wordset":
					new WordSetFrame(); 	
					break;
				case "btn_help":
					new HelpFrame(); 
					break;
				case "btn_exit":
					System.exit(0);
					break;
				}
			}
		}
				
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon homeBg = new ImageIcon(PATH + "bg_home.png");	
			g.drawImage(homeBg.getImage(), 0, 0, null);	
			setOpaque(false);
		}	
	}		
	
	public static void main(String[] args){
		/*System.setProperty("file.encoding","UTF-8");
		Field charset;
		try {
			charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null,null);
			
		} catch (NoSuchFieldException | SecurityException |IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		new MainFrame();
	}
}
