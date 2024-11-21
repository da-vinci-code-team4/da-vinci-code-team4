package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Trang LoginPage cho ứng dụng.
 */
public class LoginPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Các thành phần nhập liệu
    private JTextField idField;
    private JPasswordField passwordField;

    // Danh sách người dùng để kiểm tra đăng nhập
    private List<User> userList;

    public LoginPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;
        setLayout(null); // Sử dụng layout null để tự định vị các thành phần
        setBackground(Color.WHITE); // Màu nền của trang LoginPage

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Tạo hình chữ nhật bo góc cho Login
        RoundedPanel rectanglePanel = new RoundedPanel(10); // Bo góc 10px
        rectanglePanel.setBounds(430, 250, 670, 350);  // Vị trí và kích thước của hình chữ nhật
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // Màu đen với độ trong suốt 71%
        rectanglePanel.setLayout(null); // Sử dụng layout null cho nội dung bên trong
        background.add(rectanglePanel); // Thêm Rectangle trước

        // Khoảng cách giữa các group
        int groupSpacing = 20;
        int groupHeight = 100; // Chiều cao mỗi group (bao gồm label và input)

        // --------------------- Group ID ---------------------
        // Label ID
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.BOLD, 40));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 42, 100, 40); // Căn lề trái
        rectanglePanel.add(idLabel);

        // Text Field ID
        idField = new JTextField();
        idField.setBounds(265, 30, 342, 59); // Vị trí và kích thước input
        idField.setBackground(new Color(0xD9D9D9));
        idField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(idField);

        // --------------------- Group Password ---------------------
        // Label Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 40));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 42 + groupHeight + groupSpacing, 200, 40); // Căn lề trái
        rectanglePanel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(265, 30 + groupHeight + groupSpacing, 342, 59);
        passwordField.setBackground(new Color(0xD9D9D9));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(passwordField);


        // --------------------- Nút Login ---------------------
        JButton loginButton = createRoundedButton("Login", 216, 50, 20, new Color(0xD9D9D9), Color.BLACK, new Font("Arial", Font.PLAIN, 28));
        loginButton.setBounds(178 + 50, 280, 216, 50); // Vị trí và kích thước
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        rectanglePanel.add(loginButton);
    }

    /**
     * Tạo một nút có góc bo tròn.
     *
     * @param text          Văn bản trên nút
     * @param width         Chiều rộng của nút
     * @param height        Chiều cao của nút
     * @param cornerRadius  Bán kính bo góc
     * @param bgColor       Màu nền của nút
     * @param fgColor       Màu chữ của nút
     * @param font          Font chữ của nút
     * @return JButton với các thuộc tính đã thiết lập
     */
    private JButton createRoundedButton(String text, int width, int height, int cornerRadius, Color bgColor, Color fgColor, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Vẽ nút với góc bo tròn
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Không vẽ viền
            }
        };
        button.setFont(font);
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Xử lý khi nhấn nút Login.
     * Kiểm tra thông tin nhập và chuyển trang nếu hợp lệ.
     */
    private void handleLogin() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Kiểm tra thông tin nhập đầy đủ
        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ ID và Password.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra thông tin đăng nhập
        boolean isAuthenticated = false;
        for (User user : userList) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                isAuthenticated = true;
                break;
            }
        }

        if (isAuthenticated) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "MyPage");
        } else {
            JOptionPane.showMessageDialog(this, "ID hoặc Password không đúng.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}
