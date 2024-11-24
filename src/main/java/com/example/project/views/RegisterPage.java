package com.example.project.views;

import com.example.project.models.Session; // Thêm import này
import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Trang RegisterPage cho ứng dụng.
 */
public class RegisterPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Các thành phần nhập liệu
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JSpinner ageSpinner;

    // Danh sách người dùng để lưu trữ thông tin đăng ký
    private List<User> userList;

    public RegisterPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;

        setLayout(null); // Sử dụng layout null để tự định vị các thành phần
        setBackground(Color.WHITE); // Màu nền của trang RegisterPage

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Tạo hình chữ nhật bo góc cho Register
        RoundedPanel rectanglePanel = new RoundedPanel(10); // Bo góc 10px
        rectanglePanel.setBounds(430, 90, 670, 699); // Vị trí và kích thước của hình chữ nhật
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // Màu đen với độ trong suốt 71%
        rectanglePanel.setLayout(null); // Sử dụng layout null cho nội dung bên trong
        background.add(rectanglePanel); // Thêm Rectangle trước

        // Khoảng cách giữa các group
        int groupSpacing = 20;
        int groupHeight = 100; // Chiều cao mỗi group (bao gồm label và input)

        // --------------------- Group ID ---------------------
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.BOLD, 40));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 42, 100, 40); // Căn lề trái
        rectanglePanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(265, 30, 342, 59); // Vị trí và kích thước input
        idField.setBackground(new Color(0xD9D9D9));
        idField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(idField);

        // --------------------- Group Password ---------------------
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 40));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 42 + groupHeight + groupSpacing, 200, 40);
        rectanglePanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(265, 30 + groupHeight + groupSpacing, 342, 59);
        passwordField.setBackground(new Color(0xD9D9D9));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(passwordField);

        // --------------------- Group Username ---------------------
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 40));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 42 + 2 * (groupHeight + groupSpacing), 200, 40);
        rectanglePanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(265, 30 + 2 * (groupHeight + groupSpacing), 342, 59);
        usernameField.setBackground(new Color(0xD9D9D9));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(usernameField);

        // --------------------- Group Age ---------------------
        JLabel ageLabel = new JLabel("Age");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 40));
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setBounds(50, 42 + 3 * (groupHeight + groupSpacing), 100, 40);
        rectanglePanel.add(ageLabel);

        SpinnerNumberModel ageModel = new SpinnerNumberModel(18, 1, 120, 1); // Default=18, Min=1, Max=120, Step=1
        ageSpinner = new JSpinner(ageModel);
        ageSpinner.setBounds(265, 30 + 3 * (groupHeight + groupSpacing), 342, 59);
        ageSpinner.setBackground(new Color(0xD9D9D9));
        ageSpinner.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(ageSpinner);

        // --------------------- Nút Register ---------------------
        JButton registerButton = createRoundedButton("Register", 390, 70, 20, new Color(0xD9D9D9), Color.BLACK, new Font("Arial", Font.BOLD, 40));
        registerButton.setBounds(95 + 50, 500, 390, 70); // Vị trí và kích thước
        registerButton.addActionListener(e -> handleRegister());
        rectanglePanel.add(registerButton);

        // --------------------- Nút Login ---------------------
        JButton loginButton = createRoundedButton("Login", 216, 50, 20, new Color(0xD9D9D9), Color.BLACK, new Font("Arial", Font.PLAIN, 28));
        loginButton.setBounds(178 + 50, 600, 216, 50);
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "LoginPage"));
        rectanglePanel.add(loginButton);
    }

    private JButton createRoundedButton(String text, int width, int height, int cornerRadius, Color bgColor, Color fgColor, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
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

    private void handleRegister() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String username = usernameField.getText().trim();
        int age = (int) ageSpinner.getValue();

        if (id.isEmpty() || password.isEmpty() || username.isEmpty() || age <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (User user : userList) {
            if (user.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "ID đã tồn tại. Vui lòng chọn ID khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        User newUser = new User(id, password, username, age);
        userList.add(newUser);

        // Thiết lập người dùng hiện tại trong Session
        Session.getInstance().setCurrentUser(newUser);

        JOptionPane.showMessageDialog(this, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        // Chuyển sang ProfilePage sau khi đăng ký thành công
        ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, newUser);
        mainPanel.add(profilePage, "ProfilePage");
        cardLayout.show(mainPanel, "ProfilePage");
    }
}
