package com.example.project.views;

import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 응용 프로그램의 프로필 페이지를 나타냅니다.
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

        // 배경 이미지 설정
        JLabel backgroundImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        backgroundImg.setBounds(0, 0, 1502, 916);
        backgroundImg.setLayout(null);
        add(backgroundImg);

        // 사각형(오버레이 컨테이너)
        RoundedPanel overlayContainer = new RoundedPanel(null, new Color(0, 0, 0, 180), 10); // 둥근 모서리 10px, 반투명 검은색 배경
        overlayContainer.setBounds(136, 170, 1231, 575); // (1502 - 1231)/2 ≈ 135.5 ~ 136; (916 - 575)/2 ≈ 170.5 ~ 170
        overlayContainer.setLayout(null);
        backgroundImg.add(overlayContainer);

        // 컨텐츠 래퍼
        JPanel contentWrapper = new JPanel(null);
        contentWrapper.setBounds(0, 0, 1231, 575);
        contentWrapper.setOpaque(false);
        overlayContainer.add(contentWrapper);

        // 컨텐츠 래퍼 크기: 1137x456px, 간격 40px
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBounds(47, 59, 1137, 456); // 전체 크기: 1137x456px
        contentPanel.setOpaque(false);
        contentWrapper.add(contentPanel);

        // --------------------- 이미지 열 ---------------------
        JPanel imageColumn = new JPanel(null);
        imageColumn.setBounds(0, 108, 356, 281); // 위치 (0,108), 크기 356x281px
        imageColumn.setOpaque(false);
        contentPanel.add(imageColumn);

        // 사용자 이미지
        JLabel userImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/userimg.png")));
        userImg.setBounds(53, 28, 252, 221); // 위치 (52,135), 크기 252x221px
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
        infoColumn.setBounds(356 + 40, 0, 818, 575); // 356 (imageColumn의 너비) + 40px 간격
        infoColumn.setOpaque(false);
        contentPanel.add(infoColumn);

        // 정보 컨테이너
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(null);
        infoContainer.setBounds(0, 0, 818, 575);
        infoContainer.setOpaque(false);
        infoColumn.add(infoContainer);

        // 정보 항목 추가
        addInfoItem(infoContainer, "이름: " + currentUser.getUsername(), 0);
        addInfoItem(infoContainer, "나이: " + currentUser.getAge(), 80);
        addInfoItem(infoContainer, "기록: " + currentUser.getRecord(), 160);
        addInfoItem(infoContainer, "점수: " + currentUser.getCore() + "P", 240);
        addInfoItem(infoContainer, "랭킹: " + currentUser.getRanking(), 320);
        addInfoItem(infoContainer, "비율: " + currentUser.getRatio() + "%", 400);

        // 뒤로가기 버튼
        JButton backButton = createButton("/img/ViewImage/back.png", "뒤로가기 버튼");
        backButton.setBounds(1384, 30, 128, 86); // 위치 (1384,30), 크기 128x86px
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage"));
        backgroundImg.add(backButton);
    }

    /**
     * 정보 컨테이너에 정보를 추가하는 메서드.
     *
     * @param container 정보 항목을 추가할 컨테이너.
     * @param text      정보 항목의 텍스트.
     * @param yPosition 정보 항목의 Y 위치.
     */
    private void addInfoItem(JPanel container, String text, int yPosition) {
        JLabel infoLabel = new JLabel(text);
        infoLabel.setFont(new Font("Carrois Gothic", Font.PLAIN, 64));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(0, yPosition, 818, 50); // 크기: 818x50px
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        container.add(infoLabel);
    }

    /**
     * 이미지와 툴팁을 가진 JButton 생성 메서드.
     *
     * @param imagePath 이미지 경로.
     * @param tooltip   툴팁 텍스트.
     * @return 설정된 JButton.
     */
    private JButton createButton(String imagePath, String tooltip) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false); // 포커스 상태 비활성화
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        return button;
    }
}