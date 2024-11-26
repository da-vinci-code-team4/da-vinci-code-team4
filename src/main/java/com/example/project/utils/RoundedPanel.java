package com.example.project.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// 모서리가 둥근 사각형을 생성하기 위한 사용자 정의 JPanel 클래스
public class RoundedPanel extends JPanel {
    private int cornerRadius = 20;
    private BufferedImage backgroundImage;
    private BufferedImage[] backgroundImages = new BufferedImage[26];

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
        this.cornerRadius = 20; // 기본 값
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    // 모서리 반경을 받는 생성자
    public RoundedPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    /**
     * 이미지 경로에서 RoundedPanel의 배경 이미지를 설정합니다.
     *
     * @param imagePath 이미지 경로 (src/main/resources/img/Card/ 내 상대 경로)
     */
    public void setBackgroundImage(String imagePath) {
        try {
            URL resource = getClass().getResource("/img/Card/" + imagePath+".png");
            if (resource == null) {
                throw new IllegalArgumentException("이미지 파일을 찾을 수 없습니다: " + resource);
            }
            backgroundImage = ImageIO.read(resource);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    /**
     * 이미지 경로에서 RoundedPanel의 배경 이미지를 설정합니다.
     *
     * @param imagePath[] 이미지 경로 (src/main/resources/img/Card/ 내 상대 경로)
     */
    public void setBackgroundImage(String[] imagePath) {
        try {
            for (int i = 0; i < imagePath.length; i++) {
                if (imagePath[i] == null) {
                    backgroundImages[i] = null;
                    System.out.println("Image not found: " + i);
                    break;
                }
                System.out.println(i+imagePath[i]);
                backgroundImages[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/img/Card/" + imagePath[i] + ".png")));
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            backgroundImages = null;
        }
    }

    /**
     * 배경 이미지를 변경합니다.
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

        if (backgroundImage != null) {
            // 이미지를 패널 크기에 맞게 그립니다.
            graphics.drawImage(backgroundImage, 0, 0, width, height, this);
        } else {
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        }
    }
}