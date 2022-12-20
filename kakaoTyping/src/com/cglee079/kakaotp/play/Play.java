package com.cglee079.kakaotp.play;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.cglee079.kakaotp.cswing.FwLabel;
import com.cglee079.kakaotp.dict.UserDictionary;
import com.cglee079.kakaotp.io.ScoreIO;
import com.cglee079.kakaotp.model.Score;
import com.cglee079.kakaotp.model.User;
import com.cglee079.kakaotp.view.PauseFrame;
import com.cglee079.kakaotp.view.PlayPanel;

public class Play {
	private final Item ITEM0 = new Item0();
	private final Item ITEM1 = new Item1();
	private final Item ITEM2 = new Item2();
	private final Item ITEM3 = new Item3();
	
	private PlayPanel playPanel;
	private User user;
	private UserDictionary dictionary;
	private boolean play;
	private boolean korean; // 입력 차례 (한글,영문)
	private int heart;
	private int level;
	private int point;
	private double speed;
	private int count;
	private boolean[] item;
	
	private Vector<FwLabel> fwLabels;
	private Vector<FWAni> fwAnis;
	private WordMaker wordMaker;
	private SpeedUpper speedUpper;

	public Play(User user, Integer level, Double speed) {
		this.user 		= user;
		this.heart		= 100;
		this.level 		= level;
		this.speed 		= speed;
		this.point 		= 0;
		this.play 	= true;
		this.korean 	= true; // 입력 차례 (한글,영문)
		this.count 		= 10;
		this.item		= new boolean[4];
		this.dictionary	= new UserDictionary(user.getUsername());
		this.fwLabels	= new Vector<FwLabel>();
		this.speedUpper = new SpeedUpper();
		this.fwAnis 	= new Vector<FWAni>();
		this.wordMaker	= new WordMaker(); // 단어 생성 시작
		
		Arrays.fill(item, false);
	}

	public PlayPanel getPlayPanel() {
		return playPanel;
	}

	public void setPlayPanel(PlayPanel playPanel) {
		this.playPanel = playPanel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserDictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(UserDictionary dictionary) {
		this.dictionary = dictionary;
	}

	public boolean isIsplay() {
		return play;
	}

	public void setIsplay(boolean isplay) {
		this.play = isplay;
	}

	public boolean iskorean() {
		return korean;
	}

	public void setkorean(boolean korean) {
		this.korean = korean;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean[] getItem() {
		return item;
	}

	public void setItem(boolean[] item) {
		this.item = item;
	}

	public Vector<FwLabel> getFwLabels() {
		return fwLabels;
	}

	public void setFwLabels(Vector<FwLabel> fwLabels) {
		this.fwLabels = fwLabels;
	}

	public Vector<FWAni> getFwAnis() {
		return fwAnis;
	}

	public void setFwAnis(Vector<FWAni> fwAnis) {
		this.fwAnis = fwAnis;
	}

	public WordMaker getWordMaker() {
		return wordMaker;
	}

	public void setWordMaker(WordMaker wordMaker) {
		this.wordMaker = wordMaker;
	}

	public SpeedUpper getSpeedUpper() {
		return speedUpper;
	}

	public void setSpeedUpper(SpeedUpper speedUpper) {
		this.speedUpper = speedUpper;
	}

	
	public void speedUp(double up) {
		this.speed += up;
	} // 속도 업

	public void levelUp() {
		level++;
		playPanel.drawLevel(level);
		clearFwLabels();
	}

	public void scoreUp() {// 점수 증가
		count--;
		point += level * 5;
		
		playPanel.drawPoint(point);
		
		if (count == 0) {
			levelUp();
			count = 10;
		}
	}
	
	public void pain(int value){
		this.heart -= value;
		playPanel.drawPain(heart);
		
		if(heart <= 0){
			gameOver();
		}
	}
	
	public void fullGain(){
		this.heart = 100;
		playPanel.drawGain(100);
	}

	public void useItem(int index){
		if(item[index]){
			switch(index){
			case 0: ITEM0.call(); break;
			case 1: ITEM1.call(); break;
			case 2: ITEM2.call(); break;
			case 3: ITEM3.call(); break;
			}
			
			item[index] = false;
			playPanel.drawItemBtn(index, false);
		}
	}
	
	public void clearFwLabels() { // 떨어지는 단어 모두 삭제
		for (int index = 0; index < fwLabels.size(); index++) {
			fwLabels.get(index).setVisible(false);
			fwLabels.get(index).setValid(false);
		}
		fwLabels.removeAllElements();
	}
	
	public void checkFwLabels(String text) {
		String renderWord = dictionary.render(text); // 번역글자 : 한글 -> 영어 -> null

		if (!this.korean && renderWord != null) {// 영어 입력차례에서, 한글을  입력한 경우
			return ;
		}

		int size = fwLabels.size();
		for (int index = 0; index < size; index++) { // 떨어지는  라벨들 중
			FwLabel fwLabel = fwLabels.get(index); // 떨어지는 라벨 
			String fallWord = fwLabel.getText(); // 떨어지는 라벨의 단어

			if (fallWord.equalsIgnoreCase(text)) { // 떨어지는 단어와 입력 단어가 같을경우
				if (fwLabel.isKorean()) { koreanMatched(fwLabel); } 
				else { englishMatched(fwLabel); }
				
				fwLabel.setText(renderWord); // 한글 -> 영어로, 영어-> null로

				break;
			}
		}
	}

	public void englishMatched(FwLabel fwLabel) {
		String english 	= fwLabel.getText();
		String korean 	= dictionary.renderReverse(english); // 영어의 한글 값 저장

		playPanel.addSuccessWord(korean, english); // 성공 단어에 추가
		dictionary.plusSuccess(korean); // 단어 성공 횟수 증가
		
		fwLabels.remove(fwLabel);// 배열에서 제거
		fwLabel.setVisible(false); //안보이게
		fwLabel.setValid(false); // 무효한 숫자로

		if (fwLabel.isHaveItem()) { // Item확인, 생성
			Random random = new Random();
			
			int num = random.nextInt(4);// 0-3 아이템 번호 제공
			int n = 0;
			while (item[num]) {
				num = random.nextInt(4);
				n++;
				if (n == 10) {
					break;
				}
			}

			item[num] = true;
			playPanel.drawItemBtn(num, true);
		}

		scoreUp();// 점수 흭득
		setkorean(true); // 한글 입력차례로 변환
	}

	public void koreanMatched(FwLabel fwLabel) {
		fwLabel.setKorean(false);
		fwLabel.setEnglishFont();

		setkorean(false); // 영어차례로 변환
	}
	
	public void startGame() {
		play = true;	
		speedUpper.start();
		wordMaker.start();
	}

	public void stopGame() {
		play = false;		
	}

	public void pauseGame(boolean pause) {
		if(pause){
			playPanel.pause();
		}
		speedUpper.suspend();
		wordMaker.suspend();
		for (int i = 0; i < fwAnis.size(); i++){
			fwAnis.get(i).suspend();
		}
	}

	public void resumeGame() {
		speedUpper.resume();
		wordMaker.resume();
		for (int i = 0; i < fwAnis.size(); i++){
			fwAnis.get(i).resume();
		}
	}
	
	public void gameOver() {
		stopGame();
		Score score = new Score(user.getCharacter(), user.getUsername(), point);
		ScoreIO scoreManager = ScoreIO.getInstance();
		scoreManager.addScore(score);
		playPanel.gameOver(score, level);
	}

	class SpeedUpper extends Thread {
		
		public void run() {
			NumberFormat formatter = new DecimalFormat("#0.00");    
			
			while (play) {
				speedUp(0.01);
				playPanel.drawSpeed(formatter.format(speed));
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	class WordMaker extends Thread {
		public void run() {
			String word = null;
			while (play) {
				word = dictionary.rand();
				FWAni fallingAni = new FWAni(word);
				fwAnis.add(fallingAni);
				fallingAni.start();

				try {
					sleep(9000);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	// 라벨 하나 하나 떨어지는 쓰레드
	class FWAni extends Thread {
		String word;
		
		public FWAni(String word){
			super();
			this.word = word;
		}
		
		public void run() {
			// 좌표값 설정
			int x = 50;
			int y = 50;

			// 단어를 랜덤하게 받아와 라벨 생성.
			FwLabel fwLabel = new FwLabel(word);
			fwLabel.setLocation(x, y); // 위치 설정
			fwLabels.add(fwLabel); // 떨어지는 라벨 추가
			playPanel.drawFwLabel(fwLabel);

			// y<410까지 떨어트림
			while (y < 410 && play) {
				y = (int) (y + speed);
				fwLabel.setLocation(x, y);
				try {
					sleep(1700); // 떨어지는 속도
				} catch (InterruptedException e) {
					return;
				}
			}

			if (y >= 410 && fwLabel.isValid() == true) {
				pain(20); // 체력 감소
				fwLabels.remove(fwLabel); // 배열에서 제거

				if (!fwLabel.isKorean()){ // 영어 라벨이 다 떨어지면, 한글 차례로
					korean = true;
				}
			}

			fwAnis.remove(this);

			return;
		}

	}// FallingAni 클래스 끝
	
	abstract class Item {
		public abstract void call(); // item 사용
	}

	class Item0 extends Item {
		public void call() {
			FwLabel fwLabel;
			String korean;
			String english;

			int size = fwLabels.size();
			
			for (int index = 0; index < size; index++) {
				fwLabel = fwLabels.get(index);
				korean 	= fwLabel.getText();
				english = dictionary.render(korean);

				if (!fwLabel.isKorean()){// 모든 떨어지는 라벨 중 하나의 라벨은 영어를 가지고있음
					english = korean;
					korean = dictionary.renderReverse(english);
					
					Play.this.korean = true;
				}

				playPanel.addSuccessWord(korean, english); // 성공 단어에 추가
			}

			// 모든 떨어지는 라벨 제거
			clearFwLabels();
		}
	}

	class Item1 extends Item {
		public void call() {
			pauseGame(false);

			Timer t = new Timer(false);
			// 5초후에 원래 속도로
			t.schedule(new TimerTask() {
				public void run() {
					resumeGame();
				}
			}, 5000);
		}
	}

	class Item2 extends Item {
		double curSpeed;
		
		public void call() {
			curSpeed = speed;
			speed = 3.0;
			
			Timer t = new Timer(false);

			// 5초후에 원래 속도로
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					speed = curSpeed;
				}
			}, 5000);

		}
	}

	class Item3 extends Item {
		public void call() {
			fullGain();
		}
	}
}
