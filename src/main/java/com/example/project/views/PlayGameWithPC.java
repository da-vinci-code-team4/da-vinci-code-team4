package com.example.project.views;

import com.example.project.controllers.Controller;
import com.example.project.controllers.GameObserver;
import com.example.project.controllers.GamePhase;
import com.example.project.controllers.GameState;
import com.example.project.config.Tile;
import com.example.project.config.TileType;
import com.example.project.models.Computer;
import com.example.project.models.GameUser;
import com.example.project.models.Player;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayGameWithPC.java
 *
 * Giao diện người dùng cho trò chơi khi chơi với máy tính.
 * Xử lý các thành phần giao diện, tương tác người dùng và tích hợp với logic trò chơi.
 */
public class PlayGameWithPC extends JPanel implements GameObserver {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JLabel timeLabel;
    private Timer swingTimer;
    private int seconds = 0;

    private Controller controller;
    private GameState gameState;
    private GameUser userPlayer;
    private Computer computerPlayer;

    private JPanel centralPanel;
    private JPanel userPanel;
    private JPanel computerPanel;

    private List<JButton> userCardSlots;
    private List<JButton> computerCardSlots;

    private JLabel remainingTilesLabel;
    private JLabel opponentMatchedTilesLabel;
    private JLabel opponentTilesMatchedLabel;
    private JLabel opponentRemainingTilesLabel;
    private JLabel myRemainingTilesLabel;

    private JLabel exitButton;

    // Danh sách các nút trong khu vực trung tâm
    private List<JButton> centralCardSlots;

    // Các chỉ số thẻ bài được chọn trong giai đoạn chọn ban đầu
    private List<Integer> selectedTileIndices;

    // Nút xác nhận
    private JButton confirmButton;

    public PlayGameWithPC(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        setBackground(Color.WHITE);

        // Khởi tạo bộ điều khiển trò chơi
        controller = new Controller(this);
        gameState = controller.getGameState();
        userPlayer = controller.getUser();
        computerPlayer = controller.getComputer();

        // Khởi tạo danh sách centralCardSlots
        centralCardSlots = new ArrayList<>();
        selectedTileIndices = new ArrayList<>();

        // Khởi tạo các thành phần giao diện
        initializeUIComponents();

        // Bắt đầu trò chơi sau khi delay 0,75s
        Timer initialDelayTimer = new Timer(750, e -> startGame());
        initialDelayTimer.setRepeats(false);
        initialDelayTimer.start();
    }

    /**
     * Khởi tạo tất cả các thành phần giao diện người dùng.
     */
    private void initializeUIComponents() {
        // Panel nội dung chính
        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(Color.WHITE);
        mainContent.setBounds(0, 0, 1502, 916);
        add(mainContent);

        // Panel hiển thị thời gian
        RoundedPanel timePanel = new RoundedPanel(new FlowLayout(), new Color(0xD9D9D9), 20);
        timePanel.setBounds(1058, 13, 244, 70);
        mainContent.add(timePanel);

        timeLabel = new JLabel("00 : 00");
        timeLabel.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK); // Màu chữ
        timePanel.add(timeLabel);

        // Khởi tạo Swing Timer để cập nhật đồng hồ mỗi giây
        swingTimer = new Timer(1000, e -> {
            seconds++;
            updateClock(seconds);
        });
        swingTimer.start();

        // Nút thoát
        URL exitImageUrl = getClass().getResource("/img/ViewImage/back.png");
        ImageIcon exitIcon;
        if (exitImageUrl != null) {
            exitIcon = new ImageIcon(exitImageUrl);
        } else {
            exitIcon = new ImageIcon(); // Biểu tượng rỗng hoặc placeholder
        }
        exitButton = new JLabel(exitIcon);
        exitButton.setBounds(1396, 13, 70, 70);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                swingTimer.stop(); // Dừng đồng hồ khi thoát
                // Quay lại trang chơi
                cardLayout.show(mainPanel, "PlayPage");
            }
        });
        mainContent.add(exitButton);

        // Hình ảnh và nhãn của máy tính
        URL pcImageUrl = getClass().getResource("/img/ViewImage/pc2.png");
        ImageIcon pcIconImage;
        if (pcImageUrl != null) {
            pcIconImage = new ImageIcon(pcImageUrl);
        } else {
            pcIconImage = new ImageIcon(); // Biểu tượng rỗng hoặc placeholder
        }
        JLabel pcIcon = new JLabel(pcIconImage);
        pcIcon.setBounds(45, 68, 139, 179);
        pcIcon.setHorizontalAlignment(SwingConstants.CENTER);
        pcIcon.setVerticalAlignment(SwingConstants.TOP);
        mainContent.add(pcIcon);

        JLabel pcText = new JLabel("PC");
        pcText.setBounds(45, 196, 139, 50);
        pcText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        pcText.setHorizontalAlignment(SwingConstants.CENTER);
        mainContent.add(pcText);

        // Hình ảnh và nhãn của người chơi
        URL meImageUrl = getClass().getResource("/img/ViewImage/me.png");
        ImageIcon meIconImage;
        if (meImageUrl != null) {
            meIconImage = new ImageIcon(meImageUrl);
        } else {
            meIconImage = new ImageIcon(); // Biểu tượng rỗng hoặc placeholder
        }
        JLabel meIcon = new JLabel(meIconImage);
        meIcon.setBounds(1332, 654, 139, 186);
        meIcon.setHorizontalAlignment(SwingConstants.CENTER);
        meIcon.setVerticalAlignment(SwingConstants.TOP);
        mainContent.add(meIcon);

        JLabel meText = new JLabel("Me");
        meText.setBounds(1342, 815, 139, 50); // Chỉnh lại vị trí nếu cần
        meText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        meText.setHorizontalAlignment(SwingConstants.CENTER);
        mainContent.add(meText);

        // Khu vực trung tâm
        centralPanel = new JPanel(new GridLayout(2, 12, 5, 5));
        centralPanel.setBounds(98, 308, 1313, 261);
        centralPanel.setBackground(Color.WHITE);
        mainContent.add(centralPanel);

        // Khởi tạo danh sách các ô thẻ bài ở trung tâm
        initializeCentralTiles();

        // Khu vực của người chơi
        userPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        userPanel.setBounds(673, 617, 650, 261);
        userPanel.setBackground(Color.WHITE);
        mainContent.add(userPanel);

        // Khu vực của máy tính
        computerPanel = new RoundedPanel(new GridLayout(2, 6, 10, 10), new Color(0xFFFFFF), 20);
        computerPanel.setBounds(210, 16, 650, 261); // 81 - 65 = 16
        computerPanel.setBackground(Color.WHITE);
        mainContent.add(computerPanel);

        // Khởi tạo danh sách các ô thẻ bài của người chơi
        userCardSlots = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            JButton slotButton = createRoundedButton();
            slotButton.setBackground(Color.BLUE);
            slotButton.setFocusable(false);
            userPanel.add(slotButton);
            userCardSlots.add(slotButton);
        }

        // Khởi tạo danh sách các ô thẻ bài của máy tính
        computerCardSlots = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            JButton slotButton = createRoundedButton();
            slotButton.setBackground(new Color(0x007B75)); // Màu xanh lá ban đầu
            slotButton.setFocusable(false);
            computerPanel.add(slotButton);
            computerCardSlots.add(slotButton);
        }

        // Game Info Panel (Thông tin trò chơi)
        RoundedPanel gameInfoPanel = new RoundedPanel(null, new Color(0xD9D9D9), 20);
        gameInfoPanel.setBounds(78, 614, 571, 264);
        gameInfoPanel.setLayout(null);
        mainContent.add(gameInfoPanel);

        // Sub-panels for opponent and user info (Thông tin đối thủ và người chơi)
        JPanel leftInfoPanel = new JPanel(null);
        leftInfoPanel.setBounds(10, 10, 275, 244);
        leftInfoPanel.setOpaque(false);
        gameInfoPanel.add(leftInfoPanel);

        JPanel rightInfoPanel = new JPanel(null);
        rightInfoPanel.setBounds(286, 10, 275, 244);
        rightInfoPanel.setOpaque(false);
        gameInfoPanel.add(rightInfoPanel);

        // Opponent Info Labels (Thông tin đối thủ)
        opponentMatchedTilesLabel = new JLabel("맞춘 상대 타일 수 : 0");
        opponentMatchedTilesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        opponentMatchedTilesLabel.setForeground(Color.BLACK);
        opponentMatchedTilesLabel.setBounds(20, 30, 235, 30);
        leftInfoPanel.add(opponentMatchedTilesLabel);

        opponentTilesMatchedLabel = new JLabel("상대가 맞춘 타일 수 : 0");
        opponentTilesMatchedLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        opponentTilesMatchedLabel.setForeground(Color.BLACK);
        opponentTilesMatchedLabel.setBounds(20, 80, 235, 30);
        leftInfoPanel.add(opponentTilesMatchedLabel);

        opponentRemainingTilesLabel = new JLabel("남은 상대 타일 수 : 0");
        opponentRemainingTilesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        opponentRemainingTilesLabel.setForeground(Color.BLACK);
        opponentRemainingTilesLabel.setBounds(20, 130, 235, 30);
        leftInfoPanel.add(opponentRemainingTilesLabel);

        // User Info Labels (Thông tin người chơi)
        remainingTilesLabel = new JLabel("중앙 타일 남은 수 : 0");
        remainingTilesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        remainingTilesLabel.setForeground(Color.BLACK);
        remainingTilesLabel.setBounds(20, 30, 235, 30);
        rightInfoPanel.add(remainingTilesLabel);

        myRemainingTilesLabel = new JLabel("남은 내 타일 수 : 0");
        myRemainingTilesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        myRemainingTilesLabel.setForeground(Color.BLACK);
        myRemainingTilesLabel.setBounds(20, 80, 235, 30);
        rightInfoPanel.add(myRemainingTilesLabel);

        // Nút xác nhận
        confirmButton = new JButton("확인");
        confirmButton.setBounds(700, 580, 100, 40); // Điều chỉnh vị trí cho phù hợp
        confirmButton.setVisible(false); // Ban đầu ẩn
        mainContent.add(confirmButton);

        confirmButton.addActionListener(e -> {
            GamePhase phase = controller.getCurrentPhase();
            if (phase == GamePhase.INITIAL_SELECTION) {
                controller.confirmInitialSelection();
                confirmButton.setVisible(false);
            }
        });

        // Cập nhật giao diện ban đầu
        updateCentralPanel();
        updateUserPanel();
        updateComputerPanel();
        updateGameInfo();
    }

    /**
     * Khởi tạo 24 thẻ bài ở khu vực trung tâm (12 trắng và 12 đen) và xáo trộn chúng.
     */
    private void initializeCentralTiles() {
        List<Tile> initialTiles = controller.getTileManager().getCentralTiles();

        // Tạo 24 nút cho khu vực trung tâm
        for (int i = 0; i < 24; i++) {
            JButton tileButton = createRoundedButton();
            Tile tile = initialTiles.get(i);
            tileButton.setBackground(tile.getTileColor()); // Thiết lập màu nền theo loại thẻ bài
            centralCardSlots.add(tileButton);
            centralPanel.add(tileButton);
        }
    }

    /**
     * Tạo một JButton với góc bo tròn.
     *
     * @return JButton đã được tùy chỉnh.
     */
    private JButton createRoundedButton() {
        JButton button = new RoundedButton(20);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    /**
     * Bắt đầu trò chơi.
     */
    private void startGame() {
        controller.startGame();

        // Hiển thị thông báo sau khi các khu vực đã được hiển thị
        JOptionPane.showMessageDialog(this, "가져올 타일을 선택하세요(4개)");
    }

    /**
     * Cập nhật đồng hồ hiển thị thời gian.
     *
     * @param seconds Tổng số giây đã trôi qua.
     */
    private void updateClock(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String timeString = String.format("%02d : %02d", minutes, secs);
        timeLabel.setText(timeString);
    }

    /**
     * Cập nhật khu vực trung tâm.
     */
    private void updateCentralPanel() {
        List<Tile> centralTiles = gameState.getCentralTiles();
        GamePhase phase = controller.getCurrentPhase();

        for (int i = 0; i < centralCardSlots.size(); i++) {
            JButton tileButton = centralCardSlots.get(i);
            if (i >= centralTiles.size()) {
                // Nếu không có thẻ bài tại vị trí này, làm trống ô
                tileButton.setIcon(null);
                tileButton.setEnabled(false);
                tileButton.setBackground(Color.LIGHT_GRAY);
                continue;
            }
            Tile tile = centralTiles.get(i);

            if (tile.isOpened()) {
                // Nếu thẻ bài đã được mở, đánh dấu là ô trống
                tileButton.setIcon(null);
                tileButton.setEnabled(false);
                tileButton.setBackground(Color.GRAY);
            } else if (tile.isSelected()) {
                // Nếu thẻ bài được chọn, thay đổi màu sắc
                tileButton.setBackground(Color.YELLOW);
                tileButton.setEnabled(true);
            } else {
                // Thẻ bài có sẵn
                tileButton.setBackground(tile.getTileColor());
                tileButton.setIcon(null);
                tileButton.setEnabled(true);
            }

            final int index = i; // Lưu vị trí của thẻ bài

            // Loại bỏ các ActionListener cũ để tránh bị trùng
            for (ActionListener al : tileButton.getActionListeners()) {
                tileButton.removeActionListener(al);
            }

            // Thêm ActionListener dựa trên giai đoạn trò chơi
            if (phase == GamePhase.INITIAL_SELECTION) {
                tileButton.addActionListener(e -> {
                    controller.playerSelectInitialTile(index);
                    updateCentralPanel();
                    updateGameInfo();
                    // Hiển thị nút xác nhận nếu đã chọn đủ 4 thẻ
                    int selectedCount = 0;
                    for (Tile t : centralTiles) {
                        if (t.isSelected()) {
                            selectedCount++;
                        }
                    }
                    if (selectedCount == 4) {
                        confirmButton.setVisible(true);
                    } else {
                        confirmButton.setVisible(false);
                    }
                });
            } else if (phase == GamePhase.PLAYER_DRAW_PHASE) {
                tileButton.addActionListener(e -> {
                    controller.playerDrawTile(index);
                    updateCentralPanel();
                    updateUserPanel();
                    updateGameInfo();
                });
            } else {
                tileButton.setEnabled(false);
            }
        }

        centralPanel.revalidate();
        centralPanel.repaint();
    }

    /**
     * Cập nhật khu vực của người chơi.
     */
    private void updateUserPanel() {
        List<Tile> userTiles = userPlayer.getTiles();

        for (int i = 0; i < userCardSlots.size(); i++) {
            JButton slotButton = userCardSlots.get(i);
            if (i < userTiles.size()) {
                Tile tile = userTiles.get(i);
                if (tile.isGuessedCorrectly()) {
                    // Nếu đã đoán đúng, hiển thị hình ảnh mặt trước
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else if (tile.isOpened()) {
                    // Nếu thẻ bài đã được mở, hiển thị hình ảnh mặt trước
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else {
                    // Hiển thị mặt sau của thẻ bài hoặc ô trống
                    slotButton.setBackground(Color.BLUE);
                    slotButton.setIcon(null);
                }
            } else {
                // Ô trống
                slotButton.setBackground(Color.BLUE);
                slotButton.setIcon(null);
            }
        }

        userPanel.revalidate();
        userPanel.repaint();
    }

    /**
     * Cập nhật khu vực của máy tính.
     */
    private void updateComputerPanel() {
        List<Tile> computerTiles = computerPlayer.getTiles();

        for (int i = 0; i < computerCardSlots.size(); i++) {
            JButton slotButton = computerCardSlots.get(i);
            if (i < computerTiles.size()) {
                Tile tile = computerTiles.get(i);
                if (tile.isGuessedCorrectly()) {
                    // Nếu đã đoán đúng, hiển thị hình ảnh mặt trước
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else {
                    // Hiển thị mặt sau của thẻ bài
                    slotButton.setBackground(Color.GREEN);
                    slotButton.setIcon(null);
                }

                final int index = i; // Lưu vị trí

                // Loại bỏ các ActionListener cũ
                for (ActionListener al : slotButton.getActionListeners()) {
                    slotButton.removeActionListener(al);
                }

                // Thêm ActionListener trong giai đoạn người chơi đoán
                if (controller.getCurrentPhase() == GamePhase.PLAYER_GUESS_PHASE && !tile.isGuessedCorrectly()) {
                    slotButton.addActionListener(e -> {
                        controller.playerGuessComputerTile(index);
                        updateComputerPanel();
                        updateGameInfo();
                        updateCentralPanel();
                        updateUserPanel();
                    });
                } else {
                    slotButton.setEnabled(false);
                }
            } else {
                slotButton.setBackground(new Color(0x007B75));
                slotButton.setIcon(null);
                slotButton.setEnabled(false);
            }
        }

        computerPanel.revalidate();
        computerPanel.repaint();
    }

    /**
     * Kiểm tra điều kiện kết thúc trò chơi.
     */
    private void checkGameOver() {
        controller.checkGameOver();
    }

    /**
     * Cập nhật thông tin trò chơi.
     */
    private void updateGameInfo() {
        remainingTilesLabel.setText("중앙 타일 남은 수 : " + controller.getTileManager().getDeckSize());
        opponentMatchedTilesLabel.setText("맞춘 상대 타일 수 : " + userPlayer.getScore());
        opponentTilesMatchedLabel.setText("상대가 맞춘 타일 수 : " + computerPlayer.getScore());
        opponentRemainingTilesLabel.setText("남은 상대 타일 수 : " + getUnopenedTilesCount(computerPlayer));
        myRemainingTilesLabel.setText("남은 내 타일 수 : " + getUnopenedTilesCount(userPlayer));
    }

    /**
     * Lấy số lượng thẻ bài chưa mở của một người chơi.
     *
     * @param player Người chơi cần kiểm tra.
     * @return Số lượng thẻ bài chưa mở.
     */
    private int getUnopenedTilesCount(Player player) {
        int count = 0;
        for (Tile tile : player.getTiles()) {
            if (!tile.isOpened() && !tile.isGuessedCorrectly()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onGameStateChanged(GameState state) {
        this.gameState = state;
        updateCentralPanel();
        updateUserPanel();
        updateComputerPanel();
        updateGameInfo();

        if (gameState.isGameOver()) {
            checkGameOver();
        }
    }

    /**
     * Lớp JButton với góc bo tròn.
     */
    private class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(int radius) {
            super();
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(getBackground().darker());
            } else {
                g.setColor(getBackground());
            }
            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        }

        @Override
        public boolean contains(int x, int y) {
            Shape shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
            return shape.contains(x, y);
        }
    }
}
