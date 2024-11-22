package main.view;

import javax.swing.*;
import java.awt.*;

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
    private int cornerRadius;

    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Đảm bảo JPanel không che phủ toàn bộ
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // Vẽ hình chữ nhật bo góc
    }
}
