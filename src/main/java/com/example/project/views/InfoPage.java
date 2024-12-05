package com.example.project.views;

import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class InfoPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public InfoPage(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(null); // Sử dụng layout null
        setBackground(new Color(0, 0, 0, 0)); // Không nền

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Tạo hình chữ nhật bo góc bán trong suốt
        RoundedPanel rectanglePanel = new RoundedPanel(20); // Bo góc 20px
        rectanglePanel.setBounds(179, 150, 1200, 644); // Vị trí và kích thước của hình chữ nhật
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // Màu đen với độ trong suốt 71%
        rectanglePanel.setLayout(null); // Sử dụng layout null cho nội dung bên trong
        background.add(rectanglePanel); // Thêm Rectangle trước

        // Văn bản chính
        JLabel mainText = new JLabel("경기대학교 24년도 객체프로그래밍 금123 4조");
        mainText.setFont(new Font("맑은 고딕", Font.BOLD, 55));
        mainText.setForeground(Color.WHITE);
        mainText.setHorizontalAlignment(SwingConstants.CENTER);
        mainText.setBounds(0, 250, 1200, 97); // Căn giữa trong rectanglePanel
        rectanglePanel.add(mainText);

        // Văn bản phiên bản
        JLabel versionText = new JLabel("version 0.0.0.1");
        versionText.setFont(new Font("맑은 고딕", Font.ITALIC, 42));
        versionText.setForeground(Color.WHITE);
        versionText.setHorizontalAlignment(SwingConstants.CENTER);
        versionText.setBounds(0, 360, 1200, 40); // Bên dưới mainText
        rectanglePanel.add(versionText);

        // Nút Back
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        AudioUtil.addClickSound(backButton);
        background.add(backButton);
    }
}
