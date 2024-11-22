package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Objects;


public class PlayPage extends JPanel {
    public PlayPage(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(null);
        setBackground(Color.GREEN);

        // Nút Back để quay lại MyPage
        JButton backButton = createButton("src/main/resources/img/back.png", "Back Button");
        backButton.setBounds( 1384, 30, 128, 86);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        add(backButton);

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon("src/main/resources/img/background.png"));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Thêm chữ "Play game with" với hiệu ứng phát sáng (thêm sau để nằm trên Rectangle)
        JLabel glowingLabel = createGlowingLabel("Play game with");
        glowingLabel.setBounds(262, 95, 1046, 166); // Vị trí và kích thước khớp với Rectangle
        background.add(glowingLabel); // Thêm chữ sau Rectangle

        // Tạo Rectangle bo góc
        JPanel rectanglePanel = new RoundedPanel(10); // Bo góc 10px
        rectanglePanel.setBounds(262, 95, 1046, 166); // Vị trí và kích thước của hình chữ nhật
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // Màu đen với độ trong suốt 71%
        background.add(rectanglePanel); // Thêm Rectangle trước

        // Tạo nhóm button dưới chữ
        int buttonWidth = 414;
        int buttonHeight = 508;
        int buttonSpacing = 20;

        // Vị trí căn giữa
        int totalWidth = 3 * buttonWidth + 2 * buttonSpacing; // Tổng chiều rộng của 3 nút và khoảng cách giữa chúng
        int startX = (1502 - totalWidth) / 2; // Tọa độ X bắt đầu để căn giữa
        int startY = 300; // Tọa độ Y của nhóm button

        // Button PC
        JButton pcButton = createButton("src/main/resources/img/PC.png", "PC Button Clicked");
        pcButton.setBounds(startX, startY, buttonWidth, buttonHeight);
        background.add(pcButton);

        // Button My Friend
        JButton myFriendButton = createButton("src/main/resources/img/MyFriend.png", "My Friend Button Clicked");
        myFriendButton.setBounds(startX + buttonWidth + buttonSpacing, startY, buttonWidth, buttonHeight);
        background.add(myFriendButton);

        // Button Random
        JButton randomButton = createButton("src/main/resources/img/Random.png", "Random Button Clicked");
        randomButton.setBounds(startX + 2 * (buttonWidth + buttonSpacing), startY, buttonWidth, buttonHeight);
        background.add(randomButton);

        setVisible(true);
    }

    private JButton createButton(String iconPath, String message) {
        JButton button = new JButton(new ImageIcon(iconPath));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false); // Ẩn focus khi click
        button.addActionListener(e -> System.out.println(message)); // Xử lý sự kiện
        return button;
    }

    private JLabel createGlowingLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 128));
        label.setForeground(Color.WHITE); // Màu chữ trắng
        label.setOpaque(false);
        return label;
    }
}

// Lớp tùy chỉnh JPanel để tạo Rectangle bo góc
class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 20;
    private BufferedImage backgroundImage;

    // Constructor 1: LayoutManager, Color, int
    public RoundedPanel(LayoutManager layout, Color bgColor, int radius) {
        super(layout);
        backgroundColor = bgColor;
        cornerRadius = radius;
        setOpaque(false);
    }

    // Constructor 2: Color, int
    public RoundedPanel(Color bgColor, int radius) {
        super();
        backgroundColor = bgColor;
        cornerRadius = radius;
        setOpaque(false);
    }

    // Constructor 3: LayoutManager, Color, int, String (imagePath)
    public RoundedPanel(LayoutManager layout, Color bgColor, int radius, String imagePath) {
        super(layout);
        backgroundColor = bgColor;
        cornerRadius = radius;
        setOpaque(false);
        setBackgroundImage(imagePath);
    }

    // Constructor 4: Color, int, String (imagePath)
    public RoundedPanel(Color bgColor, int radius, String imagePath) {
        super();
        backgroundColor = bgColor;
        cornerRadius = radius;
        setOpaque(false);
        setBackgroundImage(imagePath);
    }

    // **Constructor Mới Thêm: Không Có Tham Số**
    public RoundedPanel() {
        super();
        this.backgroundColor = new Color(0xD9D9D9); // Màu nền mặc định
        this.cornerRadius = 20; // Giá trị mặc định
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Cài đặt layout mặc định nếu cần
    }

    // **Constructor Mới Thêm: Nhận Chỉ Bán Kính Bo Góc**
    public RoundedPanel(int radius) {
        super();
        this.backgroundColor = new Color(0xD9D9D9); // Màu nền mặc định
        this.cornerRadius = radius;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Cài đặt layout mặc định nếu cần
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
        } else if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        } else {
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }

        // Không vẽ viền
        // graphics.setColor(getForeground());
        // graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }
}
