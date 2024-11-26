package com.example.project.controller;

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
    }

    public static void updateRankingData() {

    }

    public static List<String[]> getHistory() {
        return history;
    }

    public static List<String[]> getRanking() {
        return ranking;
    }


}
