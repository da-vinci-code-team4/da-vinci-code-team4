package com.example.project.views;

import com.example.project.controller.Controller;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;

import org.checkerframework.checker.units.qual.s;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class PlayGameWithPC extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JLabel timeLabel;
    private Timer timer;
    private int seconds = 0;

    // 플레이어 카드 영역을 위한 멤버 변수
    private JPanel myCards;
    private JPanel sharedCards;
    private JPanel opponentCards;

    public PlayGameWithPC(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        setBackground(Color.gray);

        // 메인 패널
        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(Color.white);
        mainContent.setBounds(0, 0, 1502, 916);
        add(mainContent);

        // RoundedPanel에 표시되는 시간
        RoundedPanel timePanel = new RoundedPanel(new FlowLayout(), new Color(0xD9D9D9), 20);
        timePanel.setBounds(1058, 13, 244, 70);
        mainContent.add(timePanel);

        timeLabel = new JLabel("00 : 00");
        timeLabel.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK); // 글자 색상
        timePanel.add(timeLabel);

        // 매 초마다 시계를 업데이트하기 위한 타이머 초기화
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updateClock();
            }
        });
        timer.start();

        // 종료 버튼
        JLabel exitIcon = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/back.png")));
        exitIcon.setBounds(1396, 13, 127, 70);
        exitIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                timer.stop(); // 종료 시 타이머 중지
                // PlayPage로 돌아가기
                cardLayout.show(mainPanel, "PlayPage");
            }
        });
        mainContent.add(exitIcon);

        // PC 이미지
        JLabel pcIcon = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/pc2.png")));
        pcIcon.setBounds(45, 133 - 65, 139, 179);
        pcIcon.setHorizontalAlignment(SwingConstants.CENTER);
        pcIcon.setVerticalAlignment(SwingConstants.TOP);
        mainContent.add(pcIcon);

        // "PC" 텍스트
        JLabel pcText = new JLabel("PC");
        pcText.setBounds(45, 133 + 128 - 65, 139, 50);
        pcText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        pcText.setHorizontalAlignment(SwingConstants.CENTER);
        mainContent.add(pcText);

        // "Me" 이미지
        JLabel meIcon = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/me.png")));
        meIcon.setBounds(1332, 709 - 55, 139, 186);
        meIcon.setHorizontalAlignment(SwingConstants.CENTER);
        meIcon.setVerticalAlignment(SwingConstants.TOP);
        mainContent.add(meIcon);

        // "Me" 텍스트
        JLabel meText = new JLabel("Me");
        meText.setBounds(1342, 709 + 136 - 55, 139, 50);
        meText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        meText.setHorizontalAlignment(SwingConstants.CENTER);
        mainContent.add(meText);

        // 상대방 카드 영역
        opponentCards = createPlayerCards(opponentCards, mainContent, null);
        opponentCards.setBounds(210, 81 - 65, 650, 261);
        mainContent.add(opponentCards);

        // 플레이어 카드 영역
        myCards = createPlayerCards(myCards, mainContent, null);
        myCards.setBounds(673, 682 - 65, 650, 261);
        mainContent.add(myCards);

        // 공유 카드 영역
        createSharedCards(mainContent,null);

        // 채팅 영역
        JPanel chatPanel = new RoundedPanel(null, new Color(0xD9D9D9), 20);
        chatPanel.setBounds(78, 679 - 65, 571, 264);
        mainContent.add(chatPanel);

        // 채팅 입력 창
        RoundedPanel chatInput = new RoundedPanel(null, new Color(0xD9D9D9), 20);
        chatInput.setBounds(78, 893 - 65, 490, 38);
        mainContent.add(chatInput);

        // 채팅 입력 창의 밑줄
        JLabel chatUnderline = new JLabel();
        chatUnderline.setBounds(97, 914 - 65, 471, 1);
        chatUnderline.setBackground(Color.BLACK);
        chatUnderline.setOpaque(true);
        mainContent.add(chatUnderline);

        // 채팅 아이콘
        JLabel chatIcon = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/chat_icon.png")));
        chatIcon.setBounds(512, 210 - 10, 50, 50);
        chatPanel.add(chatIcon);

        Controller.startGame();
        updateTiles(mainContent);
    }

    /**
     * 호버 효과가 있는 RoundedPanel을 생성하는 메서드.
     *
     * @param layout     RoundedPanel의 레이아웃 매니저
     * @param bgColor    기본 배경색
     * @param hoverColor 호버 시 배경색
     * @param radius     모서리 반경
     * @return 구성된 RoundedPanel
     */
    private RoundedPanel createHoverableCard(LayoutManager layout, Color bgColor, Color hoverColor, int radius, String imagePath) {
        RoundedPanel card;
        if (imagePath == null) {
            card = new RoundedPanel(layout, bgColor, radius);
        } else {
            card = new RoundedPanel(layout, bgColor, radius, imagePath);
        }
        
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 호버 시 커서 변경

        // 호버 효과를 처리하기 위한 MouseListener 추가
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(bgColor);
            }
        });

        return card;
    }

    private JPanel createPlayerCards(JPanel playerCards,JPanel mainContent, String[] sharedCardImages){
        playerCards = new RoundedPanel(new GridLayout(2, 5, 10, 10), new Color(0xFFFFFF), 20);
        playerCards.setBounds(210, 81 - 65, 650, 261);
        playerCards.setBackground(Color.WHITE);
        for (int i = 0; i < 13; i++) {
            // 상대방 카드의 기본 색상과 호버 색상
            Color bgColor = new Color(0x61ADA8); // 기본 초록색
            Color hoverColor = new Color(0x81CFC8); // 호버 시 초록색
            
            RoundedPanel card;
            if(sharedCardImages != null){
                card = createHoverableCard(new FlowLayout(), bgColor, hoverColor, 20, sharedCardImages[i]);
            }
            else{
                card = createHoverableCard(new FlowLayout(), bgColor, hoverColor, 20, null);
            }
            
            playerCards.add(card);
        }
        
        return playerCards;
    }

    private void createSharedCards(JPanel mainContent, String[] sharedCardImages) {
        
        if(sharedCardImages == null){
            sharedCardImages = new String[26];
            for (int i = 0; i < 26; i++) {
                sharedCardImages[i] = null;
            }
        }

        sharedCards = new RoundedPanel(new GridLayout(2, 5, 10, 10), new Color(0xFFFFFF), 20);
        sharedCards.setBounds(98, 373 - 65, 1313, 261);
        sharedCards.setBackground(Color.WHITE);
        for (int i = 0; i < 26; i++) {
            // 공유 카드의 기본 색상과 호버 색상
            Color bgColor = new Color(0xD9D9D9); // 기본 흰색
            Color hoverColor = new Color(0xF2D87D); // 호버 시 노란색
            
            RoundedPanel card;
            
            if(sharedCardImages[i] != null){
                card = createHoverableCard(new FlowLayout(), bgColor, hoverColor,20, sharedCardImages[i]);
            }
            else{
                System.out.println("null"+i);
                card = createHoverableCard(new FlowLayout(), bgColor, hoverColor,20, null);
            }
            
            //createHoverableCard(new FlowLayout(), bgColor, hoverColor, 20);
            sharedCards.add(card);
        }
        mainContent.add(sharedCards);
    }

    private void recreateOpponentCards(JPanel mainContent, String[] opponentCardImages) {
        mainContent.remove(opponentCards);
        opponentCards = createPlayerCards(opponentCards, mainContent, opponentCardImages);
        opponentCards.setBounds(210, 81 - 65, 650, 261);
        mainContent.add(opponentCards);
    }

    private void reCreateMyCards(JPanel mainContent, String[] myCardImages) {
        mainContent.remove(myCards);
        myCards = createPlayerCards(myCards, mainContent, myCardImages);
        myCards.setBounds(673, 682 - 65, 650, 261);
        mainContent.add(myCards);
    }

    private void reCreateSharedCards(JPanel mainContent, String[] sharedCardImages) {
        mainContent.remove(sharedCards);
        createSharedCards(mainContent, sharedCardImages);
    }

    /**
     * 시계를 업데이트하는 메서드.
     */
    private void updateClock() {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String timeString = String.format("%02d : %02d", minutes, secs);
        timeLabel.setText(timeString);
    }

    /**
     * 타일을 표시하는 메서드
     */
    public void updateTiles(JPanel mainContent) {

        String[] sharedCardImages = new String[26];
        for (int i = 0; i < Controller.getTileManagerSize(); i++) {
            sharedCardImages[i] = Controller.placeTileManagerTiles(i);
            // System.out.println("타일: "+Controller.placeTileManagerTiles(i));
        }
        reCreateSharedCards(mainContent, sharedCardImages);

        String[] myCardImages = new String[13];
        for (int i = 0; i < Controller.getSecondPlayerDeckSize(); i++) {
            myCardImages[i] = Controller.placeSecondPlayerTiles(i);
        }
        recreateOpponentCards(mainContent, myCardImages);

        String[] opponentCardImages = new String[13];
        for (int i = 0; i < Controller.getFirstPlayerDeckSize(); i++) {
            opponentCardImages[i] = Controller.placeFirstPlayerTiles(i);
        }
        reCreateMyCards(mainContent, opponentCardImages);
    }
}