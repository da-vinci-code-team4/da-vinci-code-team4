package com.example.project.views;

import com.example.project.utils.RoundedPanel;
import com.example.project.ui.CustomSliderUI; // Import lớp CustomSliderUI

import javax.swing.*;
import java.awt.*;

public class AudioSettingPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AudioSettingPage(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        setBackground(Color.WHITE);

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Thêm hình chữ nhật chứa ảnh tab.png và dòng chữ "Audio Setting"
        addAudioSettingHeader(background);

        // Thêm nhóm điều chỉnh âm lượng nhạc nền
        addVolumeControlGroup(background, 130, 305, "Background Music");

        // Thêm nhóm điều chỉnh âm lượng hiệu ứng âm thanh (cách nhóm trên 50px)
        addVolumeControlGroup(background, 130, 305 + 140 + 50, "Sound Effects");

        // Nút Back để quay lại MyPage
        addBackButton(background);
    }

    /**
     * Thêm phần header chứa ảnh tab.png và dòng chữ "Audio Setting".
     *
     * @param background JLabel nền để thêm thành phần vào.
     */
    private void addAudioSettingHeader(JLabel background) {
        // Tạo panel chứa ảnh tab.png
        JLabel tabLabel = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/tab.png")));
        tabLabel.setBounds(503, 120, 505, 161);
        tabLabel.setLayout(null);
        background.add(tabLabel);

        // Thêm dòng chữ "Audio Setting" vào trong tabLabel
        JLabel titleLabel = new JLabel("Audio Setting", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 505, 161);
        tabLabel.add(titleLabel);
    }

    /**
     * Thêm một nhóm điều khiển âm lượng gồm thanh trượt và nhãn với ảnh tab2.png.
     *
     * @param background JLabel nền để thêm thành phần vào.
     * @param x          Vị trí X của nhóm.
     * @param y          Vị trí Y của nhóm.
     * @param labelText  Văn bản cho nhãn bên phải.
     */
    private void addVolumeControlGroup(JLabel background, int x, int y, String labelText) {
        // Tạo panel cho nhóm điều khiển âm lượng
        JPanel groupPanel = new RoundedPanel(15);
        groupPanel.setBounds(x, y, 1200, 140);
        groupPanel.setBackground(new Color(0, 0, 0, 150)); // Màu nền với độ trong suốt
        groupPanel.setLayout(null);
        background.add(groupPanel);

        // Thêm thanh trượt (JSlider) bên trái
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setBounds(20, 55, 800, 50);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        // Áp dụng CustomSliderUI cho thanh trượt
        volumeSlider.setUI(new CustomSliderUI(volumeSlider));

        groupPanel.add(volumeSlider);

        // Thêm nhãn với ảnh tab2.png và dòng chữ bên phải
        JLabel labelPanel = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/tab2.png")));
        labelPanel.setBounds(840, 30, 330, 100);
        labelPanel.setLayout(new BorderLayout());
        groupPanel.add(labelPanel);

        // Thêm dòng chữ vào labelPanel
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        labelPanel.add(label, BorderLayout.CENTER);

        // Thêm ChangeListener cho thanh trượt để xử lý thay đổi âm lượng
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            // Xử lý thay đổi âm lượng ở đây
            System.out.println(labelText + " Volume: " + volume);
        });
    }

    /**
     * Thêm nút Back để quay lại MyPage.
     *
     * @param background JLabel nền để thêm nút vào.
     */
    private void addBackButton(JLabel background) {
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);
    }
}
