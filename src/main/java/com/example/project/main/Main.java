package com.example.project.main;

import com.example.project.controllers.FileController;
import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.views.LoginPage;
import com.example.project.views.RegisterPage;
import com.example.project.views.MyPage;
import com.example.project.views.ProfilePage;
import com.example.project.views.CorrectionPage;
import com.example.project.ui.SplashScreenPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        // 메인 JFrame 생성
        JFrame frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1502, 916); // 적절한 크기 설정
        frame.setLocationRelativeTo(null);

        // CardLayout과 메인 JPanel 생성
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // 사용자 목록
        List<User> userList = new ArrayList<>();
        initializeDefaultUsers(userList); // 기본 사용자 초기화 메서드 호출

        // 현재 사용자 설정 (예: 사용자 목록의 첫 번째 사용자)
        User currentUser = userList.get(0);
        Session.getInstance().setCurrentUser(currentUser); // Session에 현재 사용자 설정

        // 다양한 페이지 생성
        RegisterPage registerPage = new RegisterPage(mainPanel, cardLayout, userList);
        LoginPage loginPage = new LoginPage(mainPanel, cardLayout, userList);
        MyPage myPage = new MyPage(mainPanel, cardLayout, userList);
        ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, currentUser); // ProfilePage 추가
        CorrectionPage correctionPage = new CorrectionPage(mainPanel, cardLayout, userList); // CorrectionPage 추가

        // 페이지를 mainPanel에 추가
        // mainPanel.add(registerPage, "RegisterPage");
        // mainPanel.add(loginPage, "LoginPage");
        mainPanel.add(myPage, "MyPage");
        mainPanel.add(profilePage, "ProfilePage"); // ProfilePage를 mainPanel에 추가
        mainPanel.add(correctionPage, "CorrectionPage"); // CorrectionPage를 mainPanel에 추가

        // SplashScreenPanel 생성
        SplashScreenPanel splashPanel = new SplashScreenPanel("/img/ViewImage/Background.png");
        frame.setContentPane(splashPanel);
        frame.setVisible(true);

        // Swing Timer를 사용하여 페이드 인 및 페이드 아웃 효과 구현
        Timer fadeTimer = new Timer();
        fadeTimer.schedule(new TimerTask() {
            private float opacity = 0.0f;
            private boolean fadingIn = true;
            private final float fadeStep = 0.05f; // 투명도 증가/감소 단계
            private final long timerDelay = 50; // 각 단계 사이의 지연 시간 (ms)

            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (fadingIn) {
                        opacity += fadeStep;
                        if (opacity >= 1.0f) {
                            opacity = 1.0f;
                            fadingIn = false;
                        }
                    } else {
                        opacity -= fadeStep;
                        if (opacity <= 0.0f) {
                            opacity = 0.0f;
                            fadeTimer.cancel();
                            // Splash Screen에서 RegisterPage로 전환
                            frame.setContentPane(mainPanel);
                            cardLayout.show(mainPanel, "RegisterPage");
                            frame.revalidate();
                            frame.repaint();
                        }
                    }
                    splashPanel.setOpacity(opacity);
                });
            }
        }, 0, 50); // 50ms마다 실행
    }

    // 기본 사용자 초기화 메서드
    private static void initializeDefaultUsers(List<User> userList) {
        // 각 사용자에 대한 추가 정보와 함께 사용자 초기화
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/texts/user.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\s+");
                userList.add(new User(data[0],data[1],data[2],Integer.parseInt(data[3]), data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]))); // 나눠진 데이터를 List에 추가
            }

            FileController.setUserList(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        userList.add(new User("JiMin", "12345678", "JiMin", 25, "90W - 10L", 1200, 90, 90.0));
//        userList.add(new User("YoungBin", "12345678", "YoungBin", 23, "80W - 20L", 1100, 92, 80.0));
//        userList.add(new User("QuocAnh", "12345678", "QuocAnh", 26, "70W - 30L", 1000, 100, 70.0));
//        userList.add(new User("HyungJoon", "12345678", "HyungJoon", 23, "85W - 15L", 1150, 96, 85.0));
//        userList.add(new User("YeWon", "12345678", "YeWon", 22, "95W - 5L", 1250, 89, 95.0));
//        userList.add(new User("TaeHyun", "12345678", "TaeHyun", 25, "65W - 35L", 950, 126, 65.0));
    }
}