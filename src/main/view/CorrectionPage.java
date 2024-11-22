package view;

import javax.swing.*;
import java.awt.*;

public class CorrectionPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public CorrectionPage(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        setBackground(Color.WHITE);

        // Cài đặt background hoặc các thành phần khác
        JLabel label = new JLabel("Trang Correction");
        label.setFont(new Font("Arial", Font.BOLD, 50));
        label.setBounds(500, 300, 500, 100);
        add(label);

        // Nút Back
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage"));
        add(backButton);
    }
}
