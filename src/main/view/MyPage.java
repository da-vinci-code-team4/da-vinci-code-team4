package main.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MyPage extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Danh sách người dùng đã đăng ký, bao gồm cả các tài khoản mặc định
    private List<User> userList = new ArrayList<>();

    public MyPage() {
        // Khởi tạo các tài khoản mặc định
        initializeDefaultUsers();

        // Cài đặt JFrame
        setTitle("Da Vinci Code");
        setSize(1502, 916);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sử dụng CardLayout để chuyển đổi giữa các trang
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        getContentPane().add(mainPanel);

        // Thêm các trang
        addPages();

        setVisible(true);
    }

    /**
     * Khởi tạo danh sách người dùng với các tài khoản mặc định.
     */
    private void initializeDefaultUsers() {
        String defaultPassword = "12345678";
        userList.add(new User("JiMin", defaultPassword, "JiMinUser", 25));
        userList.add(new User("YoungBin", defaultPassword, "YoungBinUser", 28));
        userList.add(new User("QuocAnh", defaultPassword, "QuocAnhUser", 22));
        userList.add(new User("HyungJoon", defaultPassword, "HyungJoonUser", 30));
        userList.add(new User("YeWon", defaultPassword, "YeWonUser", 27));
        userList.add(new User("TaeHyun", defaultPassword, "TaeHyunUser", 24));
    }

    private void addPages() {
        // Trang MyPage (trang chính)
        JPanel myPagePanel = createMyPage();

        // Trang MenuPage
        JPanel menuPagePanel = createMenuPage();

        // Trang PlayPage
        JPanel playPagePanel = new PlayPage(mainPanel, cardLayout);

        // Trang InfoPage
        JPanel infoPagePanel = createInfoPage();

        // Trang RegisterPage
        JPanel registerPagePanel = new RegisterPage(mainPanel, cardLayout, userList);

        // Trang LoginPage
        JPanel loginPagePanel = new LoginPage(mainPanel, cardLayout, userList);

        // Trang ProfilePage
        JPanel profilePagePanel = new ProfilePage(mainPanel, cardLayout);

        // Trang RankingPage
        JPanel rankingPagePanel = new RankingPage(mainPanel, cardLayout);
        mainPanel.add(rankingPagePanel, "RankingPage");

        // Trang HistoryPage
        JPanel historyPagePanel = new HistoryPage(mainPanel, cardLayout);

        // Trang CorrectionPage
        JPanel correctionPagePanel = new CorrectionPage(mainPanel, cardLayout);

        // Thêm các trang vào CardLayout
        mainPanel.add(myPagePanel, "MyPage");
        mainPanel.add(menuPagePanel, "MenuPage");
        mainPanel.add(playPagePanel, "PlayPage");
        mainPanel.add(infoPagePanel, "InfoPage");
        mainPanel.add(registerPagePanel, "RegisterPage");
        mainPanel.add(loginPagePanel, "LoginPage");
        mainPanel.add(profilePagePanel, "ProfilePage");
        mainPanel.add(rankingPagePanel, "RankingPage");
        mainPanel.add(historyPagePanel, "HistoryPage");
        mainPanel.add(correctionPagePanel, "CorrectionPage");
    }

    /**
     * Phương thức tạo trang MyPage.
     */
    private JPanel createMyPage() {
        JPanel myPagePanel = new JPanel(null); // Sử dụng layout null
        myPagePanel.setBackground(Color.WHITE);

        // Cài đặt background
        URL backgroundUrl = getClass().getResource("../resources/img/Background.png");
        // if (backgroundUrl == null) {
        //     throw new IllegalArgumentException("Background image not found");
        // }
        JLabel background = new JLabel(new ImageIcon(backgroundUrl));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        myPagePanel.add(background);

        // Group các button dọc bên trái
        int buttonSize = 100; // Kích thước button hình vuông
        int buttonSpacing = 35; // Khoảng cách giữa các button
        int leftMargin = 50; // Khoảng cách từ viền trái

        // Tính toán vị trí để căn giữa theo chiều dọc
        int totalHeight = 3 * buttonSize + 2 * buttonSpacing; // Chiều cao của tất cả các button và khoảng cách
        int startY = (916 - totalHeight) / 2; // Vị trí Y bắt đầu để căn giữa

        // Button Menu
        JButton menuButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/menu_button.png"))));
        menuButton.setBounds(leftMargin, startY, buttonSize, buttonSize);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage"));
        background.add(menuButton);

        // Button Setting
        JButton settingButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/setting_button.png"))));
        settingButton.setBounds(leftMargin, startY + buttonSize + buttonSpacing, buttonSize, buttonSize);
        settingButton.setBorderPainted(false);
        settingButton.setContentAreaFilled(false);
        settingButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingButton.addActionListener(e -> System.out.println("Setting Button Clicked"));
        background.add(settingButton);

        // Button Info
        JButton infoButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/info_button.png"))));
        infoButton.setBounds(leftMargin, startY + 2 * (buttonSize + buttonSpacing), buttonSize, buttonSize);
        infoButton.setBorderPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Chuyển trang sang InfoPage khi nhấn nút Info
        infoButton.addActionListener(e -> cardLayout.show(mainPanel, "InfoPage"));
        background.add(infoButton);

        // Button Play
        ImageIcon playIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/play.png")));
        JButton playButton = new JButton(playIcon);

        // Lấy kích thước của ảnh
        int playButtonWidth = playIcon.getIconWidth();
        int playButtonHeight = playIcon.getIconHeight();
        int playButtonX = 1502 - playButtonWidth - 20; // Cách mép phải 20px
        int playButtonY = 916 - playButtonHeight - 20; // Cách mép dưới 20px

        playButton.setBounds(playButtonX, playButtonY, playButtonWidth, playButtonHeight);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false); // Vô hiệu hóa trạng thái focus
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Chuyển trang sang PlayPage khi nhấn nút Play
        playButton.addActionListener(e -> cardLayout.show(mainPanel, "PlayPage"));
        background.add(playButton);

        return myPagePanel;
    }

    /**
     * Phương thức tạo trang MenuPage.
     */
    private JPanel createMenuPage() {
        JPanel menuPagePanel = new JPanel(null); // Layout null để tự định vị các nút
        menuPagePanel.setBackground(new Color(255, 255, 255, 0));

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/background.png"))));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        menuPagePanel.add(background);

        // Nút Profile
        JButton profileButton = createMenuButton("Profile", 265, 235);
        profileButton.addActionListener(e -> cardLayout.show(mainPanel, "ProfilePage"));
        background.add(profileButton);

        // Nút Ranking
        JButton rankingButton = createMenuButton("Ranking", 265 + 482, 235);
        rankingButton.addActionListener(e -> cardLayout.show(mainPanel, "RankingPage"));
        background.add(rankingButton);

        // Nút History
        JButton historyButton = createMenuButton("History", 265, 235 + 239);
        historyButton.addActionListener(e -> cardLayout.show(mainPanel, "HistoryPage"));
        background.add(historyButton);

        // Nút Correction
        JButton correctionButton = createMenuButton("Correction", 265 + 482, 235 + 239);
        correctionButton.addActionListener(e -> cardLayout.show(mainPanel, "CorrectionPage"));
        background.add(correctionButton);

        // Nút Log Out
        JButton registerButton = createMenuButton("Log Out", 560, 668);
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "RegisterPage"));
        background.add(registerButton);

        // Nút Back
        JButton backButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/back.png"))));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);

        return menuPagePanel;
    }

    /**
     * Phương thức tạo trang InfoPage.
     */
    private JPanel createInfoPage() {
        JPanel infoPagePanel = new JPanel(null); // Sử dụng layout null
        infoPagePanel.setBackground(new Color(0, 0, 0, 0)); // Không nền

        // Cài đặt background
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/Background.png"))));
        background.setBounds(0, 0, 1502, 916);
        background.setLayout(null);
        infoPagePanel.add(background);

        // Tạo hình chữ nhật bo góc bán trong suốt
        RoundedPanel rectanglePanel = new RoundedPanel(20); // Bo góc 20px
        rectanglePanel.setBounds(179, 150, 1200, 644); // Vị trí và kích thước của hình chữ nhật
        rectanglePanel.setBackground(new Color(0, 0, 0, 180)); // Màu đen với độ trong suốt 71%
        rectanglePanel.setLayout(null); // Sử dụng layout null cho nội dung bên trong
        background.add(rectanglePanel); // Thêm Rectangle trước

        // Văn bản chính
        JLabel mainText = new JLabel("경기대학교 24년도 객체프로그래밍 금123 4조");
        mainText.setFont(new Font("Arial", Font.BOLD, 64));
        mainText.setForeground(Color.WHITE);
        mainText.setHorizontalAlignment(SwingConstants.CENTER);
        mainText.setBounds(0, 250, 1200, 97); // Căn giữa trong rectanglePanel
        rectanglePanel.add(mainText);

        // Văn bản phiên bản
        JLabel versionText = new JLabel("version 0.0.0.1");
        versionText.setFont(new Font("Arial", Font.ITALIC, 42));
        versionText.setForeground(Color.WHITE);
        versionText.setHorizontalAlignment(SwingConstants.CENTER);
        versionText.setBounds(0, 360, 1200, 40); // Bên dưới mainText
        rectanglePanel.add(versionText);

        // Nút Back
        JButton backButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/back.png"))));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MyPage"));
        background.add(backButton);

        return infoPagePanel;
    }

    /**
     * Phương thức tạo nút Menu.
     *
     * @param text Văn bản trên nút
     * @param x    Vị trí X
     * @param y    Vị trí Y
     * @return JButton với các thuộc tính đã thiết lập
     */
    private JButton createMenuButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 400, 200); // Kích thước nút
        button.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/img/background_button.png"))));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.BOLD, 48));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void main() {
        // Đảm bảo rằng Swing hoạt động trên Event Dispatch Thread
        SwingUtilities.invokeLater(MyPage::new);
    }
}
