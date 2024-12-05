/*package com.example.project.views;

import com.example.project.controller.FileController;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RankingPage extends JPanel {
    private int currentPage = 0; // 현재 페이지
    private static final int ROWS_PER_PAGE = 6; // 페이지당 표시할 행 수
    private List<String[]> rankingData; // ranking.txt에서 불러온 데이터
    private JPanel dataPanel;
    private JButton rejoinButton;
    private JButton continueButton;

    public RankingPage(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0)); // 배경 없음

        // 배경 설정
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // 고정된 헤더 생성
        createHeader(background);

        // ranking.txt에서 데이터 읽기
        rankingData = FileController.getRanking();

        // 랭킹 데이터를 내림차순으로 정렬
        rankingData.sort((a, b) -> {
            try {
                int coreA = Integer.parseInt(a[1]); // "Core" 필드 (인덱스 1 위치)
                int coreB = Integer.parseInt(b[1]);
                return Integer.compare(coreB, coreA); // 높은 순서대로 정렬
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        });

        // Rejoin 버튼 생성
        rejoinButton = new JButton();
        rejoinButton.setBounds(33, 450, 80, 120);
        rejoinButton.setBorderPainted(false);
        rejoinButton.setContentAreaFilled(false);
        rejoinButton.setFocusPainted(false);
        rejoinButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateDataPanel();
            }
        });
        background.add(rejoinButton);

        // Continue 버튼 생성
        continueButton = new JButton();
        continueButton.setBounds(1396, 450, 80, 120);
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusPainted(false);
        continueButton.addActionListener(e -> {
            if ((currentPage + 1) * ROWS_PER_PAGE < rankingData.size()) {
                currentPage++;
                updateDataPanel();
            }
        });
        background.add(continueButton);

        // 데이터를 표시할 패널
        dataPanel = new JPanel(null);
        dataPanel.setBounds(0, 246, 1502, 600);
        dataPanel.setOpaque(false);
        background.add(dataPanel);

        // 모든 구성 요소가 초기화된 후 updateDataPanel 호출
        updateDataPanel();

        // 뒤로가기 버튼 (MenuPage로 돌아감)
        JButton backButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/ViewImage/back.png"))));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage")); // MenuPage로 돌아가기
        background.add(backButton);
    }

    private void createHeader(JLabel background) {
        // 헤더 그룹
        JPanel headerPanel = new RoundedPanel(10);
        headerPanel.setBounds(158, 167, 1221, 59);
        headerPanel.setBackground(new Color(0, 0, 0, 180));
        headerPanel.setLayout(null);

        // 헤더 라벨
        String[] headers = {"Rank", "Username", "Core", "Win rate", "Ratio"};
        int[] positions = {168, 382, 707, 935, 1230};
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i], SwingConstants.CENTER);
            label.setBounds(positions[i] - 158, 0, 200, 59); // 열 위치 지정
            label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            label.setForeground(Color.WHITE);
            headerPanel.add(label);
        }

        background.add(headerPanel);
    }

    private void updateDataPanel() {
        dataPanel.removeAll();

        int startY = 0; // 첫 번째 그룹의 시작 Y 위치
        int height = 80; // 각 그룹의 높이
        int rank = currentPage * ROWS_PER_PAGE + 1;

        for (int i = currentPage * ROWS_PER_PAGE; i < Math.min((currentPage + 1) * ROWS_PER_PAGE, rankingData.size()); i++) {
            String[] row = rankingData.get(i);

            // 그룹 패널
            JPanel groupPanel = new RoundedPanel(10);
            groupPanel.setBounds(158, startY, 1221, height);
            groupPanel.setBackground(new Color(0, 0, 0, 180));
            groupPanel.setLayout(null);

            // Rank
            JLabel rankLabel = new JLabel(String.valueOf(rank), SwingConstants.CENTER);
            rankLabel.setBounds(10, 0, 200, height);
            rankLabel.setFont(new Font("Arial", Font.BOLD, 20));
            rankLabel.setForeground(Color.WHITE);
            groupPanel.add(rankLabel);

            // Username
            JLabel usernameLabel = new JLabel(row[0], SwingConstants.CENTER);
            usernameLabel.setBounds(214, 0, 200, height);
            usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
            usernameLabel.setForeground(Color.WHITE);
            groupPanel.add(usernameLabel);

            // Core
            JLabel coreLabel = new JLabel(row[1] + "P", SwingConstants.CENTER);
            coreLabel.setBounds(538, 0, 200, height);
            coreLabel.setFont(new Font("Arial", Font.BOLD, 20));
            coreLabel.setForeground(Color.WHITE);
            groupPanel.add(coreLabel);

            // 승률 표시 (승/패 비율을 나타내는 바)
            JPanel winRatePanel = createWinRatePanel(row[2], row[3]);
            winRatePanel.setBounds(766, (height - 30) / 2, 300, 30); // 그룹 높이의 가운데
            groupPanel.add(winRatePanel);

            // 승패 비율 (승/패 데이터를 기반으로 계산)
            int wins = Integer.parseInt(row[2]);
            int losses = Integer.parseInt(row[3]);
            double ratio = (double) wins / (wins + losses) * 100;
            JLabel ratioLabel = new JLabel(String.format("%.0f%%", ratio), SwingConstants.CENTER);
            ratioLabel.setBounds(1060, 0, 200, height);
            ratioLabel.setFont(new Font("Arial", Font.BOLD, 20));
            ratioLabel.setForeground(Color.WHITE);
            groupPanel.add(ratioLabel);

            // 그룹을 dataPanel에 추가
            dataPanel.add(groupPanel);

            // 다음 그룹의 Y 위치 업데이트
            startY += height + 10; // 그룹 간 간격
            rank++;
        }

        // Rejoin 및 Continue 버튼 상태 업데이트
        updateNavigationButtons();

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    private void updateNavigationButtons() {
        // Rejoin 버튼
        if (currentPage > 0) {
            rejoinButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/rejon1.png")));
            rejoinButton.setEnabled(true);
        } else {
            rejoinButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/rejon.png")));
            rejoinButton.setEnabled(false);
        }

        // Continue 버튼
        if ((currentPage + 1) * ROWS_PER_PAGE < rankingData.size()) {
            continueButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/continue1.png")));
            continueButton.setEnabled(true);
        } else {
            continueButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/continue.png")));
            continueButton.setEnabled(false);
        }
    }

    private JPanel createWinRatePanel(String winsStr, String lossesStr) {
        int wins = Integer.parseInt(winsStr);
        int losses = Integer.parseInt(lossesStr);
        int totalGames = wins + losses;

        // 승/패 비율을 나타내는 패널
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        // 승리 수
        JLabel winLabel = new JLabel(wins + "W", SwingConstants.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 14));
        winLabel.setForeground(Color.WHITE);
        winLabel.setBounds(0, 0, (int) ((double) wins / totalGames * 300), 30);
        panel.add(winLabel);

        // 승리 바 (파란색)
        JPanel winBar = new JPanel();
        winBar.setBackground(Color.BLUE);
        winBar.setBounds(0, 0, (int) ((double) wins / totalGames * 300), 30);
        panel.add(winBar);

        // 패배 수
        JLabel lossLabel = new JLabel(losses + "L", SwingConstants.CENTER);
        lossLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lossLabel.setForeground(Color.WHITE);
        lossLabel.setBounds((int) ((double) wins / totalGames * 300), 0, (int) ((double) losses / totalGames * 300), 30);
        panel.add(lossLabel);

        // 패배 바 (빨간색)
        JPanel lossBar = new JPanel();
        lossBar.setBackground(Color.RED);
        lossBar.setBounds((int) ((double) wins / totalGames * 300), 0, (int) ((double) losses / totalGames * 300), 30);
        panel.add(lossBar);

        return panel;
    }
}*/

package com.example.project.views;

import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RankingPage extends JPanel {
    private int currentPage = 0; // 현재 페이지
    private static final int ROWS_PER_PAGE = 6; // 페이지당 표시되는 행 수
    private List<String[]> rankingData; // ranking.txt에서 읽은 데이터
    private JPanel dataPanel;
    private JButton rejoinButton;
    private JButton continueButton;

    public RankingPage(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0)); // 배경 없음

        // 배경 설정
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // 고정된 헤더
        createHeader(background);

        // ranking.txt에서 데이터 읽기
        rankingData = readRankingData("src/main/resources/texts/ranking.txt");

        // "Core" 기준으로 내림차순 정렬
        rankingData.sort((a, b) -> {
            try {
                int coreA = Integer.parseInt(a[1]); // "Core" 필드는 인덱스 1에 있음
                int coreB = Integer.parseInt(b[1]);
                return Integer.compare(coreB, coreA); // 내림차순 정렬
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        });

        // 이전 버튼 초기화
        rejoinButton = new JButton();
        rejoinButton.setBounds(33, 450, 80, 120);
        rejoinButton.setBorderPainted(false);
        rejoinButton.setContentAreaFilled(false);
        rejoinButton.setFocusPainted(false);
        rejoinButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateDataPanel();
            }
        });
        background.add(rejoinButton);

        // 다음 버튼 초기화
        continueButton = new JButton();
        continueButton.setBounds(1396, 450, 80, 120);
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusPainted(false);
        continueButton.addActionListener(e -> {
            if ((currentPage + 1) * ROWS_PER_PAGE < rankingData.size()) {
                currentPage++;
                updateDataPanel();
            }
        });
        background.add(continueButton);

        // 데이터 패널
        dataPanel = new JPanel(null);
        dataPanel.setBounds(0, 246, 1502, 600);
        dataPanel.setOpaque(false);
        background.add(dataPanel);

        // 모든 구성 요소가 초기화된 후 updateDataPanel 호출
        updateDataPanel();

        // 뒤로가기 버튼 (MenuPage로 돌아감)
        JButton backButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/ViewImage/back.png"))));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage")); // MenuPage로 이동
        background.add(backButton);
    }

    private void createHeader(JLabel background) {
        // 헤더 그룹
        JPanel headerPanel = new RoundedPanel(10);
        headerPanel.setBounds(158, 167, 1221, 59);
        headerPanel.setBackground(new Color(0, 0, 0, 180));
        headerPanel.setLayout(null);

        // 헤더 레이블
        String[] headers = {"순위", "사용자명", "점수", "승률", "비율"};
        int[] positions = {168, 382, 707, 935, 1230};
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i], SwingConstants.CENTER);
            label.setBounds(positions[i] - 158, 0, 200, 59); // 해당 열 위치
            label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            label.setForeground(Color.WHITE);
            headerPanel.add(label);
        }

        background.add(headerPanel);
    }

    private List<String[]> readRankingData(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split("\\s+")); // 공백을 기준으로 데이터 분리
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<User> userList = Session.getInstance().getUserList();
        System.out.println(userList);
        for (User user : userList) {
            String[] parts = user.getRecord().split("-"); // "99w"와 "122l"로 나누기
            // "w"와 "l"을 제거하고 숫자만 추출
            int wins = Integer.parseInt(parts[0].replaceAll("[^0-9]", "")); // "99w"에서 숫자만 남기기
            int losses = Integer.parseInt(parts[1].replaceAll("[^0-9]", "")); // 숫자만 남기기
            String userLine = (user.getUsername() + " " +user.getCore()+" "+ wins+ " "+ losses);
            data.add(userLine.split("\\s+"));
        }

        return data;
    }

    private void updateDataPanel() {
        dataPanel.removeAll();

        int startY = 0; // 첫 번째 그룹의 Y 위치 시작점
        int height = 80; // 각 그룹의 높이
        int rank = currentPage * ROWS_PER_PAGE + 1;

        for (int i = currentPage * ROWS_PER_PAGE; i < Math.min((currentPage + 1) * ROWS_PER_PAGE, rankingData.size()); i++) {
            String[] row = rankingData.get(i);

            // 그룹 패널
            JPanel groupPanel = new RoundedPanel(10);
            groupPanel.setBounds(158, startY, 1221, height);
            groupPanel.setBackground(new Color(0, 0, 0, 180));
            groupPanel.setLayout(null);

            // 순위
            JLabel rankLabel = new JLabel(String.valueOf(rank), SwingConstants.CENTER);
            rankLabel.setBounds(10, 0, 200, height);
            rankLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            rankLabel.setForeground(Color.WHITE);
            groupPanel.add(rankLabel);

            // 사용자명
            JLabel usernameLabel = new JLabel(row[0], SwingConstants.CENTER);
            usernameLabel.setBounds(214, 0, 200, height);
            usernameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            usernameLabel.setForeground(Color.WHITE);
            groupPanel.add(usernameLabel);

            // 점수
            JLabel coreLabel = new JLabel(row[1] + "P", SwingConstants.CENTER);
            coreLabel.setBounds(538, 0, 200, height);
            coreLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            coreLabel.setForeground(Color.WHITE);
            groupPanel.add(coreLabel);

            // 승률 (승/패 비율 표시하는 사각형)
            JPanel winRatePanel = createWinRatePanel(row[2], row[3]);
            winRatePanel.setBounds(766, (height - 30) / 2, 300, 30); // 그룹 높이의 중앙
            groupPanel.add(winRatePanel);

            // 비율 (승/패 데이터로 계산)
            int wins = Integer.parseInt(row[2]);
            int losses = Integer.parseInt(row[3]);
            double ratio = (double) wins / (wins + losses) * 100;
            JLabel ratioLabel = new JLabel(String.format("%.0f%%", ratio), SwingConstants.CENTER);
            ratioLabel.setBounds(1060, 0, 200, height);
            ratioLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            ratioLabel.setForeground(Color.WHITE);
            groupPanel.add(ratioLabel);

            // 데이터 패널에 그룹 추가
            dataPanel.add(groupPanel);

            // 다음 그룹의 Y 위치 업데이트
            startY += height + 10; // 그룹 간 간격
            rank++;
        }

        // 이전 및 다음 버튼 상태 업데이트
        updateNavigationButtons();

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    private void updateNavigationButtons() {
        // 이전 버튼
        if (currentPage > 0) {
            rejoinButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/rejon1.png")));
            rejoinButton.setEnabled(true);
        } else {
            rejoinButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/rejon.png")));
            rejoinButton.setEnabled(false);
        }

        // 다음 버튼
        if ((currentPage + 1) * ROWS_PER_PAGE < rankingData.size()) {
            continueButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/continue1.png")));
            continueButton.setEnabled(true);
        } else {
            continueButton.setIcon(new ImageIcon(getClass().getResource("/img/ViewImage/continue.png")));
            continueButton.setEnabled(false);
        }
    }

    private JPanel createWinRatePanel(String winsStr, String lossesStr) {
        int wins = Integer.parseInt(winsStr);
        int losses = Integer.parseInt(lossesStr);
        int totalGames = wins + losses;

        // 승/패 비율 표시 패널
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        // 승리 수
        JLabel winLabel = new JLabel(wins + "W", SwingConstants.CENTER);
        winLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        winLabel.setForeground(Color.WHITE);
        winLabel.setBounds(0, 0, (int) ((double) wins / totalGames * 300), 30);
        panel.add(winLabel);

        // 승리 막대 (파란색)
        JPanel winBar = new JPanel();
        winBar.setBackground(Color.BLUE);
        winBar.setBounds(0, 0, (int) ((double) wins / totalGames * 300), 30);
        panel.add(winBar);

        // 패배 수
        JLabel lossLabel = new JLabel(losses + "L", SwingConstants.CENTER);
        lossLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        lossLabel.setForeground(Color.WHITE);
        lossLabel.setBounds((int) ((double) wins / totalGames * 300), 0, (int) ((double) losses / totalGames * 300), 30);
        panel.add(lossLabel);

        // 패배 막대 (빨간색)
        JPanel lossBar = new JPanel();
        lossBar.setBackground(Color.RED);
        lossBar.setBounds((int) ((double) wins / totalGames * 300), 0, (int) ((double) losses / totalGames * 300), 30);
        panel.add(lossBar);

        return panel;
    }
}

