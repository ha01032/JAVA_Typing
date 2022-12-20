package com.cglee079.kakaotp.cswing;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.cglee079.kakaotp.font.GameFontB;
import com.cglee079.kakaotp.font.GameFontP;
import com.cglee079.kakaotp.util.PathManager;

public class FwLabel extends JLabel {
	private boolean haveItem	= false; 	// 라벨의 Item 보유
	private boolean valid 		= true; 	// 라벨의 유효성
	private boolean korean 		= true; 	// 라벨의 언어

	public FwLabel(String text) {
		super(text);
		setSize(410, 40);

		if (Math.random() < 0.2) { // Item랜덤하게 생성
			haveItem = true;
		}

		setKoreanFont(); // 한글 폰트
	}

	// Item 리턴
	public boolean isHaveItem() {
		return haveItem;
	}

	// 한글 폰트
	public void setKoreanFont() {
		if(haveItem){ 
			String path = PathManager.characterPath;
			this.setIcon(new ImageIcon(path + "icon_item" + ".png"));
			this.setFont(new GameFontB(12));
		} else {
			this.setFont(new GameFontP(13));
		}
	}

	// 영문 폰트
	public void setEnglishFont() {
		if(haveItem){ 
			String path = PathManager.characterPath;
			this.setIcon(new ImageIcon(path + "icon_item" + ".png"));
			this.setFont(new GameFontB(12));
		} else {
			this.setFont(new GameFontP(13));
		}
		
		this.setForeground(Color.blue);// 영단어 입력시 글자색 변경
	}

	// 유효성 리턴
	public boolean isValid() {
		return valid;
	}

	// 유효성 설정
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isKorean() {
		return korean;
	}

	public void setKorean(boolean language) {
		this.korean = language;
	}

}