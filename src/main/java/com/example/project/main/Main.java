package com.example.project.main;

import com.example.project.models.Session; // Thêm import này
import com.example.project.models.User;
import com.example.project.views.LoginPage;
import com.example.project.views.RegisterPage;
import com.example.project.views.MyPage;
/**TO DEBUG*/
import com.example.project.views.PlayGameWithPC;
import com.example.project.views.PlayPage;
/***/
import com.example.project.views.ProfilePage;
import com.example.project.views.CorrectionPage; // Thêm import CorrectionPage
import com.example.project.ui.SplashScreenPanel; // Thêm import SplashScreenPanel

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        // Tạo JFrame chính
        JFrame frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1502, 916); // Kích thước phù hợp
        frame.setLocationRelativeTo(null);

        // Tạo CardLayout và JPanel chính
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Danh sách người dùng
        List<User> userList = new ArrayList<>();
        initializeDefaultUsers(userList); // Gọi phương thức khởi tạo người dùng mặc định

        // Giả sử chúng ta có người dùng hiện tại (currentUser)
        User currentUser = userList.get(0); // Ví dụ: người dùng đầu tiên trong danh sách
        Session.getInstance().setCurrentUser(currentUser); // Thiết lập currentUser trong Session

        // Tạo các trang khác nhau
        RegisterPage registerPage = new RegisterPage(mainPanel, cardLayout, userList);
        LoginPage loginPage = new LoginPage(mainPanel, cardLayout, userList);
        MyPage myPage = new MyPage(mainPanel, cardLayout, userList);
        ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, currentUser); // Thêm ProfilePage
        CorrectionPage correctionPage = new CorrectionPage(mainPanel, cardLayout, userList); // Thêm CorrectionPage

        // Thêm các trang vào mainPanel
        // mainPanel.add(registerPage, "RegisterPage");
        // mainPanel.add(loginPage, "LoginPage");
        // mainPanel.add(myPage, "MyPage");
        // mainPanel.add(profilePage, "ProfilePage"); // Thêm ProfilePage vào mainPanel
        // mainPanel.add(correctionPage, "CorrectionPage"); // Thêm CorrectionPage vào mainPanel


        /**
         * TO DEBUG
         */
        PlayGameWithPC playGameWithPC = new PlayGameWithPC(mainPanel, cardLayout);
        mainPanel.add(playGameWithPC, "PlayGameWithPC");
        cardLayout.show(mainPanel, "PlayGameWithPC");

        // Tạo SplashScreenPanel
        SplashScreenPanel splashPanel = new SplashScreenPanel("/img/ViewImage/Background.png");
        frame.setContentPane(splashPanel);
        frame.setVisible(true);

        // Tạo Swing Timer để thực hiện hiệu ứng fade-in và fade-out
        Timer fadeTimer = new Timer();
        fadeTimer.schedule(new TimerTask() {
            private float opacity = 0.0f;
            private boolean fadingIn = true;
            private final float fadeStep = 0.05f; // Bước tăng giảm độ trong suốt
            private final long timerDelay = 50; // Delay giữa các bước (ms)

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
                            // Chuyển từ Splash Screen sang RegisterPage
                            frame.setContentPane(mainPanel);
                            cardLayout.show(mainPanel, "RegisterPage");
                            frame.revalidate();
                            frame.repaint();
                        }
                    }
                    splashPanel.setOpacity(opacity);
                });
            }
        }, 0, 50); // Thực hiện mỗi 50ms
    }

    // Phương thức khởi tạo người dùng mặc định
    private static void initializeDefaultUsers(List<User> userList) {
        // Thêm các thông tin bổ sung cho mỗi User với constructor đầy đủ
        userList.add(new User("JiMin", "12345678", "JiMin", 25, "90W - 10L", 1200, 90, 90.0));
        userList.add(new User("YoungBin", "12345678", "YoungBin", 23, "80W - 20L", 1100, 92, 80.0));
        userList.add(new User("QuocAnh", "12345678", "QuocAnh", 26, "70W - 30L", 1000, 100, 70.0));
        userList.add(new User("HyungJoon", "12345678", "HyungJoon", 23, "85W - 15L", 1150, 96, 85.0));
        userList.add(new User("YeWon", "12345678", "YeWon", 22, "95W - 5L", 1250, 89, 95.0));
        userList.add(new User("TaeHyun", "12345678", "TaeHyun", 25, "65W - 35L", 950, 126, 65.0));
    }
}
