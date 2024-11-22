package com.example.project.views;

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
    private int currentPage = 0; // Trang hiện tại
    private static final int ROWS_PER_PAGE = 6; // Số hàng hiển thị mỗi trang
    private List<String[]> rankingData; // Dữ liệu từ ranking.txt
    private JPanel dataPanel;
    private JButton rejoinButton;
    private JButton continueButton;

    public RankingPage(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0)); // Không nền

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/background.png"))));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Header cố định
        createHeader(background);

        // Đọc dữ liệu từ ranking.txt
        rankingData = readRankingData("src/main/resources/ranking.txt");

        // Sắp xếp rankingData theo "Core" từ cao xuống thấp
        rankingData.sort((a, b) -> {
            try {
                int coreA = Integer.parseInt(a[1]); // Trường "Core" ở vị trí index 1
                int coreB = Integer.parseInt(b[1]);
                return Integer.compare(coreB, coreA); // Sắp xếp từ cao xuống thấp
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        });

        // Khởi tạo Rejoin Button
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

        // Khởi tạo Continue Button
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

        // Panel chứa dữ liệu
        dataPanel = new JPanel(null);
        dataPanel.setBounds(0, 246, 1502, 600);
        dataPanel.setOpaque(false);
        background.add(dataPanel);

        // Gọi updateDataPanel sau khi tất cả các thành phần được khởi tạo
        updateDataPanel();

        // Nút Back để quay lại MenuPage
        JButton backButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/back.png"))));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage")); // Quay lại MenuPage
        background.add(backButton);
    }

    private void createHeader(JLabel background) {
        // Header Group
        JPanel headerPanel = new RoundedPanel(10);
        headerPanel.setBounds(158, 167, 1221, 59);
        headerPanel.setBackground(new Color(0, 0, 0, 180));
        headerPanel.setLayout(null);

        // Header Labels
        String[] headers = {"Rank", "Username", "Core", "Win rate", "Ratio"};
        int[] positions = {168, 382, 707, 935, 1230};
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i], SwingConstants.CENTER);
            label.setBounds(positions[i] - 158, 0, 200, 59); // Cột tương ứng
            label.setFont(new Font("Arial", Font.BOLD, 20));
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
                data.add(line.split("\\s+")); // Tách dữ liệu theo khoảng trắng
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void updateDataPanel() {
        dataPanel.removeAll();

        int startY = 0; // Vị trí Y bắt đầu của nhóm đầu tiên
        int height = 80; // Chiều cao mỗi nhóm
        int rank = currentPage * ROWS_PER_PAGE + 1;

        for (int i = currentPage * ROWS_PER_PAGE; i < Math.min((currentPage + 1) * ROWS_PER_PAGE, rankingData.size()); i++) {
            String[] row = rankingData.get(i);

            // Panel Group
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

            // Win rate (rectangle hiển thị tỷ lệ thắng/thua)
            JPanel winRatePanel = createWinRatePanel(row[2], row[3]);
            winRatePanel.setBounds(766, (height - 30) / 2, 300, 30); // Giữa chiều cao nhóm
            groupPanel.add(winRatePanel);

            // Ratio (tính từ dữ liệu thắng/thua)
            int wins = Integer.parseInt(row[2]);
            int losses = Integer.parseInt(row[3]);
            double ratio = (double) wins / (wins + losses) * 100;
            JLabel ratioLabel = new JLabel(String.format("%.0f%%", ratio), SwingConstants.CENTER);
            ratioLabel.setBounds(1060, 0, 200, height);
            ratioLabel.setFont(new Font("Arial", Font.BOLD, 20));
            ratioLabel.setForeground(Color.WHITE);
            groupPanel.add(ratioLabel);

            // Thêm nhóm vào dataPanel
            dataPanel.add(groupPanel);

            // Cập nhật vị trí Y cho nhóm tiếp theo
            startY += height + 10; // Khoảng cách giữa các nhóm
            rank++;
        }

        // Cập nhật trạng thái của các nút Rejoin và Continue
        updateNavigationButtons();

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    private void updateNavigationButtons() {
        // Nút Rejoin
        if (currentPage > 0) {
            rejoinButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/rejon1.png"))));
            rejoinButton.setEnabled(true);
        } else {
            rejoinButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/rejon.png"))));
            rejoinButton.setEnabled(false);
        }

        // Nút Continue
        if ((currentPage + 1) * ROWS_PER_PAGE < rankingData.size()) {
            continueButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/continue1.png"))));
            continueButton.setEnabled(true);
        } else {
            continueButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/continue.png"))));
            continueButton.setEnabled(false);
        }
    }

    private JPanel createWinRatePanel(String winsStr, String lossesStr) {
        int wins = Integer.parseInt(winsStr);
        int losses = Integer.parseInt(lossesStr);
        int totalGames = wins + losses;

        // Panel chứa tỷ lệ thắng/thua
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        // Số trận thắng
        JLabel winLabel = new JLabel(wins + "W", SwingConstants.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 14));
        winLabel.setForeground(Color.WHITE);
        winLabel.setBounds(0, 0, (int) ((double) wins / totalGames * 300), 30);
        panel.add(winLabel);

        // Thanh thắng (màu xanh)
        JPanel winBar = new JPanel();
        winBar.setBackground(Color.BLUE);
        winBar.setBounds(0, 0, (int) ((double) wins / totalGames * 300), 30);
        panel.add(winBar);

        // Số trận thua
        JLabel lossLabel = new JLabel(losses + "L", SwingConstants.CENTER);
        lossLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lossLabel.setForeground(Color.WHITE);
        lossLabel.setBounds((int) ((double) wins / totalGames * 300), 0, (int) ((double) losses / totalGames * 300), 30);
        panel.add(lossLabel);

        // Thanh thua (màu đỏ)
        JPanel lossBar = new JPanel();
        lossBar.setBackground(Color.RED);
        lossBar.setBounds((int) ((double) wins / totalGames * 300), 0, (int) ((double) losses / totalGames * 300), 30);
        panel.add(lossBar);

        return panel;
    }
}
