package com.example.project.audio;

import javax.sound.sampled.*;
import java.io.InputStream;

public class AudioPlayer {
    private Clip clip;
    private FloatControl volumeControl;
    private boolean isLooping;

    public AudioPlayer(String audioFilePath, boolean loop) {
        this.isLooping = loop;
        try {
            InputStream audioStream = getClass().getClassLoader().getResourceAsStream(audioFilePath);
            if (audioStream == null) {
                throw new IllegalArgumentException("Audio file not found: " + audioFilePath);
            }

            AudioInputStream stream = AudioSystem.getAudioInputStream(audioStream);
            clip = AudioSystem.getClip();
            clip.open(stream);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }

            if (isLooping && clip != null) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AudioPlayer(String audioFilePath) {
        this(audioFilePath, false);
    }

    public void play() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(volume);
        }
    }

    public float getVolume() {
        return volumeControl != null ? volumeControl.getValue() : -80f;
    }
}
