package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Các thành phần nhập liệu
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JSpinner ageSpinner;

    public RegisterPage(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null); // Sử dụng layout null để tự định vị các thành phần
        setBackground(Color.WHITE); // Màu nền của trang RegisterPage

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/background.png")));
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
        // Label ID
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.BOLD, 40));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 42, 100, 40); // Căn lề trái
        rectanglePanel.add(idLabel);

        // Text Field ID
        idField = new JTextField();
        idField.setBounds(265, 30, 292 + 50, 59); // Vị trí và kích thước input
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
        passwordField.setBounds(265, 30 + groupHeight + groupSpacing, 292+50, 59);
        passwordField.setBackground(new Color(0xD9D9D9));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(passwordField);

        // --------------------- Group Username ---------------------
        // Label Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 40));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 42 + 2 * (groupHeight + groupSpacing), 200, 40); // Căn lề trái
        rectanglePanel.add(usernameLabel);

        // Text Field Username
        usernameField = new JTextField();
        usernameField.setBounds(265, 30 + 2 * (groupHeight + groupSpacing), 292+50, 59);
        usernameField.setBackground(new Color(0xD9D9D9));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(usernameField);

        // --------------------- Group Age ---------------------
        // Label Age
        JLabel ageLabel = new JLabel("Age");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 40));
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setBounds(50, 42 + 3 * (groupHeight + groupSpacing), 100, 40); // Căn lề trái
        rectanglePanel.add(ageLabel);

        // Spinner Age
        SpinnerNumberModel ageModel = new SpinnerNumberModel(18, 1, 120, 1); // Default=18, Min=1, Max=120, Step=1
        ageSpinner = new JSpinner(ageModel);
        ageSpinner.setBounds(265, 30 + 3 * (groupHeight + groupSpacing), 292+50, 59);
        ageSpinner.setBackground(new Color(0xD9D9D9));
        ageSpinner.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(ageSpinner);

        // --------------------- Nút Register ---------------------
        JButton registerButton = createRoundedButton("Register", 390, 70, 20, new Color(0xD9D9D9), Color.BLACK, new Font("Arial", Font.BOLD, 40));
        registerButton.setBounds(95+50, 500, 390, 70); // Vị trí và kích thước
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        rectanglePanel.add(registerButton);

        // --------------------- Nút Login ---------------------
        JButton loginButton = createRoundedButton("Login", 216, 50, 20, new Color(0xD9D9D9), Color.BLACK, new Font("Arial", Font.PLAIN, 28));
        loginButton.setBounds(178+50, 580, 216, 50); // Vị trí và kích thước
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chuyển về MyPage hoặc trang Login nếu có
                cardLayout.show(mainPanel, "MyPage");
            }
        });
        rectanglePanel.add(loginButton);

        // --------------------- Nút Back ---------------------
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);
    }

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
     * Xử lý khi nhấn nút Register.
     * Kiểm tra thông tin nhập và chuyển trang nếu hợp lệ.
     */
    private void handleRegister() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String username = usernameField.getText().trim();
        int age = (int) ageSpinner.getValue();

        // Kiểm tra thông tin nhập đầy đủ
        if (id.isEmpty() || password.isEmpty() || username.isEmpty() || age <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lưu thông tin đăng ký
        // Ở đây bạn có thể thêm mã để lưu thông tin vào cơ sở dữ liệu hoặc một nơi lưu trữ nào đó
        // Ví dụ: In thông tin ra console
        System.out.println("Đăng ký thành công!");
        System.out.println("ID: " + id);
        System.out.println("Password: " + password);
        System.out.println("Username: " + username);
        System.out.println("Age: " + age);

        // Chuyển trang về MyPage
        cardLayout.show(mainPanel, "MyPage");
    }
}
