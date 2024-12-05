package com.example.project.views;

import com.example.project.models.User;
import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<User> userList;

    // 등록된 사용자 목록

    public MyPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;

        setLayout(null); // 레이아웃을 null로 설정하여 컴포넌트의 위치를 직접 지정
        setBackground(Color.WHITE); // MyPage 배경색 설정

        // MyPage 화면 구성 생성
        createMyPage();
    }

    /**
     * MyPage 화면을 생성하는 메서드.
     */
    private void createMyPage() {
        // 배경 설정
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // 왼쪽에 버튼 그룹 배치
        int buttonSize = 100; // 버튼 크기 (정사각형)
        int buttonSpacing = 35; // 버튼 간 간격
        int leftMargin = 50; // 왼쪽 여백

        // 세로 중앙 정렬을 위한 위치 계산
        int totalHeight = 3 * buttonSize + 2 * buttonSpacing; // 버튼과 간격의 총 높이
        int startY = (916 - totalHeight) / 2; // 시작 Y 위치 계산

        // 메뉴 버튼
        JButton menuButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/menu_button.png")));
        menuButton.setBounds(leftMargin, startY, buttonSize, buttonSize);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false); // 포커스 상태 비활성화
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(menuButton); // 클릭 시 소리 추가
        menuButton.addActionListener(e -> {
            MenuPage menuPage = new MenuPage(mainPanel, cardLayout, userList);
            mainPanel.add(menuPage, "MenuPage");
            cardLayout.show(mainPanel, "MenuPage");
        });
        background.add(menuButton);

        // 설정 버튼
        JButton settingButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/setting_button.png")));
        settingButton.setBounds(leftMargin, startY + buttonSize + buttonSpacing, buttonSize, buttonSize);
        settingButton.setBorderPainted(false);
        settingButton.setContentAreaFilled(false);
        settingButton.setFocusPainted(false); // 포커스 상태 비활성화
        settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(settingButton); // 클릭 시 소리 추가
        // 설정 버튼 클릭 시 AudioSettingPage로 이동
        settingButton.addActionListener(e -> {
            // AudioSettingPage가 mainPanel에 추가되었는지 확인
            if (!isPageAdded("AudioSettingPage")) {
                AudioSettingPage audioSettingPage = new AudioSettingPage(mainPanel, cardLayout);
                mainPanel.add(audioSettingPage, "AudioSettingPage");
            }
            cardLayout.show(mainPanel, "AudioSettingPage");
        });
        background.add(settingButton);

        // 정보 버튼
        JButton infoButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/info_button.png")));
        infoButton.setBounds(leftMargin, startY + 2 * (buttonSize + buttonSpacing), buttonSize, buttonSize);
        infoButton.setBorderPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setFocusPainted(false); // 포커스 상태 비활성화
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(infoButton); // 클릭 시 소리 추가
        // 정보 버튼 클릭 시 InfoPage로 이동
        infoButton.addActionListener(e -> {
            InfoPage infoPage = new InfoPage(mainPanel, cardLayout);
            mainPanel.add(infoPage, "InfoPage");
            cardLayout.show(mainPanel, "InfoPage");
        });
        background.add(infoButton);

        // 플레이 버튼
        ImageIcon playIcon = new ImageIcon(getClass().getResource("/img/ViewImage/play.png"));
        JButton playButton = new JButton(playIcon);

        // 이미지 크기 가져오기
        int playButtonWidth = playIcon.getIconWidth();
        int playButtonHeight = playIcon.getIconHeight();
        int playButtonX = 1502 - playButtonWidth - 20; // 오른쪽에서 20px 간격
        int playButtonY = 916 - playButtonHeight - 20; // 아래에서 20px 간격

        playButton.setBounds(playButtonX, playButtonY, playButtonWidth, playButtonHeight);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false); // 포커스 상태 비활성화
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(playButton); // 클릭 시 소리 추가

        // 플레이 버튼 클릭 시 PlayPage로 이동
        playButton.addActionListener(e -> {
            PlayPage playPage = new PlayPage(mainPanel, cardLayout);
            mainPanel.add(playPage, "PlayPage");
            cardLayout.show(mainPanel, "PlayPage");
        });
        background.add(playButton);
    }

    /**
     * 페이지가 mainPanel에 이미 추가되었는지 확인하여 중복 추가를 방지하는 메서드.
     *
     * @param pageName 확인할 페이지 이름.
     * @return true면 이미 추가됨, false면 아직 추가되지 않음.
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