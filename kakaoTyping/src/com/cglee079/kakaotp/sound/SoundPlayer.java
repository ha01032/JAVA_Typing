package com.cglee079.kakaotp.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.cglee079.kakaotp.util.PathManager;

public class SoundPlayer {
	public synchronized static void play(String fileName){
		String path = PathManager.SOUND + fileName;
		File soundFile = new File(path);
		
		AudioInputStream ais = null;
		Line.Info linfo = new Line.Info(Clip.class);
		Line line;
		Clip clip;
		
		
		try {
			line = AudioSystem.getLine(linfo);
			clip = (Clip) line;
			
			ais = AudioSystem.getAudioInputStream(soundFile);		
			clip.open(ais);
			clip.start();
			Thread.sleep(10);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException | InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
