package com.cglee079.kakaotp.model;

public class User {
	private String username;
	private String character;

	public User(String username, String character) {
		this.username = username;
		this.character = character;
		
	}
	
	public String getCharacter(){return character;}	
	public String getUsername(){return username;}
}
