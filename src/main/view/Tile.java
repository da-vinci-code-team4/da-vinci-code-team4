package view;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    private int number; // Số từ 0 đến 11 hoặc -1 cho Joker
    private String symbol; // Ký tự, ví dụ: "-"
    private Color color; // Màu sắc: Đen hoặc Trắng
    private boolean isRevealed; // Trạng thái được mở hay chưa

    public Tile(int number, String symbol, Color color) {
        this.number = number;
        this.symbol = symbol;
        this.color = color;
        this.isRevealed = false;
        setPreferredSize(new Dimension(80, 120));
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Hiển thị thông tin tile
        JLabel infoLabel = new JLabel(getDisplayText(), SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setForeground(Color.WHITE);
        add(infoLabel);
    }

    // Phương thức để lấy văn bản hiển thị dựa trên trạng thái
    public String getDisplayText() {
        if (isRevealed) {
            if (number == -1) {
                return "J"; // Joker
            }
            return number + symbol;
        } else {
            return "???";
        }
    }

    // Getter và Setter
    public int getNumber() {
        return number;
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getTileColor() {
        return color;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        this.isRevealed = true;
        removeAll();
        JLabel infoLabel = new JLabel(getDisplayText(), SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setForeground(Color.WHITE);
        add(infoLabel);
        revalidate();
        repaint();
    }
}
