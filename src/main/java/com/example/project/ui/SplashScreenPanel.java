package com.example.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SplashScreenPanel extends JPanel {
    private BufferedImage image;
    private float opacity = 1.0f; // Độ trong suốt từ 0.0 (invisible) đến 1.0 (fully visible)

    public SplashScreenPanel(String imagePath) {
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is != null) {
                image = ImageIO.read(is);
            } else {
                System.err.println("Không tìm thấy hình ảnh cho SplashScreen: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOpaque(false); // Không vẽ nền mặc định
    }

    /**
     * Thiết lập độ trong suốt của splash screen.
     *
     * @param opacity Giá trị độ trong suốt từ 0.0 đến 1.0
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
            // Thiết lập độ trong suốt
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            // Vẽ hình ảnh ở trung tâm panel
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