package com.example.project.utils;

import com.example.project.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * UserManager.java
 *
 * Lớp này quản lý việc tải, thêm và xác thực người dùng từ các nguồn dữ liệu khác nhau.
 */
public class UserManager {
    private static final String PROPERTIES_FILE = "id.properties";
    private static final String TXT_FILE = "user.txt"; // Đường dẫn tới tệp user.txt
    private Properties properties;
    private List<User> userList;

    public UserManager() {
        properties = new Properties();
        userList = new ArrayList<>();
        loadUsersFromTxt(); // Tải người dùng từ user.txt
        loadUsersFromProperties(); // Tải người dùng từ id.properties
    }

    /**
     * Tải người dùng từ tệp văn bản (user.txt).
     */
    private void loadUsersFromTxt() {
        String filePath = System.getProperty("user.dir") + "/user.txt";

        File externalFile = new File(filePath);
        if (!externalFile.exists()) {
            try (InputStream inputStream = getClass().getResourceAsStream("/texts/user.txt");
                 FileOutputStream outputStream = new FileOutputStream(externalFile)) {

                if (inputStream != null) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Đã sao chép user.txt từ resources sang thư mục hiện tại.");
                } else {
                    System.out.println("Không tìm thấy user.txt trong thư mục resources.");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        // Đọc dữ liệu từ user.txt
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\s+");
                if (data.length >= 8) { // Đảm bảo có đủ dữ liệu
                    User user = new User(
                            data[0], // id
                            data[1], // password
                            data[2], // username
                            Integer.parseInt(data[3]), // age
                            data[4], // record
                            Integer.parseInt(data[5]), // core
                            Integer.parseInt(data[6]), // ranking
                            Double.parseDouble(data[7]) // ratio
                    );
                    userList.add(user);
                } else {
                    System.out.println("Dữ liệu người dùng không hợp lệ: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tải người dùng từ tệp thuộc tính (id.properties).
     */
    private void loadUsersFromProperties() {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            int count = Integer.parseInt(properties.getProperty("count", "0"));
            for (int i = 1; i <= count; i++) {
                String id = properties.getProperty("user" + i + ".id");
                String password = properties.getProperty("user" + i + ".password");
                String username = properties.getProperty("user" + i + ".username");
                String ageStr = properties.getProperty("user" + i + ".age");
    
                int age = 0;
                if (ageStr != null) {
                    try {
                        age = Integer.parseInt(ageStr);
                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception for age: " + ageStr);
                    }
                }
    
                // 필요한 경우 다른 속성 추가
                User user = new User(id, password, username, age);
                userList.add(user);
            }
        } catch (FileNotFoundException e) {
            // 파일이 존재하지 않으면 빈 사용자 목록으로 시작
            System.out.println("id.properties 파일을 찾을 수 없습니다. user.txt에서 사용자 목록을 시작합니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm người dùng mới và lưu vào tệp properties.
     *
     * @param user Người dùng mới cần thêm.
     * @return true nếu thêm thành công, false nếu ID đã tồn tại.
     */
    public synchronized boolean addUser(User user) {
        // Kiểm tra ID đã tồn tại chưa
        for (User existingUser : userList) {
            if (existingUser.getId() != null && existingUser.getId().equals(user.getId())) {
                return false; // ID가 이미 존재함
            }
        }
        userList.add(user);
        // Cập nhật properties
        int count = userList.size();
        properties.setProperty("count", String.valueOf(count));
        properties.setProperty("user" + count + ".id", user.getId());
        properties.setProperty("user" + count + ".password", user.getPassword());
        properties.setProperty("user" + count + ".username", user.getUsername());
        properties.setProperty("user" + count + ".age", String.valueOf(user.getAge()));
        // Có thể thêm các thuộc tính khác nếu cần
        saveProperties();
        return true;
    }

    /**
     * Lưu properties vào tệp.
     */
    private void saveProperties() {
        try (OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, "User data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xác thực đăng nhập.
     *
     * @param id       ID người dùng.
     * @param password Mật khẩu người dùng.
     * @return Người dùng đã xác thực hoặc null nếu không hợp lệ.
     */
    public User authenticate(String id, String password) {
        for (User user : userList) {
            if (user.getId() != null && user.getId().equals(id) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    /**
     * Lấy danh sách người dùng.
     *
     * @return Danh sách người dùng.
     */
    public List<User> getUserList() {
        return userList;
    }
}
