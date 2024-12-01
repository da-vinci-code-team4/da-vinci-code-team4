package com.example.project.utils;

import com.example.project.audio.AudioManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AudioUtil {
    public static void addClickSound(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AudioManager.getInstance().playClickSound();
            }
        });
    }
}
