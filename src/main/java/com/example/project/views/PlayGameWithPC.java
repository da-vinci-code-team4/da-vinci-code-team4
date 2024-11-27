package com.example.project.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.m;
import org.checkerframework.checker.units.qual.min;
import org.checkerframework.checker.units.qual.s;

import com.example.project.controller.Controller;
import com.example.project.game.manager.TileManager;
import com.example.project.game.tile.Tile;
import com.example.project.listener.CardMouseListener;
import com.example.project.listener.DrawButtonListener;
import com.example.project.utils.RoundedPanel;

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

    private String opponentId = "opponent";
    private String myId = "my";
    private String sharedId = "shared";

    private ArrayList<Tile> selectedTiles = new ArrayList<>();

    public PlayGameWithPC(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        setBackground(Color.gray);

        // 메인 패널
        JPanel mainContent = new JPanel(null);
        mainPanel.setName("MainPanel");
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
                mainContent.repaint();
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

        // 가져오기 버튼
        JButton drawButton = new JButton("가져오기");
        drawButton.setBounds(1000, 130, 200, 70);
        drawButton.setFont(new Font("Capriola-Regular", Font.PLAIN, 24));
        drawButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        drawButton.addActionListener(new DrawButtonListener(this));
        mainContent.add(drawButton);

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
        opponentCards = createPlayerCards(opponentId,opponentCards, mainContent, null, null);
        opponentCards.setBounds(210, 81 - 65, 650, 261);
        mainContent.add(opponentCards);

        // 플레이어 카드 영역
        myCards = createPlayerCards(myId, myCards, mainContent, null, null);
        myCards.setBounds(673, 682 - 65, 650, 261);
        mainContent.add(myCards);

        // 공유 카드 영역
        createSharedCards(sharedId,mainContent,null, null);

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
    private RoundedPanel createHoverableCard(String id, LayoutManager layout, Color bgColor, Color hoverColor, int radius, Tile tile) {
        RoundedPanel card;
        String imageName;
    
        if (tile == null) {
            card = new RoundedPanel(layout, bgColor, radius);
            return card;
        }
        
        String color = tile.getTileColor().toString();
        String index = String.valueOf(tile.getWeight() / 10);
        
        if(index.equals("-1")){
            index = "joker";
        }

        if (color.equals("BLACK")) {
            imageName = "b" + index;
        } else {
            imageName = "w" + index;
        }
        
        card = new RoundedPanel(id, layout, bgColor, radius, imageName);
        card.setTile(tile); // 타일 설정
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 호버 시 커서 변경
    
        // 호버 효과를 처리하기 위한 MouseListener 추가
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selectedTiles.contains(tile)) {
                    card.setBorder(BorderFactory.createLineBorder(hoverColor, 6));
                }
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                if (!selectedTiles.contains(tile)) {
                    card.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        });
    
        // 타일 클릭 이벤트를 처리하기 위한 CardMouseListener 추가
        card.addMouseListener(new CardMouseListener(this, card, color, index, tile));
    
        return card;
    }

    // 플레이어 카드 영역을 생성하는 메서드
    private JPanel createPlayerCards(String id, JPanel playerCards,JPanel mainContent, String[][] cardInfo, Tile[] tile) {
        
        // String[] color = new String[13];
        // String[] index = new String[13];
        // if (cardInfo != null) {
        //     for (int i = 0; i < 13; i++) {
        //         color[i] = cardInfo[i][0];
        //         index[i] = cardInfo[i][1];
        //     }
        // }
        // else{
        //     color = new String[13];
        //     for (int i = 0; i < 13; i++) {
        //         color[i] = null;
        //     }
        // }

        playerCards = new RoundedPanel(new GridLayout(2, 5, 10, 10), new Color(0xFFFFFF), 20);
        playerCards.setBounds(210, 81 - 65, 650, 261);
        playerCards.setBackground(Color.WHITE);
        
        
        for (int i = 0; i < 13; i++) {
            // 상대방 카드의 기본 색상과 호버 색상
            Color bgColor = new Color(0x61ADA8); // 기본 초록색
            Color hoverColor = new Color(0x81CFC8); // 호버 시 초록색
            
            RoundedPanel card;
            if(tile != null){
                // System.out.println(color+" "+i); // 디버깅용
                card = createHoverableCard(id, new FlowLayout(), bgColor, hoverColor, 20, tile[i]);
            }
            else{
                card = new RoundedPanel(new FlowLayout(), bgColor, 20);
            }
            
            playerCards.add(card);
        }

        return playerCards;
    }

    // 공유 카드 영역을 생성하는 메서드
    private void createSharedCards(String id, JPanel mainContent, String[][] cardInfo, Tile tile[]) {
        // String[] color = new String[26];
        // String[] index = new String[26];
        
        // if(cardInfo != null){
        //     for (int i = 0; i < 26; i++) {
        //         color[i] = cardInfo[i][0];
        //         index[i] = cardInfo[i][1];
        //     }
        // }
        // else{
        //     color = new String[26];
        //     for (int i = 0; i < 26; i++) {
        //         color[i] = null;
        //     }
        // }

        sharedCards = new RoundedPanel(new GridLayout(2, 5, 10, 10), new Color(0xFFFFFF), 20);
        sharedCards.setBounds(98, 373 - 65, 1313, 261);
        sharedCards.setBackground(Color.WHITE);
        for (int i = 0; i < 26; i++) {
            // 공유 카드의 기본 색상과 호버 색상
            Color bgColor = new Color(0xD9D9D9); // 기본 흰색
            Color hoverColor = new Color(0xF2D87D); // 호버 시 노란색
            
            RoundedPanel card;
            
            if(tile != null){
                card = createHoverableCard(id ,new FlowLayout(), bgColor, hoverColor,20, tile[i]);
            }
            else{
                card = new RoundedPanel(new FlowLayout(), bgColor, 20);
            }
    
            sharedCards.add(card);
        }
        mainContent.add(sharedCards);
        mainContent.repaint();
    }

    /**
     * 상대방 카드를 다시 생성하는 메서드.
     * @param mainContent
     * @param opponentCardInfo 0은 컬러, 1은 인덱스
     * 
     */
    private void recreateOpponentCards(JPanel mainContent, String[][] opponentCardInfo,Tile[] tile) {
        mainContent.remove(opponentCards);
        opponentCards = createPlayerCards(opponentId, opponentCards, mainContent, opponentCardInfo, tile);
        opponentCards.setBounds(210, 81 - 65, 650, 261);
        mainContent.add(opponentCards);
    }

    /**
     * 플레이어 카드를 다시 생성하는 메서드.
     * @param mainContent
     * @param myCardInfo 0은 컬러, 1은 인덱스
     */
    private void reCreateMyCards(JPanel mainContent, String[][] myCardInfo,Tile[] tile) {
        mainContent.remove(myCards);
        myCards = createPlayerCards(myId, myCards, mainContent, myCardInfo, tile);
        myCards.setBounds(673, 682 - 65, 650, 261);
        mainContent.add(myCards);
    }

    /**
     * 공유 카드를 다시 생성하는 메서드.
     * @param mainContent
     * @param cardInfo 0은 컬러, 1은 인덱스
     */
    private void reCreateSharedCards(JPanel mainContent, String[][] cardInfo, Tile[] tile) {
        mainContent.remove(sharedCards);
        createSharedCards(sharedId, mainContent, cardInfo, tile);
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
        SwingUtilities.invokeLater(() -> {
            System.out.println("updateTiles");

            // myCards.removeAll();
            // sharedCards.removeAll();
            // opponentCards.removeAll();

            // System.out.println("mainContent components : "+mainContent.getComponentCount());
            String[][] sharedCardImages = new String[26][2];
            Tile[] sharedTile = new Tile[26];
            for (int i = 0; i < Controller.getTileManagerSize(); i++) {
                sharedTile[i] = Controller.placeTileManagerTiles(i);
                System.out.println((i+1)+"shared : "+sharedTile[i].getTileColor()+" "+sharedTile[i].getWeight());
            }
            reCreateSharedCards(mainContent, sharedCardImages, sharedTile);

            String[][] myCardImages = new String[13][2];
            Tile[] myTile = new Tile[13];
            for (int i = 0; i < Controller.getFirstPlayerDeckSize(); i++) {
                myTile[i] = Controller.placeFirstPlayerTiles(i);
                System.out.println((i+1)+"my:"+myTile[i].getTileColor()+" "+myTile[i].getWeight());
            }
            reCreateMyCards(mainContent, myCardImages, myTile);
            

            String[][] opponentCardImages = new String[13][2];
            Tile[] opponentTile = new Tile[13];
            for (int i = 0; i < Controller.getSecondPlayerDeckSize(); i++) {
                opponentTile[i] = Controller.placeSecondPlayerTiles(i);
                System.out.println((i+1)+"op : "+opponentTile[i].getTileColor()+" "+opponentTile[i].getWeight());
            }
            recreateOpponentCards(mainContent, opponentCardImages, opponentTile);
            

            // System.out.println("mainContent components : "+mainContent.getComponentCount());
                      
        });
    }

    /**
     * (클릭시 호출) 선택된 타일을 저장하는 메서드
     * 클릭된 타일을 강조하기 위해 테두리를 변경한다.
     * @param tile 선택된 타일
     */
    public void selectTile(Tile tile) {
        SwingUtilities.invokeLater(() ->{
            // RoundedPanel selectedTilePanel = findTilePanelById(Controller.getTileManager().getTileOwner(tile));
            // if (selectedTilePanel == null) {
            //     System.out.println("해당 ID의 타일을 찾을 수 없습니다: " + tile.getTileColor() + " " + tile.getWeight() / 10);
            //     return;
            // }

            Tile selectedTile = tile;
            // Tile removedTile;

            System.out.println("selected : " + selectedTile.getTileColor() + " " + selectedTile.getWeight() / 10);
            // System.out.println("selectedTilePanel : " + selectedTilePanel.getBackgroundImageName());
            
            if (selectedTiles.size() <= 3) {
                if (selectedTiles.contains(tile)) {
                    selectedTile = selectedTiles.get(selectedTiles.indexOf(tile));
                    // selectedTilePanel = findTilePanelById(selectedTilePanel.getId());
                    // selectedTilePanel.removeBorderColor();
                    selectedTiles.remove(tile);
                } 
                else {            
                    selectedTiles.add(tile);
                }
                // removedTile = selectedTiles.get(0);
                // System.out.println("removed : " + removedTile.getTileColor() + " " + removedTile.getWeight()/10);
                // RoundedPanel removedTilePanel = findTilePanelById(Controller.getTileManager().getTileOwner(removedTile));
                // removedTilePanel.removeBorderColor();
                
                // selectedTilePanel.setBorderColor(Color.YELLOW);
                // selectedTilePanel.revalidate();
                // selectedTilePanel.repaint();
            }
        
            for (Tile t : selectedTiles) {
                System.out.println(t.getTileColor() + " " + t.getWeight()/10);
            }
        });
    }

    //  /**
    //  * ID를 통해 타일 패널을 찾는 메서드
    //  * @param id 패널 ID
    //  * @return 선택된 타일 패널
    //  */
    // private RoundedPanel findTilePanelById(String id) {
    //     // 플레이어 카드에서 타일 패널 찾기
    //     System.out.println("id : "+id);
    //     for (Component component : myCards.getComponents()) {
    //         if (component instanceof RoundedPanel) {
    //             RoundedPanel panel = (RoundedPanel) component;
    //             if (id.equals(panel.getId())) {
    //                 return panel;
    //             }
    //         }
    //     }

    //     // 공유 카드에서 타일 패널 찾기
    //     for (Component component : sharedCards.getComponents()) {
    //         if (component instanceof RoundedPanel) {
    //             RoundedPanel panel = (RoundedPanel) component;
    //             if (id.equals(panel.getId())) {
    //                 return panel;
    //             }
    //         }
    //     }

    //     // 상대방 카드에서 타일 패널 찾기
    //     for (Component component : opponentCards.getComponents()) {
    //         if (component instanceof RoundedPanel) {
    //             RoundedPanel panel = (RoundedPanel) component;
    //             if (id.equals(panel.getId())) {
    //                 return panel;
    //             }
    //         }
    //     }

    //     // 타일 패널을 찾지 못한 경우 null 반환
    //     return null;
    // }

    // /**
    //  * 선택된 타일을 찾는 메서드
    //  * @param color
    //  * @param index
    //  * @return 선택된 타일
    //  */
    // private RoundedPanel findTilePanel(Tile tile) {
    //     // // 플레이어 카드에서 타일 패널 찾기
    //     // for (Component component : myCards.getComponents()) {
    //     //     if (component instanceof RoundedPanel) {
    //     //         RoundedPanel panel = (RoundedPanel) component;
    //     //         if (panelMatchesTile(panel, tile)) {
    //     //             return panel;
    //     //         }
    //     //     }
    //     // }
    
    //     // 공유 카드에서 타일 패널 찾기
    //     for (Component component : sharedCards.getComponents()) {
    //         if (component instanceof RoundedPanel) {
    //             RoundedPanel panel = (RoundedPanel) component;
    //             if (panelMatchesTile(panel, tile)) {
    //                 return panel;
    //             }
    //         }
    //     }
    
    //     // 상대방 카드에서 타일 패널 찾기
    //     for (Component component : opponentCards.getComponents()) {
    //         if (component instanceof RoundedPanel) {
    //             RoundedPanel panel = (RoundedPanel) component;
    //             if (panelMatchesTile(panel, tile)) {
    //                 return panel;
    //             }
    //         }
    //     }
    
    //     // 타일 패널을 찾지 못한 경우 null 반환
    //     System.out.println("해당 타일을 찾을 수 없습니다: " + tile.getColor() + " " + tile.getIndex());
    //     return null;
    // }
    
    // // 타일 패널이 주어진 타일과 일치하는지 확인하는 메서드
    // private boolean panelMatchesTile(RoundedPanel panel, Tile tile) {

    //     Tile panelTile = panel.getTile();

    //     return Objects.equals(panelTile, tile);
    // }

    /**
     * @return 선택된 타일들을 반환하는 메서드
     */
    public ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    public JPanel getMainPanel() {
        // System.out.println("main panel : "+mainPanel.getName());
        return mainPanel;
    }
}