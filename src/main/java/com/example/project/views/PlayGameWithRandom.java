package com.example.project.views;

import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class PlayGameWithRandom extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public PlayGameWithRandom(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);

        // Hiển thị hộp thoại thông báo khi panel được tạo
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    this,
                    "PlayGameWithRandom 구현 중입니다. 나중에 다시 시도해주세요",
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Tạo hình chữ nhật bo góc bán trong suốt
        RoundedPanel rectanglePanel = new RoundedPanel(20); // Bo góc 20px
        rectanglePanel.setBounds(179, 150, 1200, 644); // Vị trí và kích thước của hình chữ nhật
        rectanglePanel.setBackground(new Color(255, 255, 255, 180)); // Màu trắng với độ trong suốt 71%
        rectanglePanel.setLayout(null); // Sử dụng layout null cho nội dung bên trong
        add(rectanglePanel); // Thêm Rectangle trước

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Nút Back để quay lại PlayPage
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "PlayPage"));
        AudioUtil.addClickSound(backButton);
        background.add(backButton);

        // Thêm các thành phần khác cho trang này
        // Ví dụ: tìm kiếm đối thủ ngẫu nhiên, v.v.
    }
}
