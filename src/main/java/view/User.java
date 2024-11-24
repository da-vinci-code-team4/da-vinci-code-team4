package view;

/**
 * Lớp User để lưu trữ thông tin người dùng.
 */
public class User {
    private String id;
    private String password;
    private String username;
    private int age;

    public User(String id, String password, String username, int age) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.age = age;
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
}
