package com.example.project.views;

import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class PlayGameWithFriend extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public PlayGameWithFriend(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);

        // Thêm Label
        JLabel label = new JLabel("<html>PlayGameWithFriend 구현 중입니다.<br> 나중에 다시 시도해주세요</html>");
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 50));
        label.setBounds(250, 300, 1300, 150);
        add(label);

        // Tạo hình chữ nhật bo góc bán trong suốt
        RoundedPanel rectanglePanel = new RoundedPanel(20); // Bo góc 20px
        rectanglePanel.setBounds(179, 150, 1200, 644); // Vị trí và kích thước của hình chữ nhật
        rectanglePanel.setBackground(new Color(255, 255, 255, 180)); // Màu đen với độ trong suốt 71%
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
        // Ví dụ: giao diện kết nối với bạn bè, v.v.
    }
}
