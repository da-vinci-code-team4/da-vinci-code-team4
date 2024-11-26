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

    private static List<String[]> history = new ArrayList<>();
    private static List<String[]> ranking = new ArrayList<>();

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
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
        String[] parts = currentUser.getRecord().split(" - ");
        int wins = Integer.parseInt(parts[0].replaceAll("[^0-9]", "")); // 숫자만 남기기
        int losses = Integer.parseInt(parts[1].replaceAll("[^0-9]", "")); // 숫자만 남기기
        String userLine = (currentUser.getUsername() + " " +currentUser.getScore()+" "+ wins+ " "+ losses + " " + currentUser.getRecord());
        ranking.add(userLine.split("\\s+"));
    }


    public static void updateHistoryData(String result, int score, String playTime) {
        String time = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String newData = time + " " + result + " " + score + " " + playTime;
        history.add(newData.split("\\s+"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt", true))) {
            writer.write(newData);  // 새로운 데이터 작성
            writer.newLine();  // 줄바꿈
        } catch (IOException e) {
            System.out.println("파일 쓰기 오류: " + e.getMessage());
        }

        if(result.equals("Defeat")){
            Controller.updateUserScore(-score);
        }
        else{
            Controller.updateUserScore(score);
        }
    }

    public static void updateRankingData(int score) {
        currentUser.setScore(currentUser.getScore()+score);

    }

    public static List<String[]> getHistory() {
        return history;
    }

    public static List<String[]> getRanking() {
        return ranking;
    }


}
