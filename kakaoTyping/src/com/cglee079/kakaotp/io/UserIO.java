package com.cglee079.kakaotp.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.cglee079.kakaotp.model.User;

public class UserIO {
	private static UserIO instance = null;
	
	public static UserIO getInstance(){
		if(instance == null){
			instance = new UserIO();
		}
		return instance;
	}
	
	private UserIO(){
		
	}
	
	public synchronized ArrayList<User> readUser(){
		ArrayList<User> userList = new ArrayList<User>();
		BufferedReader in = null;
		String line = "";
		String[] spliter;
		String character;
		String username;
		
		try {
			in = new BufferedReader(new FileReader("resources/User.txt"));
			while ((line = in.readLine()) != null) {
				spliter = line.split("\t");
				character = spliter[0];
				username = spliter[1];
				userList.add(new User(username, character));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	public synchronized void writeUser(String chracter, String username) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter("resources/User.txt", true));
			out.write(chracter + "\t" + username);
			out.newLine();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
