package com.example.project.views;

import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;
import com.example.project.utils.UserManager;

import javax.swing.*;
import java.awt.*;

/**
 * RegisterPage 화면.
 */
public class RegisterPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // 입력 필드
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JSpinner ageSpinner;

    // 사용자 관리
    private UserManager userManager;

    public RegisterPage(JPanel mainPanel, CardLayout cardLayout, UserManager userManager) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userManager = userManager;

        setLayout(null); // 레이아웃 null 설정
        setBackground(Color.WHITE); // RegisterPage 배경색

        // 배경 설정
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // Register용 둥근 사각형 패널 생성
        RoundedPanel rectanglePanel = new RoundedPanel(10); // 모서리 반경 10px
        rectanglePanel.setBounds(430, 90, 670, 699); // 위치 및 크기
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // 투명도 180의 검정색
        rectanglePanel.setLayout(null); // 내부 레이아웃 null
        background.add(rectanglePanel);

        // 그룹 간 간격
        int groupSpacing = 20;
        int groupHeight = 100; // 그룹 높이 (레이블 및 입력 포함)

        // --------------------- ID 그룹 ---------------------
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 42, 100, 40); // 왼쪽 정렬
        rectanglePanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(265, 30, 342, 59); // 위치 및 크기
        idField.setBackground(new Color(0xD9D9D9));
        idField.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        rectanglePanel.add(idField);

        // --------------------- 비밀번호 그룹 ---------------------
        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 42 + groupHeight + groupSpacing, 200, 40); // 왼쪽 정렬
        rectanglePanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(265, 30 + groupHeight + groupSpacing, 342, 59);
        passwordField.setBackground(new Color(0xD9D9D9));
        passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        rectanglePanel.add(passwordField);

        // --------------------- 사용자 이름 그룹 ---------------------
        JLabel usernameLabel = new JLabel("사용자 이름");
        usernameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 37));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 42 + 2 * (groupHeight + groupSpacing), 200, 40);
        rectanglePanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(265, 30 + 2 * (groupHeight + groupSpacing), 342, 59);
        usernameField.setBackground(new Color(0xD9D9D9));
        usernameField.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        rectanglePanel.add(usernameField);

        // --------------------- 나이 그룹 ---------------------
        JLabel ageLabel = new JLabel("나이");
        ageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setBounds(50, 42 + 3 * (groupHeight + groupSpacing), 100, 40);
        rectanglePanel.add(ageLabel);

        SpinnerNumberModel ageModel = new SpinnerNumberModel(18, 1, 120, 1); // 기본값=18, min=1, max=120, step=1
        ageSpinner = new JSpinner(ageModel);
        ageSpinner.setBounds(265, 30 + 3 * (groupHeight + groupSpacing), 342, 59);
        ageSpinner.setBackground(new Color(0xD9D9D9));
        ageSpinner.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        rectanglePanel.add(ageSpinner);

        // --------------------- Register 버튼 ---------------------
        JButton registerButton = createRoundedButton("회원가입", 390, 70, 20, new Color(0xD9D9D9), Color.BLACK, new Font("맑은 고딕", Font.BOLD, 40));
        registerButton.setBounds(95 + 50, 500, 390, 70); // 위치 및 크기
        registerButton.addActionListener(e -> handleRegister());
        rectanglePanel.add(registerButton);

        // --------------------- 로그인 버튼 ---------------------
        JButton loginButton = createRoundedButton("로그인", 216, 50, 20, new Color(0xD9D9D9), Color.BLACK, new Font("맑은 고딕", Font.PLAIN, 28));
        loginButton.setBounds(178 + 50, 600, 216, 50);
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "LoginPage"));
        rectanglePanel.add(loginButton);
    }

    // 둥근 버튼 생성
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
                // 테두리 비활성화
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

    // 회원가입 처리
    private void handleRegister() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String username = usernameField.getText().trim();
        int age = (int) ageSpinner.getValue();

        // 입력 정보 확인
        if (id.isEmpty() || password.isEmpty() || username.isEmpty() || age <= 0) {
            JOptionPane.showMessageDialog(this, "모든 정보를 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // UserManager를 사용하여 새 사용자 추가
        User newUser = new User(id, password, username, age);
        boolean isAdded = userManager.addUser(newUser);

        if (!isAdded) {
            JOptionPane.showMessageDialog(this, "ID가 이미 존재합니다. 다른 ID를 선택해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Session에 현재 사용자 설정
        Session.getInstance().setCurrentUser(newUser);

        JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!", "알림", JOptionPane.INFORMATION_MESSAGE);

        // ProfilePage로 이동
        ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, newUser);
        mainPanel.add(profilePage, "ProfilePage");
        cardLayout.show(mainPanel, "ProfilePage");
    }
}
