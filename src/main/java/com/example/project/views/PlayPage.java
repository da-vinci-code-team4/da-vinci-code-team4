package com.example.project.views;

import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class PlayPage extends JPanel {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public PlayPage(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Nút Back để quay lại MyPage
        JButton backButton = createButton("/img/back.png", "Back Button");
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
        JButton pcButton = createButton("/img/PC.png", "PC Button Clicked");
        pcButton.setBounds(startX, startY, buttonWidth, buttonHeight);
        pcButton.addActionListener(e -> cardLayout.show(mainPanel, "PlayGameWithPC")); // Chuyển sang PlayGameWithPC
        background.add(pcButton);

        // Button My Friend
        JButton myFriendButton = createButton("/img/MyFriend.png", "My Friend Button Clicked");
        myFriendButton.setBounds(startX + buttonWidth + buttonSpacing, startY, buttonWidth, buttonHeight);
        background.add(myFriendButton);

        // Button Random
        JButton randomButton = createButton("/img/Random.png", "Random Button Clicked");
        randomButton.setBounds(startX + 2 * (buttonWidth + buttonSpacing), startY, buttonWidth, buttonHeight);
        background.add(randomButton);

        setVisible(true);
    }

    private JButton createButton(String iconPath, String message) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> System.out.println(message));
        return button;
    }

    private JLabel createGlowingLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 128));
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        return label;
    }
}
