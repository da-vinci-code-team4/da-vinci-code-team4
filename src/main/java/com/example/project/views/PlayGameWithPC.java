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
 * 컴퓨터와 플레이할 때 게임의 사용자 인터페이스입니다.
 * UI 구성 요소, 사용자 상호 작용을 처리하고 게임 로직과 통합합니다.
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

    // 중앙 영역의 버튼 목록
    private List<JButton> centralCardSlots;

    // 초기 선택 단계에서 선택된 카드의 인덱스
    private List<Integer> selectedTileIndices;

    // 확인 버튼
    private JButton confirmButton;

    public PlayGameWithPC(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        setBackground(Color.WHITE);

        // 게임 컨트롤러 초기화
        controller = new Controller(this);
        gameState = controller.getGameState();
        userPlayer = controller.getUser();
        computerPlayer = controller.getComputer();

        // centralCardSlots 리스트 초기화
        centralCardSlots = new ArrayList<>();
        selectedTileIndices = new ArrayList<>();

        // UI 구성 요소 초기화
        initializeUIComponents();

        // 0.75초 지연 후 게임 시작
        Timer initialDelayTimer = new Timer(750, e -> startGame());
        initialDelayTimer.setRepeats(false);
        initialDelayTimer.start();
    }

    /**
     * 모든 UI 구성 요소 초기화.
     */
    private void initializeUIComponents() {
        // 메인 콘텐츠 패널
        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(Color.WHITE);
        mainContent.setBounds(0, 0, 1502, 916);
        add(mainContent);

        // 시간 표시 패널
        RoundedPanel timePanel = new RoundedPanel(new FlowLayout(), new Color(0xD9D9D9), 20);
        timePanel.setBounds(1058, 13, 244, 70);
        mainContent.add(timePanel);

        timeLabel = new JLabel("00 : 00");
        timeLabel.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK); // 글자 색상
        timePanel.add(timeLabel);

        // 매초 시계를 업데이트하기 위한 Swing Timer 초기화
        swingTimer = new Timer(1000, e -> {
            seconds++;
            updateClock(seconds);
        });
        swingTimer.start();

        // 확인 대화상자가 있는 종료 버튼 (JButton 사용)
        URL exitImageUrl = getClass().getResource("/img/ViewImage/back.png");
        ImageIcon exitIcon;
        if (exitImageUrl != null) {
            exitIcon = new ImageIcon(exitImageUrl);
        } else {
            exitIcon = new ImageIcon(); // 비어 있는 아이콘 또는 플레이스홀더
        }
        JButton exitButton = new JButton(exitIcon);
        exitButton.setBounds(1384, 30, 60, 60); // 새 크기에 맞게 위치 조정
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 확인 대화상자 표시
                int result = JOptionPane.showConfirmDialog(
                        PlayGameWithPC.this, // 대화상자의 부모는 현재 패널
                        "게임 진행 중 나가시겠습니까?", // 메시지
                        "확인", // 대화상자 제목
                        JOptionPane.YES_NO_OPTION, // Yes와 No 옵션
                        JOptionPane.QUESTION_MESSAGE // 질문 아이콘
                );

                if (result == JOptionPane.YES_OPTION) {
                    // 사용자가 "Yes"를 선택한 경우
                    swingTimer.stop(); // 나갈 때 타이머 정지
                    // 종료 작업 수행: 예를 들어, 메인 화면으로 돌아가거나 애플리케이션 종료
                    cardLayout.show(mainPanel, "MyPage");
                } else {
                    // 사용자가 "No"를 선택한 경우 - 아무 작업도 하지 않음
                    // 필요하면 다른 로직을 추가할 수 있음
                }
            }
        });
        mainContent.add(exitButton);

        // 컴퓨터의 이미지와 레이블
        URL pcImageUrl = getClass().getResource("/img/ViewImage/pc2.png");
        ImageIcon pcIconImage;
        if (pcImageUrl != null) {
            pcIconImage = new ImageIcon(pcImageUrl);
        } else {
            pcIconImage = new ImageIcon(); // 빈 아이콘 또는 플레이스홀더
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

        // 플레이어의 이미지와 레이블
        URL meImageUrl = getClass().getResource("/img/ViewImage/me.png");
        ImageIcon meIconImage;
        if (meImageUrl != null) {
            meIconImage = new ImageIcon(meImageUrl);
        } else {
            meIconImage = new ImageIcon(); // 빈 아이콘 또는 플레이스홀더
        }
        JLabel meIcon = new JLabel(meIconImage);
        meIcon.setBounds(1332, 654, 139, 186);
        meIcon.setHorizontalAlignment(SwingConstants.CENTER);
        meIcon.setVerticalAlignment(SwingConstants.TOP);
        mainContent.add(meIcon);

        JLabel meText = new JLabel("Me");
        meText.setBounds(1342, 815, 139, 50); // 필요하면 위치 조정
        meText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        meText.setHorizontalAlignment(SwingConstants.CENTER);
        mainContent.add(meText);

        // 중앙 영역
        centralPanel = new JPanel(new GridLayout(2, 12, 5, 5));
        centralPanel.setBounds(98, 308, 1313, 261);
        centralPanel.setBackground(Color.WHITE);
        mainContent.add(centralPanel);

        // 중앙의 카드 슬롯 목록 초기화
        initializeCentralTiles();

        // 플레이어 영역
        userPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        userPanel.setBounds(673, 617, 650, 261);
        userPanel.setBackground(Color.WHITE);
        mainContent.add(userPanel);

        // 컴퓨터 영역
        computerPanel = new RoundedPanel(new GridLayout(2, 6, 10, 10), new Color(0xFFFFFF), 20);
        computerPanel.setBounds(210, 16, 650, 261);
        computerPanel.setBackground(Color.WHITE);
        mainContent.add(computerPanel);

        // 플레이어의 카드 슬롯 목록 초기화
        userCardSlots = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            JButton slotButton = createRoundedButton();
            slotButton.setBackground(Color.BLUE);
            slotButton.setFocusable(false);
            userPanel.add(slotButton);
            userCardSlots.add(slotButton);
        }

        // 컴퓨터의 카드 슬롯 목록 초기화
        computerCardSlots = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            JButton slotButton = createRoundedButton();
            slotButton.setBackground(new Color(0x007B75)); // 원래의 녹색
            slotButton.setFocusable(false);
            computerPanel.add(slotButton);
            computerCardSlots.add(slotButton);
        }

        // 게임 정보 패널
        RoundedPanel gameInfoPanel = new RoundedPanel(null, new Color(255,255,255), 20);
        gameInfoPanel.setBounds(78, 614, 571, 264);
        gameInfoPanel.setLayout(null);
        mainContent.add(gameInfoPanel);

        // 상대 및 사용자 정보를 위한 서브 패널
        JPanel leftInfoPanel = new JPanel(null);
        leftInfoPanel.setBounds(10, 10, 275, 244);
        leftInfoPanel.setOpaque(false);
        gameInfoPanel.add(leftInfoPanel);

        JPanel rightInfoPanel = new JPanel(null);
        rightInfoPanel.setBounds(286, 10, 275, 244);
        rightInfoPanel.setOpaque(false);
        gameInfoPanel.add(rightInfoPanel);

        // 상대 정보 레이블
        opponentMatchedTilesLabel = new JLabel("맞춘 상대 타일 수 : 0");
        opponentMatchedTilesLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        opponentMatchedTilesLabel.setForeground(Color.BLACK);
        opponentMatchedTilesLabel.setBounds(20, 30, 235, 30);
        leftInfoPanel.add(opponentMatchedTilesLabel);

        opponentTilesMatchedLabel = new JLabel("상대가 맞춘 타일 수 : 0");
        opponentTilesMatchedLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        opponentTilesMatchedLabel.setForeground(Color.BLACK);
        opponentTilesMatchedLabel.setBounds(20, 80, 235, 30);
        leftInfoPanel.add(opponentTilesMatchedLabel);

        opponentRemainingTilesLabel = new JLabel("남은 상대 타일 수 : 0");
        opponentRemainingTilesLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        opponentRemainingTilesLabel.setForeground(Color.BLACK);
        opponentRemainingTilesLabel.setBounds(20, 130, 235, 30);
        leftInfoPanel.add(opponentRemainingTilesLabel);

        // 사용자 정보 레이블
        remainingTilesLabel = new JLabel("중앙 타일 남은 수 : 0");
        remainingTilesLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        remainingTilesLabel.setForeground(Color.BLACK);
        remainingTilesLabel.setBounds(20, 30, 235, 30);
        rightInfoPanel.add(remainingTilesLabel);

        myRemainingTilesLabel = new JLabel("남은 내 타일 수 : 0");
        myRemainingTilesLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        myRemainingTilesLabel.setForeground(Color.BLACK);
        myRemainingTilesLabel.setBounds(20, 80, 235, 30);
        rightInfoPanel.add(myRemainingTilesLabel);

        // 확인 버튼
        confirmButton = new JButton("확인");
        confirmButton.setBounds(700, 580, 100, 40); // 필요에 따라 위치 조정
        confirmButton.setVisible(false); // 처음에는 숨김
        mainContent.add(confirmButton);

        confirmButton.addActionListener(e -> {
            GamePhase phase = controller.getCurrentPhase();
            if (phase == GamePhase.INITIAL_SELECTION) {
                controller.confirmInitialSelection();
                confirmButton.setVisible(false);
            }
        });

        // 초기 UI 업데이트
        updateCentralPanel();
        updateUserPanel();
        updateComputerPanel();
        updateGameInfo();
    }

    /**
     * 중앙 영역에 24장의 카드(흰색 12장, 검은색 12장)를 초기화하고 섞습니다.
     */
    private void initializeCentralTiles() {
        List<Tile> initialTiles = controller.getTileManager().getCentralTiles();

        // 중앙 영역에 24개의 버튼 생성
        for (int i = 0; i < 26; i++) {
            JButton tileButton = createRoundedButton();
            Tile tile = initialTiles.get(i);
            tileButton.setBackground(tile.getTileColor()); // 타일 유형에 따라 배경색 설정
            centralCardSlots.add(tileButton);
            centralPanel.add(tileButton);
        }
    }

    /**
     * 둥근 모서리를 가진 JButton 생성.
     *
     * @return 커스터마이즈된 JButton.
     */
    private JButton createRoundedButton() {
        JButton button = new RoundedButton(20);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    /**
     * 게임 시작.
     */
    private void startGame() {
        controller.startGame();

        // 영역이 표시된 후 메시지 표시
        JOptionPane.showMessageDialog(this, "가져올 타일을 선택하세요(4개)");
    }

    /**
     * 시계 디스플레이 업데이트.
     *
     * @param seconds 경과된 총 초 수.
     */
    private void updateClock(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String timeString = String.format("%02d : %02d", minutes, secs);
        timeLabel.setText(timeString);
    }

    /**
     * 중앙 영역 업데이트.
     */
    private void updateCentralPanel() {
        List<Tile> centralTiles = gameState.getCentralTiles();
        GamePhase phase = controller.getCurrentPhase();

        for (int i = 0; i < centralCardSlots.size(); i++) {
            JButton tileButton = centralCardSlots.get(i);
            if (i >= centralTiles.size()) {
                // 이 위치에 타일이 없으면 슬롯을 비웁니다.
                tileButton.setIcon(null);
                tileButton.setEnabled(false);
                tileButton.setBackground(Color.LIGHT_GRAY);
                continue;
            }
            Tile tile = centralTiles.get(i);

            if (tile.isOpened()) {
                // 타일이 열렸다면 빈 슬롯으로 표시
                tileButton.setIcon(null);
                tileButton.setEnabled(false);
                tileButton.setBackground(Color.LIGHT_GRAY);
            } else if (tile.isSelected()) {
                // 타일이 선택되었다면 색상 변경
                tileButton.setBackground(Color.YELLOW);
                tileButton.setEnabled(true);
            } else {
                // 타일 사용 가능
                tileButton.setBackground(tile.getTileColor());
                tileButton.setIcon(null);
                tileButton.setEnabled(true);
            }

            final int index = i; // 타일 인덱스 저장

            // 중복을 피하기 위해 이전의 ActionListener 제거
            for (ActionListener al : tileButton.getActionListeners()) {
                tileButton.removeActionListener(al);
            }

            // 게임 단계에 따라 ActionListener 추가
            if (phase == GamePhase.INITIAL_SELECTION) {
                tileButton.addActionListener(e -> {
                    controller.playerSelectInitialTile(index);
                    updateCentralPanel();
                    updateGameInfo();
                    // 4개의 타일이 선택되었다면 확인 버튼 표시
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
     * 플레이어 영역 업데이트.
     */
    private void updateUserPanel() {
        userPlayer.sorting();
        List<Tile> userTiles = userPlayer.getTiles();

        for (int i = 0; i < userCardSlots.size(); i++) {
            JButton slotButton = userCardSlots.get(i);
            if (i < userTiles.size()) {
                Tile tile = userTiles.get(i);
                if (tile.isGuessedCorrectly()) {
                    // 맞게 추측했다면 앞면 이미지 표시
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.GRAY);
                } else if (tile.isOpened()) {
                    // 타일이 열렸다면 앞면 이미지 표시
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else {
                    // 타일의 뒷면 또는 빈 슬롯 표시
                    String imagePath = tile.getBackImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.BLUE);
                    slotButton.setIcon(null);
                }
            } else {
                // 빈 슬롯
                slotButton.setBackground(Color.BLUE);
                slotButton.setIcon(null);
            }
        }

        userPanel.revalidate();
        userPanel.repaint();
    }

    /**
     * 컴퓨터 영역 업데이트.
     */
    /*private void updateComputerPanel() {
        List<Tile> computerTiles = computerPlayer.getTiles();

        for (int i = 0; i < computerCardSlots.size(); i++) {
            JButton slotButton = computerCardSlots.get(i);
            if (i < computerTiles.size()) {
                Tile tile = computerTiles.get(i);
                if (tile.isGuessedCorrectly()) {
                    // 맞게 추측했다면 앞면 이미지 표시
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else {
                    // 타일의 뒷면 표시
                    slotButton.setBackground(Color.GREEN);
                    slotButton.setIcon(null);
                }

                final int index = i; // 위치 저장

                // 이전의 ActionListener 제거
                for (ActionListener al : slotButton.getActionListeners()) {
                    slotButton.removeActionListener(al);
                }

                // 플레이어의 추측 단계에서 ActionListener 추가
                if (controller.getCurrentPhase() == GamePhase.PLAYER_GUESS_PHASE && !tile.isGuessedCorrectly()) {
                    slotButton.addActionListener(e -> {
                        System.out.println("Button " + index + " clicked.");
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
     * 컴퓨터 영역 업데이트.
     */
    private void updateComputerPanel() {
        computerPlayer.sorting();
        List<Tile> computerTiles = computerPlayer.getTiles();
        System.out.println("Updating Computer Panel. Current Phase: " + controller.getCurrentPhase());
        System.out.println("Number of computer tiles: " + computerTiles.size());

        for (int i = 0; i < computerCardSlots.size(); i++) {
            JButton slotButton = computerCardSlots.get(i);
            if (i < computerTiles.size()) {
                Tile tile = computerTiles.get(i);
                System.out.println("Tile " + i + ": Guessed Correctly? " + tile.isGuessedCorrectly());

                if (tile.isGuessedCorrectly()) {
                    // 앞면 이미지 표시
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else {
                    // Hiển thị mặt sau của thẻ
                    String imagePath = tile.getBackImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setBackground(Color.BLUE);
                    }
                    slotButton.setBackground(Color.WHITE);
                }

                final int index = i; // Lưu vị trí

                // Loại bỏ các ActionListener cũ
                for (ActionListener al : slotButton.getActionListeners()) {
                    slotButton.removeActionListener(al);
                }

                // Thêm ActionListener nếu trong giai đoạn đoán và thẻ chưa được đoán đúng
                if (controller.getCurrentPhase() == GamePhase.PLAYER_GUESS_PHASE && !tile.isGuessedCorrectly()) {
                    slotButton.addActionListener(e -> {
                        System.out.println("Button " + index + " clicked for guessing.");
                        controller.playerGuessComputerTile(index);
                        updateComputerPanel();
                        updateGameInfo();
                        updateCentralPanel();
                        updateUserPanel();
                    });
                    slotButton.setEnabled(true); // Đảm bảo nút được kích hoạt
                } else {
                    slotButton.setEnabled(false);
                }
            } else {
                slotButton.setBackground(new Color(0x007B75));//녹색
                slotButton.setIcon(null);
                slotButton.setEnabled(false);
            }
        }

        computerPanel.revalidate();
        computerPanel.repaint();
    }

    private void checkGameOver() {
        controller.checkGameOver();
    }

    /**
     * 게임 정보 업데이트.
     */
    private void updateGameInfo() {
        remainingTilesLabel.setText("중앙 타일 남은 수 : " + controller.getTileManager().getDeckSize());
        opponentMatchedTilesLabel.setText("맞춘 상대 타일 수 : " + userPlayer.getScore());
        opponentTilesMatchedLabel.setText("상대가 맞춘 타일 수 : " + computerPlayer.getScore());
        opponentRemainingTilesLabel.setText("남은 상대 타일 수 : " + getUnopenedTilesCount(computerPlayer));
        myRemainingTilesLabel.setText("남은 내 타일 수 : " + getUnopenedTilesCount(userPlayer));
    }

    /**
     * 플레이어의 열리지 않은 타일 수를 가져옵니다.
     *
     * @param player 확인할 플레이어.
     * @return 열리지 않은 타일의 수.
     */
    private int getUnopenedTilesCount(Player player) {
        int count = 0;
        for (Tile tile : player.getTiles()) {
            if (tile.isOpened() && !tile.isGuessedCorrectly()) {
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
     * 둥근 모서리를 가진 JButton 클래스.
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

    @Override
    public void showVictoryScreen(VictoryScreen victoryScreen) {
        System.out.println("Showing victory screen.");
        // VictoryScreen 추가
        // this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(victoryScreen, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void showDefeatScreen(DefeatScreen defeatScreen) {
        System.out.println("Showing defeat screen.");
        // DefeatScreen 추가
        // this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(defeatScreen, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
