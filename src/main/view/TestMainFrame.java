package main.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class TestMainFrame extends JFrame {
    private JLabel label;
    private JButton button;

    public TestMainFrame() {
        setTitle("My Swing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel("초기 데이터");
        button = new JButton("클릭");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public JLabel getLabel() {
        return label;
    }

    public JButton getButton() {
        return button;
    }
}