package com.example.project.views;

import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Trang ProfilePage cho ứng dụng.
 */
public class ProfilePage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private User currentUser;

    public ProfilePage(JPanel mainPanel, CardLayout cardLayout, User user) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.currentUser = user;
        setLayout(null);
        setBackground(Color.WHITE);

        // Thiết lập hình nền
        JLabel backgroundImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        backgroundImg.setBounds(0, 0, 1502, 916);
        backgroundImg.setLayout(null);
        add(backgroundImg);

        // Rectangle (Overlay Container)
        RoundedPanel overlayContainer = new RoundedPanel(null, new Color(0, 0, 0, 180), 10); // Bo góc 10px, nền đen với độ trong suốt 71%
        overlayContainer.setBounds(136, 170, 1231, 575); // (1502 - 1231)/2 ≈ 135.5 ~ 136; (916 - 575)/2 ≈ 170.5 ~ 170
        overlayContainer.setLayout(null);
        backgroundImg.add(overlayContainer);

        // Content Wrapper
        JPanel contentWrapper = new JPanel(null);
        contentWrapper.setBounds(0, 0, 1231, 575);
        contentWrapper.setOpaque(false);
        overlayContainer.add(contentWrapper);

        // Content Wrapper kích thước 1137x456px, cách nhau 40px
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBounds(47, 59, 1137, 456); // Tổng kích thước 1137x456px
        contentPanel.setOpaque(false);
        contentWrapper.add(contentPanel);

        // --------------------- Cột Hình Ảnh ---------------------
        JPanel imageColumn = new JPanel(null);
        imageColumn.setBounds(0, 108, 356, 281); // Vị trí (0,108), kích thước 356x281px
        imageColumn.setOpaque(false);
        contentPanel.add(imageColumn);

        // User Image
        JLabel userImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/userimg.png")));
        userImg.setBounds(53, 28, 252, 221); // Vị trí (52,135), kích thước 252x221px
        userImg.setHorizontalAlignment(SwingConstants.CENTER);
        userImg.setVerticalAlignment(SwingConstants.CENTER);
        imageColumn.add(userImg);

        // Thumbnail Image
        JLabel thumbnailImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/thumbnail.png")));
        thumbnailImg.setBounds(0, 0, 356, 281);
        thumbnailImg.setHorizontalAlignment(SwingConstants.CENTER);
        thumbnailImg.setVerticalAlignment(SwingConstants.CENTER);
        imageColumn.add(thumbnailImg);



        // --------------------- Cột Thông Tin ---------------------
        JPanel infoColumn = new JPanel(null);
        infoColumn.setBounds(356 + 40, 0, 818, 575); // 356 (width của imageColumn) + 40px spacing
        infoColumn.setOpaque(false);
        contentPanel.add(infoColumn);

        // Info Container
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(null);
        infoContainer.setBounds(0, 0, 818, 575);
        infoContainer.setOpaque(false);
        infoColumn.add(infoContainer);

        // Thêm các mục thông tin
        addInfoItem(infoContainer, "Name: " + currentUser.getUsername(), 0);
        addInfoItem(infoContainer, "Age: " + currentUser.getAge(), 80);
        addInfoItem(infoContainer, "Record: " + currentUser.getRecord(), 160);
        addInfoItem(infoContainer, "Core: " + currentUser.getCore() + "P", 240);
        addInfoItem(infoContainer, "Ranking: " + currentUser.getRanking(), 320);
        addInfoItem(infoContainer, "Ratio: " + currentUser.getRatio() + "%", 400);

        // Nút Back
        JButton backButton = createButton("/img/ViewImage/back.png", "Back Button");
        backButton.setBounds(1384, 30, 128, 86);// Vị trí (128,86), kích thước 128x86px
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage"));
        backgroundImg.add(backButton);
    }

    /**
     * Phương thức thêm một mục thông tin vào infoContainer.
     *
     * @param container Thư mục chứa các mục thông tin.
     * @param text      Văn bản của mục thông tin.
     * @param yPosition Vị trí Y của mục thông tin.
     */
    private void addInfoItem(JPanel container, String text, int yPosition) {
        JLabel infoLabel = new JLabel(text);
        infoLabel.setFont(new Font("Carrois Gothic", Font.PLAIN, 64));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(0, yPosition, 818, 50); // Kích thước 818x50px
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        container.add(infoLabel);
    }

    /**
     * Phương thức tạo một JButton với hình ảnh và tooltip.
     *
     * @param imagePath Đường dẫn tới hình ảnh.
     * @param tooltip   Văn bản hiển thị khi hover.
     * @return JButton đã được cấu hình.
     */
    private JButton createButton(String imagePath, String tooltip) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        return button;
    }
}