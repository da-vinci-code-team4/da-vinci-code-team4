package com.example.project.views;

import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class PlayPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public PlayPage(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Nút Back để quay lại MyPage
        JButton backButton = createButton("/img/ViewImage/back.png", "Back Button");
        backButton.setBounds(1384, 30, 128, 86);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);

        // Tạo chữ "Play game with"
        JLabel glowingLabel = createGlowingLabel("Play game with");
        glowingLabel.setBounds(262, 95, 1046, 166);
        background.add(glowingLabel);

        // Tạo Rectangle bo góc
        RoundedPanel rectanglePanel = new RoundedPanel(10);
        rectanglePanel.setBounds(262, 95, 1046, 166);
        rectanglePanel.setBackground(new Color(0, 0, 0, 180));
        background.add(rectanglePanel);

        // Tạo nhóm button dưới chữ
        int buttonWidth = 414;
        int buttonHeight = 508;
        int buttonSpacing = 20;

        // Vị trí căn giữa
        int totalWidth = 3 * buttonWidth + 2 * buttonSpacing;
        int startX = (1502 - totalWidth) / 2;
        int startY = 300;

        // Button PC
        JButton pcButton = createButton("/img/ViewImage/PC.png", "PC Button Clicked");
        pcButton.setBounds(startX, startY, buttonWidth, buttonHeight);
        pcButton.addActionListener(e -> {
            // Tạo trang PlayGameWithPC và chuyển đến trang này
            PlayGameWithPC playGameWithPC = new PlayGameWithPC(mainPanel, cardLayout);
            mainPanel.add(playGameWithPC, "PlayGameWithPC");
            cardLayout.show(mainPanel, "PlayGameWithPC");
        });
        background.add(pcButton);

        // Button My Friend
        JButton myFriendButton = createButton("/img/ViewImage/MyFriend.png", "My Friend Button Clicked");
        myFriendButton.setBounds(startX + buttonWidth + buttonSpacing, startY, buttonWidth, buttonHeight);
        myFriendButton.addActionListener(e -> {
            PlayGameWithFriend playGameWithFriend = new PlayGameWithFriend(mainPanel, cardLayout);
            mainPanel.add(playGameWithFriend, "PlayGameWithFriend");
            cardLayout.show(mainPanel, "PlayGameWithFriend");
        });
        background.add(myFriendButton);

        // Button Random
        JButton randomButton = createButton("/img/ViewImage/Random.png", "Random Button Clicked");
        randomButton.setBounds(startX + 2 * (buttonWidth + buttonSpacing), startY, buttonWidth, buttonHeight);
        randomButton.addActionListener(e -> {
            PlayGameWithRandom playGameWithRandom = new PlayGameWithRandom(mainPanel, cardLayout);
            mainPanel.add(playGameWithRandom, "PlayGameWithRandom");
            cardLayout.show(mainPanel, "PlayGameWithRandom");
        });
        background.add(randomButton);
    }

    private JButton createButton(String imagePath, String tooltip) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        AudioUtil.addClickSound(button);
        return button;
    }

    private JLabel createGlowingLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 80));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        // Bạn có thể thêm hiệu ứng glow nếu muốn
        return label;
    }
}
