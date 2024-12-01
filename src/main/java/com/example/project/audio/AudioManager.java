package com.example.project.audio;

public class AudioManager {
    private static AudioManager instance;
    private AudioPlayer backgroundMusicPlayer;
    private AudioPlayer clickSoundPlayer;

    private AudioManager() {
        backgroundMusicPlayer = new AudioPlayer("audio/BackgroundMusic.wav", true);
        clickSoundPlayer = new AudioPlayer("audio/Click.wav");
    }

    public static synchronized AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playBackgroundMusic() {
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
        return -80f + (volume / 100f) * 86f;
    }
}
