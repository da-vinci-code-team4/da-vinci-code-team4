package com.example.project.views;

import com.example.project.audio.AudioManager;
import com.example.project.utils.RoundedPanel;
import com.example.project.ui.CustomSliderUI;
import com.example.project.utils.AudioUtil;

import javax.swing.*;

import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.*;
import java.util.Properties;

public class AudioSettingPage extends JPanel {
    private static final String CONFIG_FILE = "audio_settings.properties";

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private int backgroundMusicVolume = 41;
    private int soundEffectsVolume = 41;

    private AudioManager audioManager;

    public AudioSettingPage(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.audioManager = AudioManager.getInstance();
        setLayout(null);
        setBackground(Color.WHITE);

        loadAudioSettings();

        adjustBackgroundMusicVolume(backgroundMusicVolume);
        adjustSoundEffectsVolume(soundEffectsVolume);

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        addAudioSettingHeader(background);

        // Add volume control sliders
        addVolumeControlGroup(background, 130, 305, "Background Music", "Background Music");
        addVolumeControlGroup(background, 130, 505, "Sound Effects", "Sound Effects");

        // Add back button
        addBackButton(background);
    }

    private void addAudioSettingHeader(JLabel background) {
        JLabel tabLabel = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/tab.png")));
        tabLabel.setBounds(503, 120, 505, 161);
        tabLabel.setLayout(null);
        background.add(tabLabel);

        JLabel titleLabel = new JLabel("Audio Setting", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 505, 161);
        tabLabel.add(titleLabel);
    }

    private void addVolumeControlGroup(JLabel background, int x, int y, String labelText, String groupType) {
        RoundedPanel groupPanel = new RoundedPanel(15);
        groupPanel.setBounds(x, y, 1200, 140);
        groupPanel.setBackground(new Color(0, 0, 0, 150));
        groupPanel.setLayout(null);
        background.add(groupPanel);
        int initialVolume = (groupType.equals("Background Music")) ? backgroundMusicVolume : soundEffectsVolume;
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, initialVolume);
        volumeSlider.setBounds(20, 55, 800, 50);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setUI(new CustomSliderUI(volumeSlider));
        groupPanel.add(volumeSlider);

        JLabel labelPanel = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/tab2.png")));
        labelPanel.setBounds(840, 30, 330, 100);
        labelPanel.setLayout(new BorderLayout());
        groupPanel.add(labelPanel);

        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        labelPanel.add(label, BorderLayout.CENTER);

        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            if (groupType.equals("Background Music")) {
                backgroundMusicVolume = volume;
                adjustBackgroundMusicVolume(volume);
                adjustGameBackgroundMusicVolume(volume);
            } else if (groupType.equals("Sound Effects")) {
                soundEffectsVolume = volume;
                adjustSoundEffectsVolume(volume);
            }
            saveAudioSettings(); // Save immediately
            System.out.println(labelText + " Volume: " + volume);
        });
        
        if (groupType.equals("Sound Effects")) {
            volumeSlider.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    audioManager.playClickSound();
                }
            });
        }
    }

    private void addBackButton(JLabel background) {
        JButton backButton = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("/img/ViewImage/back.png"))
                .getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        backButton.setBounds(1384, 30, 64, 64);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        AudioUtil.addClickSound(backButton);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);
    }

    private void adjustBackgroundMusicVolume(int volume) {
        float scaledVolume = scaleVolume(volume);
        audioManager.setBackgroundMusicVolume(volume);
    }

    private void adjustGameBackgroundMusicVolume(int volume) {
        float scaledVolume = scaleVolume(volume);
        audioManager.setGameBackgroundMusicVolume(volume);
    }
    
    private void adjustSoundEffectsVolume(int volume) {
        float scaledVolume = scaleVolume(volume);
        audioManager.setSoundEffectsVolume(volume);
    }

    private void saveAudioSettings() {
        // try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
        //     Properties props = new Properties();
        //     props.setProperty("backgroundMusicVolume", String.valueOf(backgroundMusicVolume));
        //     props.setProperty("soundEffectsVolume", String.valueOf(soundEffectsVolume));
        //     props.store(output, "Audio Settings");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    private void loadAudioSettings() {
        // try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
        //     Properties props = new Properties();
        //     props.load(input);
        //     backgroundMusicVolume = Integer.parseInt(props.getProperty("backgroundMusicVolume", "41"));
        //     soundEffectsVolume = Integer.parseInt(props.getProperty("soundEffectsVolume", "41"));
        // } catch (IOException e) {
        //     System.out.println("No settings file found. Using default values.");
        // }
    }

    private float scaleVolume(int volume) {
        return (volume == 0) ? -80f : -80f + (volume / 100f) * 86f;
    }


}
