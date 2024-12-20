/*package com.example.project.views;

import com.example.project.controller.FileController;
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
//        historyData = readHistoryData("src/main/resources/texts/history.txt");
        historyData = FileController.getHistory();

        // Sắp xếp historyData theo Date từ mới nhất đến cũ nhất
        historyData.sort((a, b) -> b[0].compareTo(a[0])); // Giả định định dạng ngày là YYYY/MM/DD

        // Khởi tạo Rejoin Button (Nút Trở Về Trang Trước)
        rejoinButton = new JButton();
        BufferedImage rejoin1Img = loadImage("/img/ViewImage/rejon1.png");
        BufferedImage rejoinImg = loadImage("/img/ViewImage/rejon.png");
        if (rejoin1Img != null) {
            rejoinButton.setIcon(new ImageIcon(rejoin1Img));
        } else {
            System.err.println("rejoin4.png không được tìm thấy!");
            rejoinButton.setText("Rejoin"); // Thêm văn bản thay thế
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
            continueButton.setText("Continue"); // Thêm văn bản thay thế
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
            backButton.setText("Back"); // Thêm văn bản thay thế nếu hình ảnh không tìm thấy
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
     */ /*
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
            label.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
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
//    private List<String[]> readHistoryData(String filePath) {
//        List<String[]> data = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Tách dữ liệu theo khoảng trắng, giả định định dạng: Date Result Core PlayTime
//                data.add(line.split("\\s+"));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Không thể đọc tệp history.txt.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//        return data;
//    }

/**
 * Cập nhật dataPanel để hiển thị dữ liệu của trang hiện tại.
 */
    /*
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
     */ /*
    private void updateNavigationButtons() {
        // Cập nhật Rejoin Button (Trở về Trang Trước)
        if (currentPage > 0) {
            BufferedImage rejoin1Img = loadImage("/img/ViewImage/rejon1.png"); // Đã sửa lỗi chính tả
            if (rejoin1Img != null) {
                rejoinButton.setIcon(new ImageIcon(rejoin1Img));
                rejoinButton.setEnabled(true);
            } else {
                System.err.println("rejoin4.png không được tìm thấy!");
                rejoinButton.setEnabled(false);
            }
        } else {
            BufferedImage rejoinImg = loadImage("/img/ViewImage/rejon.png");
            if (rejoinImg != null) {
                rejoinButton.setIcon(new ImageIcon(rejoinImg));
            } else {
                System.err.println("rejoin3.png không được tìm thấy!");
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
    }  */ /*

    /**
     * Tải hình ảnh từ đường dẫn tài nguyên.
     *
     * @param path Đường dẫn tài nguyên hình ảnh.
     * @return BufferedImage nếu tải thành công, null nếu không tìm thấy.
     */ /*
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
        */

package com.example.project.views;

import com.example.project.main.Main;
import com.example.project.models.User;
import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections; // Collections 클래스 import 추가
import java.util.List;

public class HistoryPage extends JPanel {

    private static final int ROWS_PER_PAGE = 6; // 페이지당 표시되는 행 수
    private static List<String[]> historyData; // history.txt에서 가져온 데이터 저장
    private int currentPage = 0; // 현재 페이지
    private JPanel dataPanel;
    private JButton rejoinButton;
    private JButton continueButton;

    public HistoryPage(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0)); // 투명 배경 설정

        // 배경 설정
        JLabel background = new JLabel();
        BufferedImage bgImage = loadImage("/img/ViewImage/Background.png");
        if (bgImage != null) {
            background.setIcon(new ImageIcon(bgImage));
        } else {
            System.err.println("Background.png 파일을 찾을 수 없습니다!");
        }
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // 헤더 생성
        createHeader(background);

        // history.txt에서 데이터 읽기
        loadHistoryData();

        // 최신 데이터를 위로 정렬하기 위해 리스트를 뒤집음
        Collections.reverse(historyData);

        // 날짜와 시간별로 정렬하려면 주석 해제
        /*
        historyData.sort((a, b) -> {
            // 날짜 비교
            int dateCompare = b[0].compareTo(a[0]);
            if (dateCompare != 0) {
                return dateCompare;
            }
            // 시간이 동일한 경우 시간 비교
            return b[3].compareTo(a[3]); // a[3]과 b[3]은 "HH:mm" 형식으로 가정
        });
        */

        // "이전 페이지" 버튼 생성
        rejoinButton = new JButton();
        BufferedImage rejoin1Img = loadImage("/img/ViewImage/rejon1.png");
        BufferedImage rejoinImg = loadImage("/img/ViewImage/rejon.png");
        if (rejoin1Img != null) {
            rejoinButton.setIcon(new ImageIcon(rejoin1Img));
        } else {
            System.err.println("rejon.png 파일을 찾을 수 없습니다!");
            rejoinButton.setText("Rejoin"); // 이미지가 없을 경우 텍스트 표시
        }
        rejoinButton.setBounds(33, 450, 80, 120);
        rejoinButton.setBorderPainted(false);
        rejoinButton.setContentAreaFilled(false);
        rejoinButton.setFocusPainted(false);
        rejoinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(rejoinButton);
        rejoinButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateDataPanel();
            }
        });
        background.add(rejoinButton);

        // "다음 페이지" 버튼 생성
        continueButton = new JButton();
        BufferedImage continue1Img = loadImage("/img/ViewImage/continue1.png");
        BufferedImage continueImg = loadImage("/img/ViewImage/continue.png");
        if (continue1Img != null) {
            continueButton.setIcon(new ImageIcon(continue1Img));
        } else {
            System.err.println("continue1.png 파일을 찾을 수 없습니다!");
            continueButton.setText("Continue"); // 이미지가 없을 경우 텍스트 표시
        }
        continueButton.setBounds(1396, 450, 80, 120);
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusPainted(false);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(continueButton);
        continueButton.addActionListener(e -> {
            if ((currentPage + 1) * ROWS_PER_PAGE < historyData.size()) {
                currentPage++;
                updateDataPanel();
            }
        });
        background.add(continueButton);

        // 데이터 패널 생성
        dataPanel = new JPanel(null);
        dataPanel.setBounds(0, 246, 1502, 600);
        dataPanel.setOpaque(false);
        background.add(dataPanel);

        // "메뉴로 돌아가기" 버튼 생성
        JButton backButton = new JButton();
        BufferedImage backImg = loadImage("/img/ViewImage/back.png");
        if (backImg != null) {
            backButton.setIcon(new ImageIcon(backImg));
        } else {
            System.err.println("back.png 파일을 찾을 수 없습니다!");
            backButton.setText("Back"); // 이미지가 없을 경우 텍스트 표시
        }
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AudioUtil.addClickSound(backButton);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage")); // MenuPage로 이동
        background.add(backButton);

        // 초기 데이터 표시
        updateDataPanel();
    }

    /**
     * history.txt 파일에서 데이터를 읽고 historyData에 저장
     */
    private void loadHistoryData() {
        historyData = new ArrayList<>();
        String filePath = System.getProperty("user.dir") + "/history.txt";
        File historyFile = new File(filePath);
        if (!historyFile.exists()) {
            System.err.println("history.txt 파일이 존재하지 않습니다: " + filePath);
            JOptionPane.showMessageDialog(this, "history.txt 파일이 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 4) {
                    historyData.add(parts);
                } else {
                    System.err.println("유효하지 않은 데이터 라인: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "history.txt 파일을 읽을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 새 항목 추가 및 history.txt 파일에 기록
     */
    public static void updateHistory(String result, int score, int time) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String[] data = new String[4];
        data[0] = today.format(formatter);
        data[1] = result;
        data[2] = Integer.toString(score);
        data[3] = String.format("%02d:%02d", time / 60, time % 60);

        // 리스트 맨 앞에 추가
        historyData.add(0, data);

        // history.txt 파일에 기록
        String filePath = System.getProperty("user.dir") + "/history.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String[] lineData : historyData) {
                String line = String.join(" ", lineData);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "history.txt 파일에 기록할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 헤더를 생성하여 날짜, 결과, 점수, 플레이 시간, 리플레이 열 표시
     */
    private void createHeader(JLabel background) {
        JPanel headerPanel = new RoundedPanel(10);
        headerPanel.setBounds(158, 167, 1221, 59);
        headerPanel.setBackground(new Color(0, 0, 0, 180));
        headerPanel.setLayout(null);

        String[] headers = {"날짜", "결과", "점수", "플레이 시간", "리플레이"};
        int[] positions = {168, 382, 707, 935, 1230};
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i], SwingConstants.CENTER);
            label.setBounds(positions[i] - 158, 0, 200, 59);
            label.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
            label.setForeground(Color.WHITE);
            headerPanel.add(label);
        }

        background.add(headerPanel);
    }

    /**
     * 현재 페이지 데이터를 표시하도록 dataPanel 업데이트
     */
    private void updateDataPanel() {
        dataPanel.removeAll();

        int startY = 0;
        int height = 80;

        for (int i = currentPage * ROWS_PER_PAGE;
             i < Math.min((currentPage + 1) * ROWS_PER_PAGE, historyData.size()); i++) {
            String[] row = historyData.get(i);

            String date = row[0];
            String result = row[1];
            String coreStr = row[2];
            String playTime = row[3];

            int core = Integer.parseInt(coreStr);
            String coreDisplay = core + "P";

            JPanel groupPanel = new RoundedPanel(10);
            groupPanel.setBounds(158, startY, 1221, height);
            groupPanel.setBackground(new Color(0, 0, 0, 180));
            groupPanel.setLayout(null);

            JLabel dateLabel = new JLabel(date, SwingConstants.CENTER);
            dateLabel.setBounds(10, 0, 200, height);
            dateLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
            dateLabel.setForeground(Color.WHITE);
            groupPanel.add(dateLabel);

            JLabel resultsLabel = new JLabel(result, SwingConstants.CENTER);
            resultsLabel.setBounds(214, 0, 200, height);
            resultsLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
            resultsLabel.setForeground(Color.WHITE);
            groupPanel.add(resultsLabel);

            JLabel coreLabel = new JLabel(coreDisplay, SwingConstants.CENTER);
            coreLabel.setBounds(538, 0, 200, height);
            coreLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
            coreLabel.setForeground(Color.WHITE);
            groupPanel.add(coreLabel);

            JLabel playTimeLabel = new JLabel(playTime, SwingConstants.CENTER);
            playTimeLabel.setBounds(766, 0, 200, height);
            playTimeLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
            playTimeLabel.setForeground(Color.WHITE);
            groupPanel.add(playTimeLabel);

            JButton replayButton = new JButton();
            BufferedImage replayImg = loadImage("/img/ViewImage/replay.png");
            if (replayImg != null) {
                replayButton.setIcon(new ImageIcon(replayImg));
            } else {
                System.err.println("replay.png 파일을 찾을 수 없습니다!");
                replayButton.setText("Replay");
            }
            replayButton.setBounds(1060, 10, 60, 60);
            replayButton.setBorderPainted(false);
            replayButton.setContentAreaFilled(false);
            replayButton.setFocusPainted(false);
            replayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            AudioUtil.addClickSound(replayButton);
            replayButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Replay 기능은 나중에 추가될 예정입니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
            });
            groupPanel.add(replayButton);

            dataPanel.add(groupPanel);

            startY += height + 10;
        }

        updateNavigationButtons();

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    /**
     * 이전 및 다음 버튼 상태와 아이콘 업데이트
     */
    private void updateNavigationButtons() {
        if (currentPage > 0) {
            BufferedImage rejoin1Img = loadImage("/img/ViewImage/rejon1.png");
            if (rejoin1Img != null) {
                rejoinButton.setIcon(new ImageIcon(rejoin1Img));
                rejoinButton.setEnabled(true);
            } else {
                System.err.println("rejon1.png 파일을 찾을 수 없습니다!");
                rejoinButton.setEnabled(false);
            }
        } else {
            BufferedImage rejoinImg = loadImage("/img/ViewImage/rejon.png");
            if (rejoinImg != null) {
                rejoinButton.setIcon(new ImageIcon(rejoinImg));
            } else {
                System.err.println("rejon.png 파일을 찾을 수 없습니다!");
            }
            rejoinButton.setEnabled(false);
        }

        if ((currentPage + 1) * ROWS_PER_PAGE < historyData.size()) {
            BufferedImage continue1Img = loadImage("/img/ViewImage/continue1.png");
            if (continue1Img != null) {
                continueButton.setIcon(new ImageIcon(continue1Img));
                continueButton.setEnabled(true);
            } else {
                System.err.println("continue1.png 파일을 찾을 수 없습니다!");
                continueButton.setEnabled(false);
            }
        } else {
            BufferedImage continueImg = loadImage("/img/ViewImage/continue.png");
            if (continueImg != null) {
                continueButton.setIcon(new ImageIcon(continueImg));
            } else {
                System.err.println("continue.png 파일을 찾을 수 없습니다!");
            }
            continueButton.setEnabled(false);
        }
    }

    /**
     * 리소스 경로에서 이미지를 로드
     *
     * @param path 리소스 이미지 경로
     * @return 성공 시 BufferedImage, 실패 시 null
     */
    private BufferedImage loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                return ImageIO.read(is);
            } else {
                System.err.println("리소스를 찾을 수 없습니다: " + path);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 외부에서 historyData를 설정하기 위한 메소드
     *
     * @param data 외부 데이터 리스트
     */
    public static void setHistoryData(List<String[]> data) {
        historyData = data;
    }

}