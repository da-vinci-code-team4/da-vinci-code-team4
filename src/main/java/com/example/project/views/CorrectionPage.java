package com.example.project.views;

import com.example.project.models.Session;
import com.example.project.models.User;
import com.example.project.utils.RoundedPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * Trang CorrectionPage cho ứng dụng.
 * Cho phép người dùng chỉnh sửa thông tin cá nhân.
 */
public class CorrectionPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private User currentUser;
    private List<User> userList;

    // Các thành phần chỉnh sửa thông tin
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JSpinner ageSpinner;
    private JLabel userImgLabel;
    private ImageIcon userImageIcon;

    public CorrectionPage(JPanel mainPanel, CardLayout cardLayout, List<User> userList) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userList = userList;
        this.currentUser = Session.getInstance().getCurrentUser();
        setLayout(null);
        setBackground(Color.WHITE);

        // Thiết lập hình nền
        JLabel backgroundImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/Background.png")));
        backgroundImg.setBounds(0, 0, 1502, 916);
        backgroundImg.setLayout(null);
        add(backgroundImg);

        // Rectangle (Overlay Container)
        RoundedPanel overlayContainer = new RoundedPanel(null, new Color(0, 0, 0, 180), 10); // Bo góc 10px, nền đen với độ trong suốt 71%
        overlayContainer.setBounds(136, 170, 1231, 575); // (1502 - 1231)/2 ≈ 135.5 ~ 136; (916 - 575)/2 ≈ 170.5 ~ 170
        overlayContainer.setLayout(null);
        backgroundImg.add(overlayContainer);

        // Content Wrapper
        JPanel contentWrapper = new JPanel(null);
        contentWrapper.setBounds(0, 0, 1231, 575);
        contentWrapper.setOpaque(false);
        overlayContainer.add(contentWrapper);

        // Content Panel kích thước 1137x456px, cách nhau 40px
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBounds(47, 59, 1137, 456);
        contentPanel.setOpaque(false);
        contentWrapper.add(contentPanel);

        // --------------------- Cột Hình Ảnh ---------------------
        JPanel imageColumn = new JPanel(null);
        imageColumn.setBounds(0, 58, 356, 320); // Vị trí (0,108), kích thước 356x281px
        imageColumn.setOpaque(false);
        contentPanel.add(imageColumn);

        // User Image Label
        userImageIcon = (currentUser.getUserImage() != null && !currentUser.getUserImage().isEmpty())
                ? new ImageIcon(currentUser.getUserImage())
                : new ImageIcon(getClass().getResource("/img/ViewImage/userimg.png")); // Default image
        userImgLabel = new JLabel(userImageIcon);
        userImgLabel.setBounds(53, 58, 252, 221); // Vị trí (52,135), kích thước 252x221px
        userImgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userImgLabel.setVerticalAlignment(SwingConstants.CENTER);
        userImgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imageColumn.add(userImgLabel);

        // Thêm MouseListener để thay đổi ảnh khi nhấp vào
        userImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeUserImage();
            }
        });

        // Instruction Label
        JLabel instructionLabel = new JLabel("Click on the image to change");
        instructionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setBounds(0, 0, 356, 30); // Vị trí ở trên cùng của imageColumn
        imageColumn.add(instructionLabel);

        // Thumbnail Image
        JLabel thumbnailImg = new JLabel(new ImageIcon(getClass().getResource("/img/ViewImage/thumbnail.png")));
        thumbnailImg.setBounds(0, 30, 356, 281);
        thumbnailImg.setHorizontalAlignment(SwingConstants.CENTER);
        thumbnailImg.setVerticalAlignment(SwingConstants.CENTER);
        imageColumn.add(thumbnailImg);

        // --------------------- Cột Thông Tin Chỉnh Sửa ---------------------
        JPanel infoColumn = new JPanel(null);
        infoColumn.setBounds(356 + 40, 0, 818, 575); // 356 (width của imageColumn) + 40px spacing
        infoColumn.setOpaque(false);
        contentPanel.add(infoColumn);

        // Info Container
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(null);
        infoContainer.setBounds(0, 0, 818, 575);
        infoContainer.setOpaque(false);
        infoColumn.add(infoContainer);

        // --------------------- Group ID ---------------------
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(0, 0, 250, 59);
        idLabel.setHorizontalAlignment(SwingConstants.LEFT);
        infoContainer.add(idLabel);

        idField = new JTextField(currentUser.getId());
        idField.setBounds(300, 0, 400, 59);
        idField.setBackground(new Color(0xD9D9D9));
        idField.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        infoContainer.add(idField);

        // --------------------- Group Password ---------------------
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(0, 80, 250, 59);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        infoContainer.add(passwordLabel);

        passwordField = new JPasswordField(currentUser.getPassword());
        passwordField.setBounds(300, 80, 400, 59);
        passwordField.setBackground(new Color(0xD9D9D9));
        passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        infoContainer.add(passwordField);

        // --------------------- Group Username ---------------------
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(0, 160, 250, 59);
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        infoContainer.add(usernameLabel);

        usernameField = new JTextField(currentUser.getUsername());
        usernameField.setBounds(300, 160, 400, 59);
        usernameField.setBackground(new Color(0xD9D9D9));
        usernameField.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        infoContainer.add(usernameField);

        // --------------------- Group Age ---------------------
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setBounds(0, 240, 250, 59);
        ageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        infoContainer.add(ageLabel);

        SpinnerNumberModel ageModel = new SpinnerNumberModel(currentUser.getAge(), 1, 120, 1); // Default=current age
        ageSpinner = new JSpinner(ageModel);
        ageSpinner.setBounds(300, 240, 100, 59);
        ageSpinner.setBackground(new Color(0xD9D9D9));
        ageSpinner.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        infoContainer.add(ageSpinner);

        // --------------------- Nút Save ---------------------
        JButton saveButton = createRoundedButton("Save", 200, 60, 20, new Color(0xD9D9D9), Color.BLACK, new Font("맑은 고딕", Font.BOLD, 30));
        saveButton.setBounds(309, 350, 200, 60); // Vị trí và kích thước
        saveButton.addActionListener(e -> handleSave());
        infoContainer.add(saveButton);

        // --------------------- Nút Back ---------------------
        JButton backButton = new JButton(new ImageIcon(getClass().getResource("/img/ViewImage/back.png")));
        backButton.setBounds(1384, 30, 128, 86);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuPage"));
        backgroundImg.add(backButton);
    }

    /**
     * Phương thức để thay đổi ảnh người dùng.
     */
    private void changeUserImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh đại diện");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "gif");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            // Cập nhật ImageIcon và JLabel
            ImageIcon newIcon = new ImageIcon(fileToOpen.getAbsolutePath());
            // Resize ảnh nếu cần thiết
            Image img = newIcon.getImage().getScaledInstance(userImgLabel.getWidth(), userImgLabel.getHeight(), Image.SCALE_SMOOTH);
            userImageIcon = new ImageIcon(img);
            userImgLabel.setIcon(userImageIcon);
            // Lưu đường dẫn ảnh vào User
            currentUser.setUserImage(fileToOpen.getAbsolutePath());
        }
    }

    /**
     * Phương thức tạo một JButton với góc bo tròn và hỗ trợ độ trong suốt.
     *
     * @param text         Văn bản trên nút
     * @param width        Chiều rộng của nút
     * @param height       Chiều cao của nút
     * @param cornerRadius Bán kính bo góc
     * @param bgColor      Màu nền của nút (có alpha)
     * @param fgColor      Màu chữ của nút
     * @param font         Font chữ của nút
     * @return JButton với các thuộc tính đã thiết lập
     */
    private JButton createRoundedButton(String text, int width, int height, int cornerRadius, Color bgColor, Color fgColor, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Vẽ nút với góc bo tròn và độ trong suốt
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Không vẽ viền
            }
        };
        button.setFont(font);
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false); // Thiết lập không opaque để hỗ trợ độ trong suốt
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Xử lý khi nhấn nút Save.
     * Lưu các thay đổi tạm thời vào đối tượng User hiện tại.
     */
    private void handleSave() {
        String newId = idField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();
        String newUsername = usernameField.getText().trim();
        int newAge = (int) ageSpinner.getValue();

        // Kiểm tra thông tin nhập đầy đủ
        if (newId.isEmpty() || newPassword.isEmpty() || newUsername.isEmpty() || newAge <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra ID đã tồn tại (trừ trường hợp ID không thay đổi)
        for (User user : userList) {
            if (user.getId().equals(newId) && !user.getId().equals(currentUser.getId())) {
                JOptionPane.showMessageDialog(this, "ID đã tồn tại. Vui lòng chọn ID khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Cập nhật thông tin người dùng
        currentUser.setId(newId);
        currentUser.setPassword(newPassword);
        currentUser.setUsername(newUsername);
        currentUser.setAge(newAge);

        JOptionPane.showMessageDialog(this, "Thông tin đã được lưu tạm thời.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        // Bạn có thể thêm logic để lưu thay đổi này vào cơ sở dữ liệu hoặc danh sách người dùng
        // Ví dụ: userList đã được cập nhật vì currentUser tham chiếu tới một phần tử trong userList

        // Sau khi lưu, quay lại ProfilePage
        ProfilePage profilePage = new ProfilePage(mainPanel, cardLayout, currentUser);
        mainPanel.add(profilePage, "ProfilePage");
        cardLayout.show(mainPanel, "ProfilePage");
    }
}
