package com.example.project.main;

import com.example.project.controllers.FileController;
import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.views.LoginPage;
import com.example.project.views.RegisterPage;
import com.example.project.views.MyPage;
import com.example.project.views.ProfilePage;
import com.example.project.views.CorrectionPage;
import com.example.project.views.PlayGameWithPC;
import com.example.project.ui.SplashScreenPanel;
import com.example.project.audio.AudioPlayer;
import com.example.project.audio.AudioManager;
import com.example.project.utils.UserManager;
import com.example.project.views.MenuPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class Main {
    //public static AudioPlayer backgroundMusicPlayer;
    //public static AudioPlayer soundEffectsPlayer;

    public static void main(String[] args) {

        AudioManager audioManager = AudioManager.getInstance();

        //backgroundMusicPlayer = new AudioPlayer("audio/BackgroundMusic.wav", true); // Looping background music
        //soundEffectsPlayer = new AudioPlayer("audio/Click.wav"); // Sound effect for clicks

        //backgroundMusicPlayer.play();
        audioManager.playBackgroundMusic();

        // 메인 JFrame 생성
        JFrame frame = new JFrame("애플리케이션");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1502, 916); // 적절한 크기 설정
        frame.setLocationRelativeTo(null);

        // CardLayout 및 mainPanel 생성
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        UserManager userManager = new UserManager();

        // 사용자 목록 초기화
        List<User> userList = new ArrayList<>();
        initializeDefaultUsers(userList); // 기본 사용자 초기화 메서드 호출
        Session.getInstance().setUserList(userList);

        // 현재 사용자 설정 (예: 목록의 첫 번째 사용자)
        User currentUser = userList.get(0);
        Session.getInstance().setCurrentUser(currentUser); // 세션에 현재 사용자 설정

        // 다양한 페이지 생성
        RegisterPage registerPage = new RegisterPage(mainPanel, cardLayout, userManager);
        LoginPage loginPage = new LoginPage(mainPanel, cardLayout, userManager);
        MyPage myPage = new MyPage(mainPanel, cardLayout, userList, userManager);
        ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, currentUser); // ProfilePage 추가
        CorrectionPage correctionPage = new CorrectionPage(mainPanel, cardLayout, userList); // CorrectionPage 추가
//        PlayGameWithPC playGameWithPC = new PlayGameWithPC(mainPanel, cardLayout); // PlayGameWithPC 추가

        // 각 페이지를 mainPanel에 고유한 이름으로 추가
        mainPanel.add(registerPage, "RegisterPage");
        mainPanel.add(loginPage, "LoginPage");
        mainPanel.add(myPage, "MyPage");
        mainPanel.add(profilePage, "ProfilePage");
        mainPanel.add(correctionPage, "CorrectionPage");
//        mainPanel.add(playGameWithPC, "PlayGameWithPC");

        // JFrame의 콘텐츠 패널을 mainPanel로 설정
        frame.setContentPane(mainPanel);

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
            private final long timerDelay = 50; // 각 단계 간 지연 (ms)

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

    // 기본 사용자를 초기화하는 메서드
    private static void initializeDefaultUsers(List<User> userList) {
        // 파일에서 사용자 추가 또는 수동으로 추가
        String filePath = System.getProperty("user.dir") + "/user.txt";

        // 외부에 user.txt 파일이 없으면 리소스에서 복사
        File externalFile = new File(filePath);
        if (!externalFile.exists()) {
            try (InputStream inputStream = Main.class.getResourceAsStream("/texts/user.txt");
                 FileOutputStream outputStream = new FileOutputStream(externalFile)) {

                if (inputStream != null) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("파일이 외부로 복사되었습니다: " + filePath);
                } else {
                    System.out.println("resources 폴더에서 user.txt 파일을 찾을 수 없습니다.");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        // 파일에서 사용자 읽기
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\s+");
                if (data.length >= 8) {
                    userList.add(new User(
                            data[0],
                            data[1],
                            data[2],
                            Integer.parseInt(data[3]),
                            data[4],
                            Integer.parseInt(data[5]),
                            Integer.parseInt(data[6]),
                            Double.parseDouble(data[7])
                    ));
                } else {
                    System.out.println("사용자 데이터가 유효하지 않습니다: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
