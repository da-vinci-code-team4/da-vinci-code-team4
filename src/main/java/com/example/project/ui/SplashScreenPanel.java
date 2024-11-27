// src/main/java/com/example/project/ui/SplashScreenPanel.java
package com.example.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SplashScreenPanel extends JPanel {
    private BufferedImage image;
    private float opacity = 1.0f; // 투명도: 0.0 (보이지 않음)부터 1.0 (완전히 보임)

    public SplashScreenPanel(String imagePath) {
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is != null) {
                image = ImageIO.read(is);
            } else {
                System.err.println("스플래시 스크린 이미지를 찾을 수 없습니다: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOpaque(false); // 기본 배경을 그리지 않음
    }

    /**
     * 스플래시 스크린의 투명도를 설정합니다.
     *
     * @param opacity 투명도 값: 0.0부터 1.0까지
     */
    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            // 투명도 설정
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            // 패널 중앙에 이미지 그리기
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int x = (panelWidth - imgWidth) / 2;
            int y = (panelHeight - imgHeight) / 2;
            g2d.drawImage(image, x, y, this);
            g2d.dispose();
        }
    }
}
