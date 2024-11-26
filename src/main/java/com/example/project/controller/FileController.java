package com.example.project.controller;

import com.example.project.models.Session;
import com.example.project.models.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileController {
    private static final String historyPath = "src/main/resources/texts/history.txt";
    private static final String rankingPath = "src/main/resources/texts/ranking.txt";
    private static final String userPath = "src/main/resources/texts/user.txt";

    private static List<String[]> history = new ArrayList<>();
    private static List<String[]> ranking = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static void setUserList(List<User> userList) {
        for (User user : userList) {
            users.add(user);
        }
    }
    public static void readHistoryData() {
        try (BufferedReader br = new BufferedReader(new FileReader(historyPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                history.add((line.split("\\s+")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readRankingData() {
        try (BufferedReader br = new BufferedReader(new FileReader(rankingPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                ranking.add(line.split("\\s+"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //로그인한 유저도 랭킹에 추가
        String[] parts = currentUser.getRecord().split("-"); // "99w"와 "122l"로 나누기
        // "w"와 "l"을 제거하고 숫자만 추출
        int wins = Integer.parseInt(parts[0].replaceAll("[^0-9]", "")); // "99w"에서 숫자만 남기기
        int losses = Integer.parseInt(parts[1].replaceAll("[^0-9]", "")); // 숫자만 남기기
        String userLine = (currentUser.getUsername() + " " +currentUser.getScore()+" "+ wins+ " "+ losses + " " + currentUser.getRecord());
        ranking.add(userLine.split("\\s+"));
    }


    public static void updateHistoryData(String result, int score, String playTime) {
        String time = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String newData = time + " " + result + " " + score + " " + playTime;
        int[] winData = new int[2];
        history.add(newData.split("\\s+"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt", true))) {
            writer.write(newData);  // 새로운 데이터 작성
            writer.newLine();  // 줄바꿈
        } catch (IOException e) {
            System.out.println("파일 쓰기 오류: " + e.getMessage());
        }

        if(result.equals("Defeat")){
            Controller.updateUserScore(-score);
            Controller.updateWin(-1);
        }
        else{
            Controller.updateUserScore(score);
            winData= Controller.updateWin(1);
        }

        currentUser.setScore(currentUser.getScore()+score);
        currentUser.setRecord(winData[0] + " " + winData[1]);
    }

    public static List<String[]> getHistory() {
        return history;
    }

    public static List<String[]> getRanking() {
        return ranking;
    }

    public static void updateUserFile() {
        for (User user : users) {
            if(user.getUsername().equals(currentUser.getUsername())){
                user.setScore(currentUser.getScore());
                user.setRecord(currentUser.getRecord());
            }
        }

        try (FileWriter writer = new FileWriter(userPath)) {
            // 1. 파일을 지운 후 새로운 내용으로 덮어쓰기
            for (User user : users) {
                String newContent = user.getId() + " " + user.getPassword() + " " + user.getUsername() + " " +
                    user.getAge() + " " + user.getRecord() + " " + user.getScore() + " " + user.getRatio(); // 새로 쓸 내용
                writer.write(newContent); // 새 내용을 파일에 씁니다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
