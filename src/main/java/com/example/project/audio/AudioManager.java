package com.example.project.audio;

public class AudioManager {
    private static AudioManager instance;
    private AudioPlayer backgroundMusicPlayer;
    private AudioPlayer gamebackgroundMusicPlayer;
    private AudioPlayer clickSoundPlayer;

    private AudioManager() {
        backgroundMusicPlayer = new AudioPlayer("audio/BackgroundMusic.wav", true);
        gamebackgroundMusicPlayer = new AudioPlayer("audio/_BackgroundMusic.wav", true);
        clickSoundPlayer = new AudioPlayer("audio/Click.wav");
        setBackgroundMusicVolume(41);
        setGameBackgroundMusicVolume(41);
        setSoundEffectsVolume(41);
    }

    public static synchronized AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playGameBackgroundMusic() {
        stopBackgroundMusic();
        gamebackgroundMusicPlayer.play();
    }

    public void stopGameBackgroundMusic() {
        gamebackgroundMusicPlayer.stop();
    }
    
    public void playBackgroundMusic() {
        stopGameBackgroundMusic();
        backgroundMusicPlayer.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusicPlayer.stop();
    }

    public void playClickSound() {
        clickSoundPlayer.play();
    }

    public void setBackgroundMusicVolume(int volume) {
        backgroundMusicPlayer.setVolume(scaleVolume(volume));
        System.out.println("Background music volume: " + backgroundMusicPlayer.getVolume());
    }

    public void setGameBackgroundMusicVolume(int volume) {
        gamebackgroundMusicPlayer.setVolume(scaleVolume(volume));
    }

    public void setSoundEffectsVolume(int volume) {
        clickSoundPlayer.setVolume(scaleVolume(volume));
    }

    public int getBackgroundMusicVolume() {
        return (int) ((backgroundMusicPlayer.getVolume() + 80) / 86 * 100);
    }

    public int getSoundEffectsVolume() {
        return (int) ((clickSoundPlayer.getVolume() + 80) / 86 * 100);
    }

    private float scaleVolume(int volume) {
        return (volume == 0) ? -80f : (float) (30 * Math.log10(volume / 100.0));
    }
}
