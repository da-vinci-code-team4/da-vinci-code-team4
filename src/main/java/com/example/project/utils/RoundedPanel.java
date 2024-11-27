// src/main/java/com/example/project/utils/RoundedPanel.java
package com.example.project.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// 둥근 모서리를 가진 사각형을 만들기 위한 맞춤형 JPanel 클래스
public class RoundedPanel extends JPanel {
    private int cornerRadius = 20;
    private BufferedImage backgroundImage;

    // 생성자 1: LayoutManager, Color, int
    public RoundedPanel(LayoutManager layout, Color bgColor, int radius) {
        super(layout);
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
    }

    // 생성자 2: Color, int
    public RoundedPanel(Color bgColor, int radius) {
        super();
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
    }

    // 생성자 3: LayoutManager, Color, int, String (imagePath)
    public RoundedPanel(LayoutManager layout, Color bgColor, int radius, String imagePath) {
        super(layout);
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
        setBackgroundImage(imagePath);
    }

    // 생성자 4: Color, int, String (imagePath)
    public RoundedPanel(Color bgColor, int radius, String imagePath) {
        super();
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
        setBackgroundImage(imagePath);
    }

    // 기본 생성자
    public RoundedPanel() {
        super();
        this.cornerRadius = 20; // 기본값
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    // 반경을 받는 생성자
    public RoundedPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    /**
     * 이미지 경로에서 RoundedPanel의 배경을 설정합니다.
     *
     * @param imagePath 이미지 경로 (src/main/resources/img/Card/ 내의 상대 경로)
     */
    public void setBackgroundImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/img/Card/" + imagePath)));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    /**
     * 배경을 변경합니다.
     *
     * @param imagePath 새로운 이미지 경로
     */
    public void changeBackgroundImage(String imagePath) {
        setBackgroundImage(imagePath);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 둥근 배경 그리기
        if (backgroundImage != null) {
            // 패널에 맞게 이미지 그리기
            graphics.drawImage(backgroundImage, 0, 0, width, height, this);
        } else {
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }
}
