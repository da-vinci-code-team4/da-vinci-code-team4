package com.example.project.views;

import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.utils.UserManager;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<User> userList;
    private UserManager userManager;

    public MenuPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList,  UserManager userManager) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;
        this.userManager = userManager;

        setLayout(null); // Layout null để tự định vị các nút
        setBackground(new Color(255, 255, 255, 0));

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Nút Profile
        JButton profileButton = createMenuButton("Profile", 265, 235);
        profileButton.addActionListener(e -> {
            User currentUser = Session.getInstance().getCurrentUser();
            if (currentUser != null) {
                ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, currentUser);
                mainPanel.add(profilePage, "ProfilePage");
                cardLayout.show(mainPanel, "ProfilePage");
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa đăng nhập.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        background.add(profileButton);

        // Nút Ranking
        JButton rankingButton = createMenuButton("Ranking", 265 + 482, 235);
        rankingButton.addActionListener(e -> {
            RankingPage rankingPage = new RankingPage(mainPanel, cardLayout);
            mainPanel.add(rankingPage, "RankingPage");
            cardLayout.show(mainPanel, "RankingPage");
        });
        background.add(rankingButton);

        // Nút History
        JButton historyButton = createMenuButton("History", 265, 235 + 239);
        historyButton.addActionListener(e -> {
            HistoryPage historyPage = new HistoryPage(mainPanel, cardLayout);
            mainPanel.add(historyPage, "HistoryPage");
            cardLayout.show(mainPanel, "HistoryPage");
        });
        background.add(historyButton);

        // Nút Correction
        JButton correctionButton = createMenuButton("Correction", 265 + 482, 235 + 239);
        correctionButton.addActionListener(e -> {
            CorrectionPage correctionPage = new CorrectionPage(mainPanel, cardLayout, userList);
            mainPanel.add(correctionPage, "CorrectionPage");
            cardLayout.show(mainPanel, "CorrectionPage");
        });
        background.add(correctionButton);

        // Nút Log Out
        JButton logoutButton = createMenuButton("Log Out", 560, 668);
        logoutButton.addActionListener(e -> {
            Session.getInstance().setCurrentUser(null); // Đăng xuất bằng cách xóa currentUser
            cardLayout.show(mainPanel, "RegisterPage");
        });
        background.add(logoutButton);

        // Nút Back
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);
    }

    /**
     * Phương thức tạo nút Menu.
     *
     * @param text Văn bản trên nút
     * @param x    Vị trí X
     * @param y    Vị trí Y
     * @return JButton với các thuộc tính đã thiết lập
     */
    private JButton createMenuButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 400, 200); // Kích thước nút
        button.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/background_button.png")));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.BOLD, 48));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}