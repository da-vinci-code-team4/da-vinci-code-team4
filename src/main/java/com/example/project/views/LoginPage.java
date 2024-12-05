package com.example.project.views;

import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;
import com.example.project.utils.UserManager; // 추가: UserManager 가져오기

import javax.swing.*;
import java.awt.*;

/**
 * LoginPage 화면.
 */
public class LoginPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // 입력 필드
    private JTextField idField;
    private JPasswordField passwordField;

    // 사용자 관리
    private UserManager userManager; // 추가: UserManager 사용

    public LoginPage(JPanel mainPanel, CardLayout cardLayout, UserManager userManager) { // 변경: UserManager 전달
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userManager = userManager; // 추가: UserManager 설정
        setLayout(null); // 레이아웃 null 설정
        setBackground(Color.WHITE); // LoginPage 배경색

        // 배경 설정
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        background.setOpaque(false); // JLabel이 투명도를 지원하도록 설정
        add(background);

        // Login용 둥근 사각형 생성
        RoundedPanel rectanglePanel = new RoundedPanel(10); // 모서리 반경 10px
        rectanglePanel.setBounds(430, 250, 670, 350);  // 위치 및 크기 설정
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // 투명도 180의 검정색
        rectanglePanel.setLayout(null); // 내부 레이아웃 null 설정
        background.add(rectanglePanel); // Rectangle 추가

        // 그룹 간 간격
        int groupSpacing = 20;
        int groupHeight = 100; // 각 그룹 높이 (레이블 및 입력 포함)

        // --------------------- ID 그룹 ---------------------
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 42, 100, 40); // 왼쪽 정렬
        rectanglePanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(265, 30, 342, 59); // 위치 및 크기 설정
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


        // --------------------- 로그인 버튼 ---------------------
        JButton loginButton = createRoundedButton("로그인", 216, 50, 20, new Color(0xD9D9D9), Color.BLACK, new Font("맑은 고딕", Font.PLAIN, 28));
        loginButton.setBounds(178 + 50, 250, 216, 50); // 위치 및 크기 설정
        loginButton.addActionListener(e -> handleLogin());
        rectanglePanel.add(loginButton);
    }

    /**
     * 모서리가 둥근 버튼을 생성하고 투명도를 지원.
     *
     * @param text          버튼에 표시될 텍스트
     * @param width         버튼의 너비
     * @param height        버튼의 높이
     * @param cornerRadius  모서리 반경
     * @param bgColor       버튼 배경색 (투명도 포함)
     * @param fgColor       버튼 텍스트 색상
     * @param font          버튼 글꼴
     * @return JButton 설정된 속성을 포함한 버튼
     */
    private JButton createRoundedButton(String text, int width, int height, int cornerRadius, Color bgColor, Color fgColor, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // 둥근 모서리와 투명도가 있는 버튼 그리기
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
        button.setOpaque(false); // 투명도 지원을 위해 설정
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * 로그인 버튼 클릭 처리.
     * 입력 정보 확인 후 페이지 이동.
     */
    private void handleLogin() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // 입력 정보 확인
        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID와 비밀번호를 입력해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // UserManager를 사용하여 사용자 인증
        User authenticatedUser = userManager.authenticate(id, password);

        if (authenticatedUser != null) {
            // Session에 현재 사용자 설정
            Session.getInstance().setCurrentUser(authenticatedUser);

            JOptionPane.showMessageDialog(this, "로그인 성공!", "알림", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "MyPage"); // MyPage 또는 ProfilePage로 이동
        } else {
            JOptionPane.showMessageDialog(this, "ID 또는 비밀번호가 올바르지 않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
        }
    }
}
