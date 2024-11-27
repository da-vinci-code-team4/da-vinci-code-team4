package com.example.project.views;

import com.example.project.models.Session; // Session import 추가
import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 애플리케이션의 회원가입(RegisterPage) 화면.
 */
public class RegisterPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // 입력 필드 구성 요소
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JSpinner ageSpinner;

    // 사용자 리스트, 등록 정보를 저장하기 위해 사용
    private List<User> userList;

    public RegisterPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;

        setLayout(null); // 컴포넌트의 위치를 직접 설정하기 위해 null 레이아웃 사용
        setBackground(Color.WHITE); // RegisterPage의 배경 색상 설정

        // 배경 이미지 설정
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        add(background);

        // 둥근 모서리의 패널 생성
        RoundedPanel rectanglePanel = new RoundedPanel(10); // 모서리 반지름 10px
        rectanglePanel.setBounds(430, 90, 670, 699); // 위치 및 크기
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // 반투명 검정 배경
        rectanglePanel.setLayout(null); // 내부 컴포넌트의 자유로운 배치
        background.add(rectanglePanel);

        // 각 그룹 간 간격과 높이 설정
        int groupSpacing = 20;
        int groupHeight = 100; // 그룹의 높이 (레이블과 입력 필드 포함)

        // --------------------- ID 입력 그룹 ---------------------
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.BOLD, 40));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 42, 100, 40); // 왼쪽 정렬
        rectanglePanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(265, 30, 342, 59); // 위치 및 크기
        idField.setBackground(new Color(0xD9D9D9));
        idField.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(idField);

        // --------------------- 비밀번호 입력 그룹 ---------------------
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

        // --------------------- 사용자 이름 입력 그룹 ---------------------
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

        // --------------------- 나이 입력 그룹 ---------------------
        JLabel ageLabel = new JLabel("Age");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 40));
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setBounds(50, 42 + 3 * (groupHeight + groupSpacing), 100, 40);
        rectanglePanel.add(ageLabel);

        SpinnerNumberModel ageModel = new SpinnerNumberModel(18, 1, 120, 1); // 기본값=18, 최소값=1, 최대값=120, 증가 단위=1
        ageSpinner = new JSpinner(ageModel);
        ageSpinner.setBounds(265, 30 + 3 * (groupHeight + groupSpacing), 342, 59);
        ageSpinner.setBackground(new Color(0xD9D9D9));
        ageSpinner.setFont(new Font("Arial", Font.PLAIN, 24));
        rectanglePanel.add(ageSpinner);

        // --------------------- 회원가입 버튼 ---------------------
        JButton registerButton = createRoundedButton("Register", 390, 70, 20, new Color(0xD9D9D9), Color.BLACK, new Font("Arial", Font.BOLD, 40));
        registerButton.setBounds(95 + 50, 500, 390, 70); // 위치 및 크기
        registerButton.addActionListener(e -> handleRegister());
        rectanglePanel.add(registerButton);

        // --------------------- 로그인 버튼 ---------------------
        JButton loginButton = createRoundedButton("Login", 216, 50, 20, new Color(0xD9D9D9), Color.BLACK, new Font("Arial", Font.PLAIN, 28));
        loginButton.setBounds(178 + 50, 600, 216, 50);
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "LoginPage"));
        rectanglePanel.add(loginButton);
    }

    // 둥근 버튼을 생성하는 메서드
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
                // 테두리 그리지 않음
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

    // 회원가입 처리를 위한 메서드
    private void handleRegister() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String username = usernameField.getText().trim();
        int age = (int) ageSpinner.getValue();

        // 필드가 비어 있는 경우 경고 메시지 표시
        if (id.isEmpty() || password.isEmpty() || username.isEmpty() || age <= 0) {
            JOptionPane.showMessageDialog(this, "모든 정보를 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 중복 ID 체크
        for (User user : userList) {
            if (user.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "이미 존재하는 ID입니다. 다른 ID를 선택해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 새로운 사용자 생성 및 등록
        User newUser = new User(id, password, username, age);
        userList.add(newUser);

        // Session에 현재 사용자 설정
        Session.getInstance().setCurrentUser(newUser);

        JOptionPane.showMessageDialog(this, "회원가입 성공!", "알림", JOptionPane.INFORMATION_MESSAGE);

        // 회원가입 성공 후 ProfilePage로 이동
        ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, newUser);
        mainPanel.add(profilePage, "ProfilePage");
        cardLayout.show(mainPanel, "ProfilePage");
    }
}