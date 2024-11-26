package com.example.project.models;

/**
 * Lớp User để lưu trữ thông tin người dùng.
 */
public class User {
    private String id;
    private String password;
    private String username;
    private int age;
    private String record; // Ví dụ: "90 wins - 10 loses"
    private int score; // Ví dụ: 1200
    private double ratio; // Ví dụ: 90.0 (tỉ lệ thắng)
    private String userImage;

    // Constructor đầy đủ
    public User(String id, String password, String username, int age, String record, int score, double ratio) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.age = age;
        this.record = record;
        this.score = score;
        this.ratio = ratio;
        this.userImage = userImage;
    }

    // Constructor mới với 4 tham số, các trường còn lại được gán giá trị mặc định
    public User(String id, String password, String username, int age) {
        this(id, password, username, age, "0W - 0L", 0, 0.0);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getRecord() {
        return record;
    }

    public int getScore() {
        return score;
    }

    public double getRatio() {
        return ratio;
    }

    // Setters (nếu cần)
    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setScore(int core) {
        this.score = score;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
