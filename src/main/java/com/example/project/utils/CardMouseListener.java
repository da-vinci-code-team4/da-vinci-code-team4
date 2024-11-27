// src/main/java/com/example/project/utils/CardMouseListener.java
package com.example.project.utils;

import com.example.project.views.PlayGameWithPC;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CardMouseListener.java
 *
 * Lớp lắng nghe sự kiện chuột cho các thẻ bài.
 */
public class CardMouseListener extends MouseAdapter {
    private PlayGameWithPC parentFrame;
    private RoundedPanel card;
    private String imageName;

    public CardMouseListener(PlayGameWithPC parentFrame, RoundedPanel card, String imageName) {
        this.parentFrame = parentFrame;
        this.card = card;
        this.imageName = imageName;
    }
}