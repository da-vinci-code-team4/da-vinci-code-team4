package com.example.project.views;

import com.example.project.controllers.Controller;
import com.example.project.controllers.GameObserver;
import com.example.project.controllers.GamePhase;
import com.example.project.controllers.GameState;
import com.example.project.audio.AudioManager;
import com.example.project.config.Tile;
import com.example.project.models.Computer;
import com.example.project.models.GameUser;
import com.example.project.models.Player;
import com.example.project.models.User;
import com.example.project.utils.AudioUtil;
import com.example.project.utils.RoundedPanel;
import com.example.project.models.Session;

import javax.swing.*;

import org.checkerframework.checker.units.qual.A;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayGameWithPC.java
 * <p>
 * 컴퓨터와 플레이할 때의 사용자 인터페이스.
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

    // 중앙 버튼 목록
    private List<JButton> centralCardSlots;

    // 초기 단계에서 선택된 타일의 인덱스
    private List<Integer> selectedTileIndices;

    // 확인 버튼
    private JButton confirmButton;

    public PlayGameWithPC(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        setBackground(Color.WHITE);

        AudioManager.getInstance().playGameBackgroundMusic();
        // 컨트롤러 초기화
        controller = new Controller(this);
        gameState = controller.getGameState();
        userPlayer = controller.getUser();
        computerPlayer = controller.getComputer();

        // 중앙 버튼 목록 및 선택된 인덱스 초기화
        centralCardSlots = new ArrayList<>();
        selectedTileIndices = new ArrayList<>();

        // UI 구성 요소 초기화
        initializeUIComponents();

        // 0.75초 후 게임 시작
        Timer initialDelayTimer = new Timer(750, e -> startGame());
        initialDelayTimer.setRepeats(false);
        initialDelayTimer.start();
    }

    /**
     * 사용자가 패배했을 때 패배 화면을 표시합니다.
     */
    private void showDefeatScreen() {
        DefeatScreen defeatScreen = new DefeatScreen(
            controller.getCurrentUser(),
            controller.calculateTimeTaken(),
            mainPanel,
            cardLayout
        );
        showDefeatScreen(defeatScreen);
    }

    /**
     * 사용자가 승리했을 때 승리 화면을 표시합니다.
     */
    private void showVictoryScreen() {
        VictoryScreen victoryScreen = new VictoryScreen(
            controller.getCurrentUser(),
            controller.calculateTimeTaken(),
            mainPanel,
            cardLayout
        );
        showVictoryScreen(victoryScreen);
    }

    /**
     * 모든 UI 구성 요소를 초기화합니다.
     */
    private void initializeUIComponents() {
        
        // mainContent 패널 생성
        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(Color.WHITE);
        mainContent.setBounds(0, 0, 1502, 916);
        add(mainContent);

        // 시간 표시 패널 생성
        RoundedPanel timePanel = new RoundedPanel(new FlowLayout(), new Color(0xD9D9D9), 20);
        timePanel.setBounds(1058, 13, 244, 70);
        mainContent.add(timePanel);

        timeLabel = new JLabel("00 : 00");
        timeLabel.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK);
        timePanel.add(timeLabel);

        // 매초 시계를 업데이트하는 Swing Timer 초기화
        swingTimer = new Timer(1000, e -> {
            seconds++;
            updateClock(seconds);
        });
        swingTimer.start();

        // Exit 버튼 (MyPage로 돌아가기) 생성
        JButton exitButton = createRoundedButton();
        URL exitImageUrl = getClass().getResource("/img/ViewImage/back.png");
        ImageIcon exitIcon;
        if (exitImageUrl != null) {
            exitIcon = new ImageIcon(exitImageUrl);
            exitButton.setIcon(exitIcon);
        } else {
            exitButton.setText("Exit"); // 아이콘을 찾을 수 없을 경우 텍스트 사용
        }
        exitButton.setBounds(1384, 30, 60, 60);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setBackground(Color.WHITE);
        mainContent.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 확인 대화상자 표시
                int result = JOptionPane.showConfirmDialog(
                    PlayGameWithPC.this,
                    "게임 진행 중 나가시겠습니까?",
                    "확인",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) {
                    // Yes를 선택하면 타이머를 멈추고 MyPage로 전환
                    swingTimer.stop();
                    cardLayout.show(mainPanel, "MyPage");
                    AudioManager.getInstance().playBackgroundMusic();
                }
                // No를 선택하면 아무 것도 하지 않음
            }
        });

        // PC 이미지와 레이블 추가
        URL pcImageUrl = getClass().getResource("/img/ViewImage/pc2.png");
        ImageIcon pcIconImage;
        if (pcImageUrl != null) {
            pcIconImage = new ImageIcon(pcImageUrl);
        } else {
            pcIconImage = new ImageIcon();
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

        // 사용자 이미지와 레이블 추가
        URL meImageUrl = getClass().getResource("/img/ViewImage/me.png");
        ImageIcon meIconImage;
        if (meImageUrl != null) {
            meIconImage = new ImageIcon(meImageUrl);
        } else {
            meIconImage = new ImageIcon();
        }
        JLabel meIcon = new JLabel(meIconImage);
        meIcon.setBounds(1332, 654, 139, 186);
        meIcon.setHorizontalAlignment(SwingConstants.CENTER);
        meIcon.setVerticalAlignment(SwingConstants.TOP);
        mainContent.add(meIcon);

        JLabel meText = new JLabel("Me");
        meText.setBounds(1342, 815, 139, 50);
        meText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        meText.setHorizontalAlignment(SwingConstants.CENTER);
        mainContent.add(meText);

        // centralPanel 생성
        centralPanel = new JPanel(new GridLayout(2, 12, 5, 5));
        centralPanel.setBounds(98, 308, 1313, 261);
        centralPanel.setBackground(Color.WHITE);
        mainContent.add(centralPanel);

        // centralPanel의 버튼 초기화
        initializeCentralTiles();

        // userPanel 생성
        userPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        userPanel.setBounds(673, 617, 650, 261);
        userPanel.setBackground(Color.WHITE);
        mainContent.add(userPanel);

        // computerPanel 생성
        computerPanel = new RoundedPanel(new GridLayout(2, 6, 10, 10), new Color(0xFFFFFF), 20);
        computerPanel.setBounds(210, 16, 650, 261);
        computerPanel.setBackground(Color.WHITE);
        mainContent.add(computerPanel);

        // userPanel의 버튼 초기화
        userCardSlots = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            JButton slotButton = createRoundedButton();
            slotButton.setBackground(Color.BLUE);
            slotButton.setFocusable(false);
            userPanel.add(slotButton);
            userCardSlots.add(slotButton);
        }

        // computerPanel의 버튼 초기화
        computerCardSlots = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            JButton slotButton = createRoundedButton();
            slotButton.setBackground(new Color(0x007B75)); // 파란색
            slotButton.setFocusable(false);
            computerPanel.add(slotButton);
            computerCardSlots.add(slotButton);
        }

        // gameInfoPanel 생성
        RoundedPanel gameInfoPanel = new RoundedPanel(null, new Color(255, 255, 255), 20);
        gameInfoPanel.setBounds(78, 614, 571, 264);
        gameInfoPanel.setLayout(null);
        mainContent.add(gameInfoPanel);

        // leftInfoPanel 생성
        JPanel leftInfoPanel = new JPanel(null);
        leftInfoPanel.setBounds(10, 10, 275, 244);
        leftInfoPanel.setOpaque(false);
        gameInfoPanel.add(leftInfoPanel);

        // rightInfoPanel 생성
        JPanel rightInfoPanel = new JPanel(null);
        rightInfoPanel.setBounds(286, 10, 275, 244);
        rightInfoPanel.setOpaque(false);
        gameInfoPanel.add(rightInfoPanel);

        // 상대 정보 레이블 추가
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

        // 사용자 정보 레이블 추가
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

        // "확인" 버튼 생성
        confirmButton = new JButton("확인");
        confirmButton.setBounds(700, 580, 100, 40); // 필요시 위치 조정
        confirmButton.setVisible(false); // 처음에는 숨김
        mainContent.add(confirmButton);

        // "확인" 버튼 이벤트 처리
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
     * centralPanel의 버튼들을 초기화합니다.
     */
    private void initializeCentralTiles() {
        List<Tile> initialTiles = controller.getTileManager().getCentralTiles();

        // centralPanel에 26개의 버튼 생성
        for (int i = 0; i < 26; i++) { // 필요시 24로 수정
            JButton tileButton = createRoundedButton();
            Tile tile = initialTiles.get(i);
            tileButton.setBackground(tile.getTileColor());
            centralCardSlots.add(tileButton);
            centralPanel.add(tileButton);
        }
    }

    /**
     * 둥근 모서리를 가진 JButton을 생성합니다.
     *
     * @return 커스터마이징된 JButton
     */
    private JButton createRoundedButton() {
        JButton button = new RoundedButton(20);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        AudioUtil.addClickSound(button);
        return button;
    }

    /**
     * 게임을 시작합니다.
     */
    private void startGame() {
        controller.startGame();

        // 플레이어에게 4개의 타일을 선택하라는 알림 표시
        JOptionPane.showMessageDialog(this, "가져올 타일을 선택하세요(4개)");
    }

    /**
     * 시계를 업데이트합니다.
     *
     * @param seconds 경과된 총 초
     */
    private void updateClock(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String timeString = String.format("%02d : %02d", minutes, secs);
        timeLabel.setText(timeString);
    }

    /**
     * centralPanel을 업데이트합니다.
     */
    private void updateCentralPanel() {
        List<Tile> centralTiles = gameState.getCentralTiles();
        GamePhase phase = controller.getCurrentPhase();

        for (int i = 0; i < centralCardSlots.size(); i++) {
            JButton tileButton = centralCardSlots.get(i);
            if (i >= centralTiles.size()) {
                // 해당 위치에 더 이상 타일이 없으면 비활성화하고 배경색 변경
                tileButton.setIcon(null);
                tileButton.setEnabled(false);
                tileButton.setBackground(Color.LIGHT_GRAY);
                continue;
            }
            Tile tile = centralTiles.get(i);

            if (tile.isOpened()) {
                // 타일이 열렸으면 비활성화하고 배경색 변경
                tileButton.setIcon(null);
                tileButton.setEnabled(false);
                tileButton.setBackground(Color.LIGHT_GRAY);
            } else if (tile.isSelected()) {
                // 타일이 선택되었으면 배경색 변경
                tileButton.setBackground(Color.YELLOW);
                tileButton.setEnabled(true);
            } else {
                // 타일을 선택할 수 있음
                tileButton.setBackground(tile.getTileColor());
                tileButton.setIcon(null);
                tileButton.setEnabled(true);
            }

            final int index = i; // 타일의 인덱스 저장

            // 기존 ActionListener 제거
            for (ActionListener al : tileButton.getActionListeners()) {
                tileButton.removeActionListener(al);
            }

            // 게임 단계에 따라 ActionListener 추가
            if (phase == GamePhase.INITIAL_SELECTION) {
                tileButton.addActionListener(e -> {
                    controller.playerSelectInitialTile(index);
                    updateCentralPanel();
                    updateGameInfo();
                    // 4개의 타일을 선택했으면 "확인" 버튼 표시
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
     * userPanel을 업데이트합니다.
     */
    private void updateUserPanel() {
        userPlayer.sorting();
        List<Tile> userTiles = userPlayer.getTiles();
        System.out.println("플레이어 패널 업데이트 중. 현재 단계: " + controller.getCurrentPhase());
        System.out.println("플레이어 타일 수: " + userTiles.size());

        for (int i = 0; i < userCardSlots.size(); i++) {
            JButton slotButton = userCardSlots.get(i);
            if (i < userTiles.size()) {
                Tile tile = userTiles.get(i);
                // 각 타일의 상태 표시
                System.out.println("타일 " + i + ": 올바르게 맞혔습니까? " + tile.isGuessedCorrectly());
                if (tile.isGuessedCorrectly()) {
                    // 타일이 올바르게 맞춰졌으면 앞면 이미지 표시
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.GRAY);
                } else if (tile.isOpened()) {
                    // 타일이 열렸으면 앞면 이미지 표시
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else {
                    // 타일이 열리지 않았으면 뒷면 이미지 표시 또는 아무것도 없음
                    String imagePath = tile.getBackImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imagePath));
                    } else {
                        slotButton.setIcon(null);
                    }
                    slotButton.setBackground(Color.BLUE);
                    slotButton.setIcon(null);
                }
            } else {
                // 타일이 없는 슬롯 비활성화 및 아이콘 제거
                slotButton.setBackground(Color.BLUE);
                slotButton.setIcon(null);
            }
        }

        userPanel.revalidate();
        userPanel.repaint();
    }

    /**
     * computerPanel을 업데이트합니다.
     */
    private void updateComputerPanel() {
        computerPlayer.sorting();
        List<Tile> computerTiles = computerPlayer.getTiles();
        System.out.println("컴퓨터 패널 업데이트 중. 현재 단계: " + controller.getCurrentPhase());
        System.out.println("컴퓨터 타일 수: " + computerTiles.size());

        for (int i = 0; i < computerCardSlots.size(); i++) {
            JButton slotButton = computerCardSlots.get(i);
            if (i < computerTiles.size()) {
                Tile tile = computerTiles.get(i);
                System.out.println("타일 " + i + ": 올바르게 맞혔습니까? " + tile.isGuessedCorrectly());

                if (tile.isGuessedCorrectly()) {
                    // 타일이 올바르게 맞춰졌으면 앞면 이미지 표시
                    String imagePath = tile.getImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setBackground(Color.BLUE);
                    }
                    slotButton.setBackground(Color.WHITE);
                } else {
                    // 타일이 올바르게 맞춰지지 않았으면 뒷면 이미지 표시
                    String imagePath = tile.getBackImagePath();
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        slotButton.setIcon(new ImageIcon(imageUrl));
                    } else {
                        slotButton.setBackground(Color.BLUE);
                    }
                    slotButton.setBackground(Color.WHITE);
                }

                final int index = i; // 타일의 위치 저장

                // 기존 ActionListener 제거
                for (ActionListener al : slotButton.getActionListeners()) {
                    slotButton.removeActionListener(al);
                }

                // 게임 단계가 추측 단계이고 타일이 아직 맞춰지지 않았으면 ActionListener 추가
                if (controller.getCurrentPhase() == GamePhase.PLAYER_GUESS_PHASE
                    && !tile.isGuessedCorrectly()) {
                    slotButton.addActionListener(e -> {
                        System.out.println("버튼 " + index + " 클릭됨 (컴퓨터 타일 추측).");
                        controller.playerGuessComputerTile(index);
                        updateComputerPanel();
                        updateGameInfo();
                        updateCentralPanel();
                        updateUserPanel();
                    });
                    slotButton.setEnabled(true); // 버튼 활성화
                } else {
                    slotButton.setEnabled(false);
                }
            } else {
                // 타일이 없는 슬롯 비활성화 및 아이콘 제거
                slotButton.setBackground(new Color(0x007B75)); // 파란색
                slotButton.setIcon(null);
                slotButton.setEnabled(false);
            }
        }

        computerPanel.revalidate();
        computerPanel.repaint();
    }

    /**
     * 게임이 종료되었는지 확인합니다.
     */
    private void checkGameOver() {
        controller.checkGameOver();
    }

    /**
     * 게임 정보를 업데이트합니다.
     */
    private void updateGameInfo() {
        remainingTilesLabel.setText("중앙 타일 남은 수 : " + controller.getTileManager().getDeckSize());
        opponentMatchedTilesLabel.setText("맞춘 상대 타일 수 : " + userPlayer.getScore());
        opponentTilesMatchedLabel.setText("상대가 맞춘 타일 수 : " + computerPlayer.getScore());
        opponentRemainingTilesLabel.setText(
            "남은 상대 타일 수 : " + getUnopenedTilesCount(computerPlayer));
        myRemainingTilesLabel.setText("남은 내 타일 수 : " + getUnopenedTilesCount(userPlayer));
    }

    /**
     * 플레이어의 열리지 않은 타일 수를 가져옵니다.
     *
     * @param player 확인할 플레이어
     * @return 열리지 않은 타일 수
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
        System.out.println("onGameStateChanged 호출됨. 게임 종료: " + gameState.isGameOver());

        if (gameState.isGameOver()) {
            System.out.println("게임이 종료되었습니다. 결과를 표시합니다.");
            String winner = gameState.getWinner();
            if ("COMPUTER".equals(winner)) {
                Session.getInstance().getCurrentUser().updateAfterGame("Victory");
                HistoryPage.updateHistory("Victory", 100, controller.calculateTimeTaken());
                VictoryScreen victoryScreen = new VictoryScreen(
                    controller.getCurrentUser(),
                    controller.calculateTimeTaken(),
                    mainPanel,
                    cardLayout
                );
                showVictoryScreen(victoryScreen);
            } else if ("PLAYER".equals(winner)) {
                Session.getInstance().getCurrentUser().updateAfterGame("Defeat");
                HistoryPage.updateHistory("Defeat", 100, controller.calculateTimeTaken());
                DefeatScreen defeatScreen = new DefeatScreen(
                    controller.getCurrentUser(),
                    controller.calculateTimeTaken(),
                    mainPanel,
                    cardLayout
                );
                showDefeatScreen(defeatScreen);
            } else if ("DRAW".equals(winner)) {
                HistoryPage.updateHistory("Draw", 0, controller.calculateTimeTaken());
                JOptionPane.showMessageDialog(this, "게임이 무승부로 끝났습니다!");
            }

            swingTimer.stop(); // 게임 종료 시 타이머 중지
            return; // 추가적인 UI 업데이트 불필요
        }

        // 게임이 종료되지 않았을 때 UI 업데이트
        updateCentralPanel();
        updateUserPanel();
        updateComputerPanel();
        updateGameInfo();
    }

    /**
     * GameObserver에서 상속된 showVictoryScreen 메서드 구현.
     */
    @Override
    public void showVictoryScreen(VictoryScreen victoryScreen) {
        System.out.println("승리 화면을 표시합니다.");

        // VictoryScreen을 mainPanel에 추가하고 표시
        mainPanel.add(victoryScreen, "VictoryScreen");
        cardLayout.show(mainPanel, "VictoryScreen");
    }

    /**
     * GameObserver에서 상속된 showDefeatScreen 메서드 구현.
     */
    @Override
    public void showDefeatScreen(DefeatScreen defeatScreen) {
        System.out.println("패배 화면을 표시합니다.");
        // DefeatScreen을 mainPanel에 추가하고 표시
        mainPanel.add(defeatScreen, "DefeatScreen");
        cardLayout.show(mainPanel, "DefeatScreen");
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
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }

        @Override
        public boolean contains(int x, int y) {
            Shape shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                radius, radius);
            return shape.contains(x, y);
        }
    }
}
