package com.example.project.main;

import com.example.project.views.ProfilePage;
import com.example.project.views.CorrectionPage;
import com.example.project.views.RegisterPage;
import com.example.project.views.LoginPage;
import com.example.project.views.MyPage;
import com.example.project.models.User;
import com.example.project.models.Session;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<User> userList;
    private User currentUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.initialize();
        });
    }

    private void initialize() {
        // CardLayout과 메인 JPanel 생성
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 사용자 목록 초기화
        userList = new ArrayList<>();
        SwingMain.initializeDefaultUsers(userList); // 기본 사용자 초기화 메서드 호출

        // 현재 사용자 설정 (예: 사용자 목록의 첫 번째 사용자)
        currentUser = userList.get(0);
        Session.getInstance().setCurrentUser(currentUser); // Session에 현재 사용자 설정

        // JFrame 생성 및 메인 JPanel 설정
        JFrame frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setSize(1502, 916);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 초기 페이지 설정
        showPage("MyPage");
    }

    private void initializeDefaultUsers(List<User> userList) {
        // 기본 사용자 초기화 로직
        SwingMain.initializeDefaultUsers(userList);
    }

    private void showPage(String pageName) {
        // 페이지가 추가되지 않은 경우 페이지 추가
        if (!isPageAdded(pageName)) {
            addPage(pageName);
        }
        // 페이지 전환
        cardLayout.show(mainPanel, pageName);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private boolean isPageAdded(String pageName) {
        // 페이지가 이미 추가되었는지 확인
        for (Component comp : mainPanel.getComponents()) {
            if (pageName.equals(comp.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addPage(String pageName) {
        // 페이지 추가 로직
        switch (pageName) {
            case "RegisterPage":
                RegisterPage registerPage = new RegisterPage(mainPanel, cardLayout, userList);
                registerPage.setName("RegisterPage");
                mainPanel.add(registerPage, "RegisterPage");
                break;
            case "LoginPage":
                LoginPage loginPage = new LoginPage(mainPanel, cardLayout, userList);
                loginPage.setName("LoginPage");
                mainPanel.add(loginPage, "LoginPage");
                break;
            case "MyPage":
                MyPage myPage = new MyPage(mainPanel, cardLayout, userList);
                myPage.setName("MyPage");
                mainPanel.add(myPage, "MyPage");
                break;
            case "ProfilePage":
                ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, currentUser);
                profilePage.setName("ProfilePage");
                mainPanel.add(profilePage, "ProfilePage");
                break;
            case "CorrectionPage":
                CorrectionPage correctionPage = new CorrectionPage(mainPanel, cardLayout, userList);
                correctionPage.setName("CorrectionPage");
                mainPanel.add(correctionPage, "CorrectionPage");
                break;
            default:
                throw new IllegalArgumentException("Unknown page: " + pageName);
        }
    }
}