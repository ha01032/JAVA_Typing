package com.cglee079.kakaotp.model;

public class Score {
	private String character;
	private String username;
	private Integer point;

	public Score(String character, String name, Integer point) {
		this.character = character;
		this.username = name;
		this.point = point;
	}

	public String getCharacter() {// 캐릭터 타입 리턴
		return character;
	}

	public String getUsername() {// 이름 리턴
		return username;
	}

	public Integer getPoint() {// 점수 리턴
		return point;
	}

}
