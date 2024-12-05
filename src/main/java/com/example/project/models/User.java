package com.example.project.models;

import com.example.project.main.Main;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.units.qual.A;

/**
 * User.java
 *
 * 사용자 정보를 저장하는 클래스입니다.
 */
public class User {
    private String id;
    private String password;
    private String username;
    private int age;
    private String record; // 예: "90승 - 10패"
    private int core; // 예: 1200
    private int ranking; // 예: 1
    private double ratio; // 예: 90.0 (승률)
    private String userImage;

    /**
     * 전체 필드를 포함하는 생성자입니다.
     *
     * @param id 사용자 ID
     * @param password 비밀번호
     * @param username 사용자 이름
     * @param age 나이
     * @param record 전적
     * @param core 코어 점수
     * @param ranking 순위
     * @param ratio 승률
     */
    public User(String id, String password, String username, int age, String record, int core, int ranking, double ratio) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.age = age;
        this.record = record;
        this.core = core;
        this.ranking = ranking;
        this.ratio = ratio;
        this.userImage = userImage;
    }

    /**
     * 필수 필드만 포함하는 생성자입니다. 나머지 필드는 기본값으로 설정됩니다.
     *
     * @param id 사용자 ID
     * @param password 비밀번호
     * @param username 사용자 이름
     * @param age 나이
     */
    public User(String id, String password, String username, int age) {
        this(id, password, username, age, "0승-0패", 0, 0, 0.0);
    }

    // Getter 메서드
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

    public int getCore() {
        return core;
    }

    public int getRanking() {
        return ranking;
    }

    public double getRatio() {
        return ratio;
    }

    public String getUserImage() {
        return userImage;
    }

    // Setter 메서드 (필요 시)
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

    public void setCore(int core) {
        this.core = core;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void updateAfterGame(String result){
        String[] parts = getRecord().split("[^0-9]+");
        int win =Integer.parseInt(parts[0]);
        int lose = Integer.parseInt(parts[1]);

        if(result.equals("Victory")){
            setCore(getCore()+100);
            setRecord((win+1) + "w-"+lose+"l");
            setRatio((double) (win + 1) / (win+1+lose) * 100);
        }
        else{
            setCore(getCore()-100);
            setRecord(win + "w-"+(lose+1)+"l");
            setRatio((double) (win) / (win+1+lose) * 100);
        }

        List<User> temp = new ArrayList<>();
        for (User user : Session.getInstance().getUserList()) {
            if(user.getId().equals(Session.getInstance().getCurrentUser().getId())){
                user.setCore(Session.getInstance().getCurrentUser().getCore());
                user.setRecord(Session.getInstance().getCurrentUser().getRecord());
                user.setRatio(Session.getInstance().getCurrentUser().getRatio());
            }
            temp.add(user);
        }
        Session.getInstance().setUserList(temp);
        Main.updateUsers(temp);
    }
}
