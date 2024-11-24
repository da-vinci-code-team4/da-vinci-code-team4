package com.example.project.views;

import com.example.project.utils.RoundedPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryPage extends JPanel {
    private int currentPage = 0; // Trang hiện tại
    private static final int ROWS_PER_PAGE = 6; // Số hàng hiển thị mỗi trang
    private List<String[]> historyData; // Dữ liệu từ history.txt
    private JPanel dataPanel;
    private JButton rejoinButton;
    private JButton continueButton;

    public HistoryPage(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0)); // Không nền

        // Cài đặt background
        JLabel background = new JLabel();
        BufferedImage bgImage = loadImage("/img/ViewImage/Background.png");
        if (bgImage != null) {
            background.setIcon(new ImageIcon(bgImage));
        } else {
            System.err.println("Background.png không được tìm thấy!");
        }
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Tạo header
        createHeader(background);

        // Đọc dữ liệu từ history.txt
        historyData = readHistoryData("src/main/resources/texts/history.txt");

        // Sắp xếp historyData theo Date từ mới nhất đến cũ nhất
        historyData.sort((a, b) -> b[0].compareTo(a[0])); // Giả định định dạng ngày là YYYY/MM/DD

        // Khởi tạo Rejoin Button (Nút Trở Về Trang Trước)
        rejoinButton = new JButton();
        BufferedImage rejoin1Img = loadImage("/img/ViewImage/rejoin4.png");
        BufferedImage rejoinImg = loadImage("/img/ViewImage/rejoin3.png");
        if (rejoin1Img != null) {
            rejoinButton.setIcon(new ImageIcon(rejoin1Img));
        } else {
            System.err.println("rejoin1.png không được tìm thấy!");
        }
        rejoinButton.setBounds(33, 450, 80, 120);
        rejoinButton.setBorderPainted(false);
        rejoinButton.setContentAreaFilled(false);
        rejoinButton.setFocusPainted(false);
        rejoinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rejoinButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateDataPanel();
            }
        });
        background.add(rejoinButton);

        // Khởi tạo Continue Button (Nút Tiếp Theo Trang)
        continueButton = new JButton();
        BufferedImage continue1Img = loadImage("/img/ViewImage/continue1.png");
        BufferedImage continueImg = loadImage("/img/ViewImage/continue.png");
        if (continue1Img != null) {
            continueButton.setIcon(new ImageIcon(continue1Img));
        } else {
            System.err.println("continue1.png không được tìm thấy!");
        }
        continueButton.setBounds(1396, 450, 80, 120);
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusPainted(false);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.addActionListener(e -> {
            if ((currentPage + 1) * ROWS_PER_PAGE < historyData.size()) {
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

        // Nút Back để quay lại MenuPage
        JButton backButton = new JButton();
        BufferedImage backImg = loadImage("/img/ViewImage/back.png");
        if (backImg != null) {
            backButton.setIcon(new ImageIcon(backImg));
        } else {
            System.err.println("back.png không được tìm thấy!");
        }
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage")); // Quay lại MenuPage
        background.add(backButton);

        // Hiển thị dữ liệu ban đầu
        updateDataPanel();
    }

    /**
     * Tạo header với các cột: Date, Results, Core, Play Time, Replay.
     *
     * @param background JLabel nền để thêm header vào.
     */
    private void createHeader(JLabel background) {
        // Header Panel
        JPanel headerPanel = new RoundedPanel(10);
        headerPanel.setBounds(158, 167, 1221, 59);
        headerPanel.setBackground(new Color(0, 0, 0, 180));
        headerPanel.setLayout(null);

        // Header Labels
        String[] headers = {"Date", "Results", "Core", "Play Time", "Replay"};
        int[] positions = {168, 382, 707, 935, 1230};
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i], SwingConstants.CENTER);
            label.setBounds(positions[i] - 158, 0, 200, 59); // Điều chỉnh vị trí theo cột
            label.setFont(new Font("Arial", Font.BOLD, 20));
            label.setForeground(Color.WHITE);
            headerPanel.add(label);
        }

        background.add(headerPanel);
    }

    /**
     * Đọc dữ liệu lịch sử từ tệp văn bản.
     *
     * @param filePath Đường dẫn tới tệp history.txt
     * @return Danh sách các dòng dữ liệu đã tách thành mảng String
     */
    private List<String[]> readHistoryData(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Tách dữ liệu theo khoảng trắng, giả định định dạng: Date Result Core PlayTime
                data.add(line.split("\\s+"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể đọc tệp history.txt.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }

    /**
     * Cập nhật dataPanel để hiển thị dữ liệu của trang hiện tại.
     */
    private void updateDataPanel() {
        dataPanel.removeAll();

        int startY = 0; // Vị trí Y bắt đầu của hàng đầu tiên
        int height = 80; // Chiều cao mỗi hàng
        int replayButtonWidth = 60;
        int replayButtonHeight = 60;

        for (int i = currentPage * ROWS_PER_PAGE; i < Math.min((currentPage + 1) * ROWS_PER_PAGE, historyData.size()); i++) {
            String[] row = historyData.get(i);

            // Mỗi dòng có: Date, Result, Core, PlayTime
            String date = row[0];
            String result = row[1];
            String coreStr = row[2];
            String playTime = row[3];

            // Tính toán Core với dấu + hoặc -
            int core = Integer.parseInt(coreStr);
            String coreDisplay = (result.equalsIgnoreCase("Victory") ? "P(+" + core + ")" : "P(-" + core + ")");

            // Panel cho mỗi hàng
            JPanel groupPanel = new RoundedPanel(10);
            groupPanel.setBounds(158, startY, 1221, height);
            groupPanel.setBackground(new Color(0, 0, 0, 180));
            groupPanel.setLayout(null);

            // Date Label
            JLabel dateLabel = new JLabel(date, SwingConstants.CENTER);
            dateLabel.setBounds(10, 0, 200, height);
            dateLabel.setFont(new Font("Arial", Font.BOLD, 20));
            dateLabel.setForeground(Color.WHITE);
            groupPanel.add(dateLabel);

            // Results Label
            JLabel resultsLabel = new JLabel(result, SwingConstants.CENTER);
            resultsLabel.setBounds(214, 0, 200, height);
            resultsLabel.setFont(new Font("Arial", Font.BOLD, 20));
            resultsLabel.setForeground(Color.WHITE);
            groupPanel.add(resultsLabel);

            // Core Label
            JLabel coreLabel = new JLabel(coreDisplay, SwingConstants.CENTER);
            coreLabel.setBounds(538, 0, 200, height);
            coreLabel.setFont(new Font("Arial", Font.BOLD, 20));
            coreLabel.setForeground(Color.WHITE);
            groupPanel.add(coreLabel);

            // Play Time Label
            JLabel playTimeLabel = new JLabel(playTime, SwingConstants.CENTER);
            playTimeLabel.setBounds(766, 0, 200, height);
            playTimeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            playTimeLabel.setForeground(Color.WHITE);
            groupPanel.add(playTimeLabel);

            // Replay Button
            JButton replayButton = new JButton();
            BufferedImage replayImg = loadImage("/img/ViewImage/replay.png");
            if (replayImg != null) {
                replayButton.setIcon(new ImageIcon(replayImg));
            } else {
                System.err.println("replay.png không được tìm thấy!");
                replayButton.setText("Replay"); // Thêm văn bản thay thế nếu hình ảnh không tìm thấy
            }
            replayButton.setBounds(1060, 10, replayButtonWidth, replayButtonHeight);
            replayButton.setBorderPainted(false);
            replayButton.setContentAreaFilled(false);
            replayButton.setFocusPainted(false);
            replayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Thêm ActionListener cho nút Replay (chức năng thêm sau)
            replayButton.addActionListener(e -> {
                // Chức năng Replay sẽ được thêm sau
                JOptionPane.showMessageDialog(this, "Chức năng Replay sẽ được thêm sau.", "Thông Tin", JOptionPane.INFORMATION_MESSAGE);
            });
            groupPanel.add(replayButton);

            // Thêm panel hàng vào dataPanel
            dataPanel.add(groupPanel);

            // Cập nhật vị trí Y cho hàng tiếp theo
            startY += height + 10; // 10px khoảng cách giữa các hàng
        }

        // Cập nhật trạng thái của các nút Rejoin và Continue
        updateNavigationButtons();

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    /**
     * Cập nhật trạng thái và biểu tượng của các nút điều hướng dựa trên trang hiện tại.
     */
    private void updateNavigationButtons() {
        // Cập nhật Rejoin Button (Trở về Trang Trước)
        if (currentPage > 0) {
            BufferedImage rejoin1Img = loadImage("/img/ViewImage/rejon4.png");
            if (rejoin1Img != null) {
                rejoinButton.setIcon(new ImageIcon(rejoin1Img));
                rejoinButton.setEnabled(true);
            } else {
                System.err.println("rejoin1.png không được tìm thấy!");
                rejoinButton.setEnabled(false);
            }
        } else {
            BufferedImage rejoinImg = loadImage("/img/ViewImage/rejoin3.png");
            if (rejoinImg != null) {
                rejoinButton.setIcon(new ImageIcon(rejoinImg));
            } else {
                System.err.println("rejoin.png không được tìm thấy!");
            }
            rejoinButton.setEnabled(false);
        }

        // Cập nhật Continue Button (Đi tới Trang Tiếp Theo)
        if ((currentPage + 1) * ROWS_PER_PAGE < historyData.size()) {
            BufferedImage continue1Img = loadImage("/img/ViewImage/continue1.png");
            if (continue1Img != null) {
                continueButton.setIcon(new ImageIcon(continue1Img));
                continueButton.setEnabled(true);
            } else {
                System.err.println("continue1.png không được tìm thấy!");
                continueButton.setEnabled(false);
            }
        } else {
            BufferedImage continueImg = loadImage("/img/ViewImage/continue.png");
            if (continueImg != null) {
                continueButton.setIcon(new ImageIcon(continueImg));
            } else {
                System.err.println("continue.png không được tìm thấy!");
            }
            continueButton.setEnabled(false);
        }
    }

    /**
     * Tải hình ảnh từ đường dẫn tài nguyên.
     *
     * @param path Đường dẫn tài nguyên hình ảnh.
     * @return BufferedImage nếu tải thành công, null nếu không tìm thấy.
     */
    private BufferedImage loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                return ImageIO.read(is);
            } else {
                System.err.println("Tài nguyên không tồn tại: " + path);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}