package com.example.project.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSliderUI extends BasicSliderUI {

    private final int THUMB_RADIUS = 15; // Kích thước nút trượt (nút tròn)
    private final Color TRACK_COLOR = new Color(200, 200, 200);
    private final Color THUMB_COLOR = new Color(50, 150, 250);
    private final Color BORDER_COLOR = new Color(30, 30, 30);

    public CustomSliderUI(JSlider slider) {
        super(slider);
        slider.setOpaque(false); // Không vẽ nền mặc định
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // Anti-aliasing cho hình tròn mượt mà
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ thanh trượt
        g2d.setColor(TRACK_COLOR);
        int trackY = trackRect.y + (trackRect.height / 2) - 5; // Điều chỉnh vị trí theo chiều dọc
        g2d.fillRoundRect(trackRect.x, trackY, trackRect.width, 10, 10, 10); // Thanh trượt to hơn

        g2d.dispose();
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // Anti-aliasing cho hình tròn mượt mà
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nút trượt
        g2d.setColor(THUMB_COLOR);
        int centerX = thumbRect.x + thumbRect.width / 2;
        int centerY = thumbRect.y + thumbRect.height / 2;
        g2d.fillOval(centerX - THUMB_RADIUS, centerY - THUMB_RADIUS, THUMB_RADIUS * 2, THUMB_RADIUS * 2);

        // Vẽ viền cho nút trượt
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