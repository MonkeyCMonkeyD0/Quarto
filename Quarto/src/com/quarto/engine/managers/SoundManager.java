package com.quarto.engine.managers;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
	
	private static HashMap<String, Clip> sounds = new HashMap<String, Clip>();
	private static boolean muted = Boolean.parseBoolean(DataManager.getDataFile("options").get("SOUND"));
	private static boolean musicMuted = Boolean.parseBoolean(DataManager.getDataFile("options").get("MUSIC"));
	private static String backgroundMusic;
	
	private static void onLoad(String path) {
		try {
			Clip clip = AudioSystem.getClip();
		    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("res/sound/" + path));
		    clip.open(inputStream);
		    sounds.put(path, clip);
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	}

	public static void onPlay(String path) {
		if(!isMuted()) {
			new Thread(new Runnable() {
			    public void run() {
			    	if(!sounds.containsKey(path))
			    		onLoad(path);
			    	sounds.get(path).setMicrosecondPosition(0);
			        sounds.get(path).start();
			    }
			}).start();
		}
	}

	public static void onPlayBackgroundMusic(String path) {
		if(!isMusicMuted()) {
			new Thread(new Runnable() {
			    public void run() {
			    	if(!sounds.containsKey(path))
			    		onLoad(path);
			    	sounds.get(path).setMicrosecondPosition(0);
			        sounds.get(path).start();
			    }
			}).start();
		}
	}
	
	public static void onStop(String path) {
    	if(sounds.containsKey(path))
    		sounds.get(path).stop();
	}

	public static boolean isMuted() {
		return muted;
	}

	public static boolean isMusicMuted() {
		return musicMuted;
	}

	public static void setMuted(boolean muted) {
		SoundManager.muted = muted;
		DataManager.getDataFile("options").add("SOUND", muted);
	}

	public static void setMusicMuted(boolean musicMuted) {
		SoundManager.musicMuted = musicMuted;
		DataManager.getDataFile("options").add("MUSIC", musicMuted);
		if(backgroundMusic != null)
			if(musicMuted)
				onStop(backgroundMusic);
			else
				onPlayBackgroundMusic(backgroundMusic);
	}

	public static String getBackgroundMusic() {
		return backgroundMusic;
	}

	public static void setBackgroundMusic(String backgroundMusic) {
		SoundManager.backgroundMusic = backgroundMusic;
		onPlayBackgroundMusic(backgroundMusic);
	}
	
}
