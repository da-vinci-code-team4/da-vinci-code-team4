package com.example.project.views;

import com.example.project.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * VictoryScreen.java
 *
 * 플레이어가 게임에서 승리했을 때 승리 화면을 표시합니다.
 */
public class VictoryScreen extends JPanel {
    private User user;
    private int timeTaken;  // 게임 완료 시간(초)

    public VictoryScreen(User user, int timeTaken) {
        this.user = user;
        this.timeTaken = timeTaken;

        initializeUI();
    }

    private void initializeUI() {
        // 화면 크기와 배경색 설정
        setPreferredSize(new Dimension(784, 400));
        setBackground(Color.decode("#D9D9D9"));
        setLayout(new BorderLayout());

        // 모서리가 둥근 패널 설정 (20px 반경)
        JPanel mainPanel = new RoundedPanel(20);
        mainPanel.setBackground(Color.decode("#D9D9D9"));
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 784, 400);
        add(mainPanel, BorderLayout.CENTER);

        // 상단에 PNG 이미지 추가 (위쪽 여백 31px)
        ImageIcon victoryIcon = new ImageIcon(getClass().getResource("/img/ViewImage/victory.png"));
        JLabel iconLabel = new JLabel(victoryIcon);
        iconLabel.setBounds((784 - victoryIcon.getIconWidth()) / 2, 31, victoryIcon.getIconWidth(), victoryIcon.getIconHeight());
        mainPanel.add(iconLabel);

        // 시간 표시 라벨 (PNG 이미지와 -20px 간격)
        JLabel timeLabel = new JLabel("시간: " + formatTime(timeTaken));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setBounds(0, iconLabel.getY() + victoryIcon.getIconHeight() - 20, 784, 30);
        mainPanel.add(timeLabel);

        // 점수 표시 라벨 (100점 추가)
        // User로부터 점수를 가져와 100 추가
        int displayScore = user.getCore() + 100;
        JLabel scoreLabel = new JLabel("점수: " + displayScore + "P(+100)");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setBounds(0, timeLabel.getY() + 40, 784, 30);
        mainPanel.add(scoreLabel);

        // 랭킹 표시 라벨 (점수와 10px 간격)
        JLabel rankLabel = new JLabel("랭킹: " + user.getRanking());
        rankLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rankLabel.setBounds(0, scoreLabel.getY() + 40, 784, 30);
        mainPanel.add(rankLabel);

        // "다시" 버튼 (재시작)
        JButton retryButton = new JButton("다시");
        retryButton.setFont(new Font("Arial", Font.BOLD, 16));
        retryButton.setBounds(784 / 2 - 170, rankLabel.getY() + 60, 150, 40);
        mainPanel.add(retryButton);

        // "홈" 버튼 (메인 화면으로)
        JButton homeButton = new JButton("홈");
        homeButton.setFont(new Font("Arial", Font.BOLD, 16));
        homeButton.setBounds(784 / 2 + 20, rankLabel.getY() + 60, 150, 40);
        mainPanel.add(homeButton);

        // 버튼 이벤트 처리
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 게임 재시작 처리
                // 예: Controller 또는 MainFrame을 호출하여 화면을 전환
                // 아래는 가상의 예시입니다:
                // Controller.restartGame();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 메인 화면으로 이동
                // 예: Controller 또는 MainFrame을 호출하여 메인 화면으로 전환
                // 아래는 가상의 예시입니다:
                // Controller.goToHome();
            }
        });
    }

    /**
     * 초 단위를 "분:초" 문자열 형식으로 변환합니다.
     *
     * @param totalSeconds 총 초
     * @return 형식화된 시간 문자열
     */
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d : %02d", minutes, seconds);
    }

    /**
     * 둥근 모서리를 가진 JPanel 클래스
     */
    private class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }
}