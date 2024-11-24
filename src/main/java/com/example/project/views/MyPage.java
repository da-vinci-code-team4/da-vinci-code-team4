package com.example.project.views;

import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Danh sách người dùng đã đăng ký
    private List<User> userList;

    public MyPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;

        setLayout(null); // Sử dụng layout null để tự định vị các thành phần
        setBackground(Color.WHITE); // Màu nền của trang MyPage

        // Tạo trang MyPage
        createMyPage();
    }

    /**
     * Phương thức tạo trang MyPage.
     */
    private void createMyPage() {
        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Group các button dọc bên trái
        int buttonSize = 100; // Kích thước button hình vuông
        int buttonSpacing = 35; // Khoảng cách giữa các button
        int leftMargin = 50; // Khoảng cách từ viền trái

        // Tính toán vị trí để căn giữa theo chiều dọc
        int totalHeight = 3 * buttonSize + 2 * buttonSpacing; // Chiều cao của tất cả các button và khoảng cách
        int startY = (916 - totalHeight) / 2; // Vị trí Y bắt đầu để căn giữa

        // Button Menu
        JButton menuButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/menu_button.png")));
        menuButton.setBounds(leftMargin, startY, buttonSize, buttonSize);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.addActionListener(e -> {
            MenuPage menuPage = new MenuPage(mainPanel, cardLayout, userList);
            mainPanel.add(menuPage, "MenuPage");
            cardLayout.show(mainPanel, "MenuPage");
        });
        background.add(menuButton);

        // Button Setting
        JButton settingButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/setting_button.png")));
        settingButton.setBounds(leftMargin, startY + buttonSize + buttonSpacing, buttonSize, buttonSize);
        settingButton.setBorderPainted(false);
        settingButton.setContentAreaFilled(false);
        settingButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Chuyển trang sang AudioSettingPage khi nhấn nút Setting
        settingButton.addActionListener(e -> {
            // Kiểm tra xem AudioSettingPage đã được thêm vào mainPanel chưa
            if (!isPageAdded("AudioSettingPage")) {
                AudioSettingPage audioSettingPage = new AudioSettingPage(mainPanel, cardLayout);
                mainPanel.add(audioSettingPage, "AudioSettingPage");
            }
            cardLayout.show(mainPanel, "AudioSettingPage");
        });
        background.add(settingButton);

        // Button Info
        JButton infoButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/info_button.png")));
        infoButton.setBounds(leftMargin, startY + 2 * (buttonSize + buttonSpacing), buttonSize, buttonSize);
        infoButton.setBorderPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Chuyển trang sang InfoPage khi nhấn nút Info
        infoButton.addActionListener(e -> {
            InfoPage infoPage = new InfoPage(mainPanel, cardLayout);
            mainPanel.add(infoPage, "InfoPage");
            cardLayout.show(mainPanel, "InfoPage");
        });
        background.add(infoButton);

        // Button Play
        ImageIcon playIcon = new ImageIcon(getClass().getResource("/img/ViewImage/play.png"));
        JButton playButton = new JButton(playIcon);

        // Lấy kích thước của ảnh
        int playButtonWidth = playIcon.getIconWidth();
        int playButtonHeight = playIcon.getIconHeight();
        int playButtonX = 1502 - playButtonWidth - 20; // Cách mép phải 20px
        int playButtonY = 916 - playButtonHeight - 20; // Cách mép dưới 20px

        playButton.setBounds(playButtonX, playButtonY, playButtonWidth, playButtonHeight);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Chuyển trang sang PlayPage khi nhấn nút Play
        playButton.addActionListener(e -> {
            PlayPage playPage = new PlayPage(mainPanel, cardLayout);
            mainPanel.add(playPage, "PlayPage");
            cardLayout.show(mainPanel, "PlayPage");
        });
        background.add(playButton);
    }

    /**
     * Kiểm tra xem trang đã được thêm vào mainPanel chưa để tránh thêm nhiều lần.
     *
     * @param pageName Tên của trang cần kiểm tra.
     * @return true nếu đã được thêm, false nếu chưa.
     */
    private boolean isPageAdded(String pageName) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(pageName)) {
                return true;
            }
        }
        return false;
    }
}