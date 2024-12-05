package com.example.project.views;

import com.example.project.models.User;
import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

/**
 * ProfilePage 화면.
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

        // 배경 설정
        JLabel backgroundImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        backgroundImg.setBounds(0, 0, 1502, 916);
        backgroundImg.setLayout(null);
        add(backgroundImg);

        // 둥근 모서리 오버레이 컨테이너 생성
        RoundedPanel overlayContainer = new RoundedPanel(null, new Color(0, 0, 0, 180), 10);
        overlayContainer.setBounds(136, 170, 1231, 575);
        overlayContainer.setLayout(null);
        backgroundImg.add(overlayContainer);

        // 콘텐츠 래퍼
        JPanel contentWrapper = new JPanel(null);
        contentWrapper.setBounds(0, 0, 1231, 575);
        contentWrapper.setOpaque(false);
        overlayContainer.add(contentWrapper);

        // 콘텐츠 패널
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBounds(47, 59, 1137, 456);
        contentPanel.setOpaque(false);
        contentWrapper.add(contentPanel);

        // --------------------- 이미지 열 ---------------------
        JPanel imageColumn = new JPanel(null);
        imageColumn.setBounds(0, 108, 356, 281);
        imageColumn.setOpaque(false);
        contentPanel.add(imageColumn);

        // 사용자 이미지
        JLabel userImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/userimg.png")));
        userImg.setBounds(53, 28, 252, 221);
        userImg.setHorizontalAlignment(SwingConstants.CENTER);
        userImg.setVerticalAlignment(SwingConstants.CENTER);
        imageColumn.add(userImg);

        // 썸네일 이미지
        JLabel thumbnailImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/thumbnail.png")));
        thumbnailImg.setBounds(0, 0, 356, 281);
        thumbnailImg.setHorizontalAlignment(SwingConstants.CENTER);
        thumbnailImg.setVerticalAlignment(SwingConstants.CENTER);
        imageColumn.add(thumbnailImg);

        // --------------------- 정보 열 ---------------------
        JPanel infoColumn = new JPanel(null);
        infoColumn.setBounds(356 + 40, 0, 818, 575);
        infoColumn.setOpaque(false);
        contentPanel.add(infoColumn);

        // 정보 컨테이너
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(null);
        infoContainer.setBounds(0, 0, 818, 575);
        infoContainer.setOpaque(false);
        infoColumn.add(infoContainer);

        // 사용자 정보 추가
        addInfoItem(infoContainer, "이름: " + currentUser.getUsername(), 0);
        addInfoItem(infoContainer, "나이: " + currentUser.getAge(), 80);
        addInfoItem(infoContainer, "기록: " + currentUser.getRecord(), 160);
        addInfoItem(infoContainer, "점수: " + currentUser.getCore() + "P", 240);
        addInfoItem(infoContainer, "랭킹: " + currentUser.getRanking(), 320);
        addInfoItem(infoContainer, "승률: " + currentUser.getRatio() + "%", 400);

        // 뒤로가기 버튼
        JButton backButton = createButton("/img/ViewImage/back.png", "뒤로가기 버튼");
        backButton.setBounds(1384, 30, 128, 86);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        AudioUtil.addClickSound(backButton);
        backgroundImg.add(backButton);
    }

    // 정보 항목 추가
    private void addInfoItem(JPanel container, String text, int yPosition) {
        JLabel infoLabel = new JLabel(text);
        infoLabel.setFont(new Font("Carrois Gothic", Font.PLAIN, 64));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(0, yPosition, 818, 50);
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        container.add(infoLabel);
    }

    // 이미지 및 툴팁이 있는 버튼 생성
    private JButton createButton(String imagePath, String tooltip) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false); // 포커스 상태 비활성화
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        AudioUtil.addClickSound(button);
        return button;
    }
}
