package com.example.project.views;

import com.example.project.audio.AudioManager;
import com.example.project.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DefeatScreen.java
 *
 * 사용자가 패배했을 때 패배 화면을 표시합니다.
 */
public class DefeatScreen extends JPanel {
    private User user;
    private int timeTaken;  // 게임을 완료하는 데 걸린 시간 (초)
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public DefeatScreen(User user, int timeTaken, JPanel mainPanel, CardLayout cardLayout) {
        this.user = user;
        this.timeTaken = timeTaken;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        initializeUI();
    }

    private void initializeUI() {
        // 크기 및 배경색 설정
        setPreferredSize(new Dimension(1502, 916));
        setBackground(Color.decode("#D9D9D9"));
        setLayout(null); // 절대 레이아웃 또는 다른 적절한 레이아웃 사용

        // 모서리가 둥근 내부 패널 생성 (20px)
        RoundedPanel innerPanel = new RoundedPanel(20);
        innerPanel.setBackground(Color.decode("#D9D9D9"));
        innerPanel.setLayout(null);
        innerPanel.setBounds(0, 0, 1502, 916);
        add(innerPanel);

        // 상단에 PNG 아이콘 추가
        ImageIcon defeatIcon = new ImageIcon(getClass().getResource("/img/ViewImage/defeat.png"));
        JLabel iconLabel = new JLabel(defeatIcon);
        iconLabel.setBounds((1502 - defeatIcon.getIconWidth()) / 2, 31, defeatIcon.getIconWidth(), defeatIcon.getIconHeight());
        innerPanel.add(iconLabel);

        // 시간 표시 레이블 추가
        JLabel timeLabel = new JLabel("시간: " + formatTime(timeTaken));
        timeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setBounds(0, iconLabel.getY() + defeatIcon.getIconHeight() - 20, 1502, 30);
        innerPanel.add(timeLabel);

        // 점수 표시 레이블 추가 (100점 감소)
        int displayScore = user.getCore() - 100;
        JLabel scoreLabel = new JLabel("점수: " + displayScore + "P(-100)");
        scoreLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setBounds(0, timeLabel.getY() + 40, 1502, 30);
        innerPanel.add(scoreLabel);

        // 랭킹 표시 레이블 추가
        JLabel rankLabel = new JLabel("랭킹: " + user.getRanking());
        rankLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rankLabel.setBounds(0, scoreLabel.getY() + 40, 1502, 30);
        innerPanel.add(rankLabel);

        // "다시" 버튼 추가 (Retry)
        JButton retryButton = new JButton("다시");
        retryButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        retryButton.setBounds(1502 / 2 - 170, rankLabel.getY() + 60, 150, 40);
        innerPanel.add(retryButton);

        // "홈" 버튼 추가 (Home)
        JButton homeButton = new JButton("홈");
        homeButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        homeButton.setBounds(1502 / 2 + 20, rankLabel.getY() + 60, 150, 40);
        innerPanel.add(homeButton);

        // "다시" 버튼 이벤트 처리
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("다시 버튼이 클릭되었습니다. PlayGameWithPC를 재설정합니다.");

                // 기존 PlayGameWithPC 제거
                Component[] components = mainPanel.getComponents();
                for (Component comp : components) {
                    if (comp instanceof PlayGameWithPC) {
                        mainPanel.remove(comp);
                        break;
                    }
                }

                // 새로운 PlayGameWithPC 인스턴스 생성
                PlayGameWithPC newPlayGame = new PlayGameWithPC(mainPanel, cardLayout);

                // 새로운 PlayGameWithPC를 mainPanel에 추가하고 같은 카드 이름으로 설정
                mainPanel.add(newPlayGame, "PlayGameWithPC");

                // PlayGameWithPC 카드 표시
                cardLayout.show(mainPanel, "PlayGameWithPC");
                AudioManager.getInstance().playGameBackgroundMusic();

                // UI 업데이트를 위해 재검증 및 다시 그리기
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        // "홈" 버튼 이벤트 처리
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("홈 버튼이 클릭되었습니다. MyPage로 전환합니다.");
                cardLayout.show(mainPanel, "MyPage");
                AudioManager.getInstance().playBackgroundMusic();
            }
        });
    }

    /**
     * 시간을 "분:초" 형식의 문자열로 변환합니다.
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
     * 모서리가 둥근 JPanel 클래스.
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
