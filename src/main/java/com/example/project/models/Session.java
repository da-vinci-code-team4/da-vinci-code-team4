package com.example.project.models;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private static Session instance;
    private User currentUser;
    private List<User> userList;

    private Session() {
        userList = new ArrayList<>(); // Khởi tạo danh sách người dùng
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}