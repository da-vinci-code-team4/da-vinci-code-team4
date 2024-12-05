package com.example.project.views;

import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<User> userList;


    public MenuPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;

        setLayout(null); // 레이아웃 null로 설정하여 버튼 위치 지정
        setBackground(new Color(255, 255, 255, 0));

        // 배경 설정
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Profile 버튼
        JButton profileButton = createMenuButton("프로필", 265, 235);
        profileButton.addActionListener(e -> {
            User currentUser = Session.getInstance().getCurrentUser();
            if (currentUser != null) {
                ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, currentUser);
                mainPanel.add(profilePage, "ProfilePage");
                cardLayout.show(mainPanel, "ProfilePage");
            } else {
                JOptionPane.showMessageDialog(this, "로그인하지 않았습니다.", "알림", JOptionPane.WARNING_MESSAGE);
            }
        });
        background.add(profileButton);

        // Ranking 버튼
        JButton rankingButton = createMenuButton("랭킹", 265 + 482, 235);
        rankingButton.addActionListener(e -> {
            RankingPage rankingPage = new RankingPage(mainPanel, cardLayout);
            mainPanel.add(rankingPage, "RankingPage");
            cardLayout.show(mainPanel, "RankingPage");
        });
        background.add(rankingButton);

        // History 버튼
        JButton historyButton = createMenuButton("히스토리", 265, 235 + 239);
        historyButton.addActionListener(e -> {
            HistoryPage historyPage = new HistoryPage(mainPanel, cardLayout);
            mainPanel.add(historyPage, "HistoryPage");
            cardLayout.show(mainPanel, "HistoryPage");
        });
        background.add(historyButton);

        // Correction 버튼
        JButton correctionButton = createMenuButton("수정", 265 + 482, 235 + 239);
        correctionButton.addActionListener(e -> {
            CorrectionPage correctionPage = new CorrectionPage(mainPanel, cardLayout, userList);
            mainPanel.add(correctionPage, "CorrectionPage");
            cardLayout.show(mainPanel, "CorrectionPage");
        });
        background.add(correctionButton);

        // Log Out 버튼
        JButton logoutButton = createMenuButton("로그아웃", 560, 668);
        logoutButton.addActionListener(e -> {
            Session.getInstance().setCurrentUser(null); // 로그아웃: currentUser 초기화
            cardLayout.show(mainPanel, "RegisterPage");
        });
        background.add(logoutButton);

        // 뒤로가기 버튼
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);
        AudioUtil.addClickSound(backButton);
    }

    /**
     * 메뉴 버튼 생성 메서드.
     *
     * @param text 버튼에 표시할 텍스트
     * @param x    X 좌표
     * @param y    Y 좌표
     * @return JButton 설정된 속성을 포함한 버튼
     */
    private JButton createMenuButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 400, 200); // 버튼 크기
        button.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/background_button.png")));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 48));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(button);
        return button;
    }
}
