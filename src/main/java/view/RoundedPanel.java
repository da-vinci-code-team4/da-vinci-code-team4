package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// Lớp tùy chỉnh JPanel để tạo Rectangle bo góc
public class RoundedPanel extends JPanel {
    private int cornerRadius = 20;
    private BufferedImage backgroundImage;

    // Constructor 1: LayoutManager, Color, int
    public RoundedPanel(LayoutManager layout, Color bgColor, int radius) {
        super(layout);
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
    }

    // Constructor 2: Color, int
    public RoundedPanel(Color bgColor, int radius) {
        super();
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
    }

    // Constructor 3: LayoutManager, Color, int, String (imagePath)
    public RoundedPanel(LayoutManager layout, Color bgColor, int radius, String imagePath) {
        super(layout);
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
        setBackgroundImage(imagePath);
    }

    // Constructor 4: Color, int, String (imagePath)
    public RoundedPanel(Color bgColor, int radius, String imagePath) {
        super();
        cornerRadius = radius;
        setOpaque(false);
        setBackground(bgColor);
        setBackgroundImage(imagePath);
    }

    // Constructor Mặc định
    public RoundedPanel() {
        super();
        this.cornerRadius = 20; // Giá trị mặc định
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    // Constructor Nhận Bán Kính Bo Góc
    public RoundedPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    /**
     * Đặt hình nền cho RoundedPanel từ đường dẫn ảnh.
     *
     * @param imagePath Đường dẫn tới ảnh (relative path trong src/main/resources/img/Card/)
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
     * Thay đổi hình nền.
     *
     * @param imagePath Đường dẫn tới ảnh mới
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

        // Vẽ nền bo tròn
        if (backgroundImage != null) {
            // Vẽ hình ảnh sao cho vừa với panel
            graphics.drawImage(backgroundImage, 0, 0, width, height, this);
        } else {
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }
}
