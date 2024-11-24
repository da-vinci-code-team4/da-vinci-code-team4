package view;

import controller.Controller;
import view.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class PlayGameWithPC extends JFrame {
    private JLabel timeLabel;
    private Timer timer;
    private int seconds = 0;

    // Biến thành viên cho khu vực bài của người chơi
    private JPanel myCards;
    private JPanel sharedCards;
    private JPanel opponentCards;

    public PlayGameWithPC() {
        // Thiết lập JFrame
        setTitle("Play Game With PC");
        setSize(1502, 916);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Panel chính
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 1502, 916);
        add(mainPanel);

        // Thời gian hiển thị trong RoundedPanel
        RoundedPanel timePanel = new RoundedPanel(new FlowLayout(), new Color(0xD9D9D9), 20);
        timePanel.setBounds(1058, 13, 244, 70);
        mainPanel.add(timePanel);

        timeLabel = new JLabel("00 : 00");
        timeLabel.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK); // Màu chữ
        timePanel.add(timeLabel);

        // Khởi tạo Timer để cập nhật đồng hồ mỗi giây
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updateClock();
            }
        });
        timer.start();

        // Nút thoát
        JLabel exitIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/back.png"))));
        exitIcon.setBounds(1396, 13, 127, 70);
        exitIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                timer.stop(); // Dừng đồng hồ khi thoát
                dispose(); // Đóng cửa sổ trò chơi
            }
        });
        mainPanel.add(exitIcon);

        // Hình PC
        JLabel pcIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/pc 2.png"))));
        pcIcon.setBounds(45, 133 - 65, 139, 179);
        pcIcon.setHorizontalAlignment(SwingConstants.CENTER);
        pcIcon.setVerticalAlignment(SwingConstants.TOP);
        mainPanel.add(pcIcon);

        // Dòng chữ "PC"
        JLabel pcText = new JLabel("PC");
        pcText.setBounds(45, 133 + 128 - 65, 139, 50);
        pcText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        pcText.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(pcText);

        // Hình ảnh "Me"
        JLabel meIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/me.png"))));
        meIcon.setBounds(1332, 709 - 55, 139, 186);
        meIcon.setHorizontalAlignment(SwingConstants.CENTER);
        meIcon.setVerticalAlignment(SwingConstants.TOP);
        mainPanel.add(meIcon);

        // Dòng chữ "Me"
        JLabel meText = new JLabel("Me");
        meText.setBounds(1342, 709 + 136 - 55, 139, 50);
        meText.setFont(new Font("Capriola-Regular", Font.PLAIN, 48));
        meText.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(meText);

        // Khu vực bài đối thủ
        opponentCards = new RoundedPanel(new GridLayout(2, 5, 10, 10), new Color(0xFFFFFF), 20);
        opponentCards.setBounds(210, 81 - 65, 650, 261);
        opponentCards.setBackground(Color.WHITE);
        for (int i = 0; i < 12; i++) {
            // Màu ban đầu và màu khi hover cho thẻ bài đối thủ
            Color bgColor = new Color(0x61ADA8); // Màu xanh lá ban đầu
            Color hoverColor = new Color(0x81CFC8); // Màu xanh lá khi hover
            RoundedPanel card = createHoverableCard(new FlowLayout(), bgColor, hoverColor, 20);
            opponentCards.add(card);
        }
        mainPanel.add(opponentCards);

        // Khu vực bài của người chơi
        myCards = new RoundedPanel(new GridLayout(2, 5, 10, 10), new Color(0xFFFFFF), 20);
        myCards.setBounds(673, 682 - 65, 650, 261);
        myCards.setBackground(Color.WHITE);
        for (int i = 0; i < 12; i++) {
            // Màu ban đầu và màu khi hover cho thẻ bài người chơi
            Color bgColor = new Color(0x3C77D0); // Màu xanh dương ban đầu
            Color hoverColor = new Color(0x5C99F0); // Màu xanh dương khi hover
            RoundedPanel card = createHoverableCard(new FlowLayout(), bgColor, hoverColor, 20);
            myCards.add(card);
        }
        mainPanel.add(myCards);

        // Khu vực bài chung
        sharedCards = new RoundedPanel(new GridLayout(2, 5, 10, 10), new Color(0xFFFFFF), 20);
        sharedCards.setBounds(98, 373 - 65, 1313, 261);
        sharedCards.setBackground(Color.WHITE);
        for (int i = 0; i < 24; i++) {
            RoundedPanel card = new RoundedPanel(new FlowLayout(), new Color(0xD9D9D9), 20); // Màu xám không có hiệu ứng hover
            sharedCards.add(card);
        }
        mainPanel.add(sharedCards);

        // Khu vực chat
        JPanel chatPanel = new RoundedPanel(null, new Color(0xD9D9D9), 20);
        chatPanel.setBounds(78, 679 - 65, 571, 264);
        mainPanel.add(chatPanel);

        // Khung nhập chat
        RoundedPanel chatInput = new RoundedPanel(null, new Color(0xD9D9D9), 20);
        chatInput.setBounds(78, 893 - 65, 490, 38);
        mainPanel.add(chatInput);

        // Gạch dưới trong khung chat
        JLabel chatUnderline = new JLabel();
        chatUnderline.setBounds(97, 914 - 65, 471, 1);
        chatUnderline.setBackground(Color.BLACK);
        chatUnderline.setOpaque(true);
        mainPanel.add(chatUnderline);

        // Biểu tượng chat
        JLabel chatIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/chat_icon.png"))));
        chatIcon.setBounds(512, 210 - 10, 50, 50);
        chatPanel.add(chatIcon);

        setVisible(true);

        updateTiles();
    }

    /**
     * Phương thức tạo một RoundedPanel với hiệu ứng hover.
     *
     * @param layout      Layout manager của RoundedPanel
     * @param bgColor     Màu nền ban đầu
     * @param hoverColor  Màu nền khi hover
     * @param radius      Bán kính bo góc
     * @return RoundedPanel đã được cấu hình
     */
    private RoundedPanel createHoverableCard(LayoutManager layout, Color bgColor, Color hoverColor, int radius) {
        RoundedPanel card = new RoundedPanel(layout, bgColor, radius);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thay đổi con trỏ khi hover

        // Thêm MouseListener để xử lý hiệu ứng hover
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

    /**
     * Thêm một thẻ bài vào khu vực bài của người chơi.
     *
     * @param card      Thẻ bài được thêm (RoundedPanel)
     * @param imageName Tên tệp hình ảnh của thẻ bài
     */
    public void addTileToMyTiles(RoundedPanel card, String imageName) {
        // Tạo một JLabel với hình ảnh của thẻ bài
        JLabel cardLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/tiles/" + imageName + ".png"))));
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Thêm JLabel vào RoundedPanel
        card.add(cardLabel);
        card.revalidate();
        card.repaint();
        // Cập nhật khu vực bài của người chơi

        myCards.revalidate();
        myCards.repaint();
    }

    public void addTileToOpponentTiles(RoundedPanel card, String imageName) {
        // Tạo một JLabel với hình ảnh của thẻ bài
        JLabel cardLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/tiles/" + imageName + ".png"))));
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Thêm JLabel vào RoundedPanel
        card.add(cardLabel);
        card.revalidate();
        card.repaint();
        // Cập nhật khu vực bài của người chơi

        opponentCards.revalidate();
        opponentCards.repaint();
    }

    public void addTileToTiles(RoundedPanel card, String imageName) {
        // Tạo một JLabel với hình ảnh của thẻ bài
        JLabel cardLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/tiles/" + imageName + ".png"))));
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Thêm JLabel vào RoundedPanel
        card.add(cardLabel);
        card.revalidate();
        card.repaint();

        // Cập nhật khu vực bài của người chơi

        sharedCards.revalidate();
        sharedCards.repaint();
    }

    /**
     * Phương thức cập nhật đồng hồ.
     */
    private void updateClock() {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String timeString = String.format("%02d : %02d", minutes, secs);
        timeLabel.setText(timeString);
    }

    public void updateTiles() {
        for(int i = 0; i < Controller.getTileManagerSize(); i++) {
            addTileToTiles((RoundedPanel)sharedCards.getComponent(i), Controller.placeTileManagerTiles(i));
        }

        for(int i = 0; i < Controller.getSecondPlayerDeckSize(); i++) {
            addTileToOpponentTiles((RoundedPanel)opponentCards.getComponent(i), Controller.placeSecondPlayerTiles(i));
        }

        for(int i = 0; i < Controller.getFirstPlayerDeckSize(); i++) {
            addTileToMyTiles((RoundedPanel)myCards.getComponent(i), Controller.placeFirstPlayerTiles(i));
        }
    }

}