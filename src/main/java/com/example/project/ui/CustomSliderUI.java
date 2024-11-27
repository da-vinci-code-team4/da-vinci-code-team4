// src/main/java/com/example/project/ui/CustomSliderUI.java
package com.example.project.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSliderUI extends BasicSliderUI {

    private final int THUMB_RADIUS = 15; // 슬라이더 버튼의 크기 (원형 버튼)
    private final Color TRACK_COLOR = new Color(200, 200, 200);
    private final Color THUMB_COLOR = new Color(50, 150, 250);
    private final Color BORDER_COLOR = new Color(30, 30, 30);

    public CustomSliderUI(JSlider slider) {
        super(slider);
        slider.setOpaque(false); // 기본 배경을 그리지 않음
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // 원형의 부드러운 Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 슬라이더 트랙 그리기
        g2d.setColor(TRACK_COLOR);
        int trackY = trackRect.y + (trackRect.height / 2) - 5; // 수직 위치 조정
        g2d.fillRoundRect(trackRect.x, trackY, trackRect.width, 10, 10, 10); // 트랙을 더 크게 그림

        g2d.dispose();
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // 원형의 부드러운 Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 슬라이더 버튼 그리기
        g2d.setColor(THUMB_COLOR);
        int centerX = thumbRect.x + thumbRect.width / 2;
        int centerY = thumbRect.y + thumbRect.height / 2;
        g2d.fillOval(centerX - THUMB_RADIUS, centerY - THUMB_RADIUS, THUMB_RADIUS * 2, THUMB_RADIUS * 2);

        // 슬라이더 버튼 테두리 그리기
        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(centerX - THUMB_RADIUS, centerY - THUMB_RADIUS, THUMB_RADIUS * 2, THUMB_RADIUS * 2);

        g2d.dispose();
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(THUMB_RADIUS * 2, THUMB_RADIUS * 2);
    }
}
