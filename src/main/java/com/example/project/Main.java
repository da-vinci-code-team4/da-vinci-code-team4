package com.example.project;

import com.example.project.factory.FileFactory;
import com.example.project.main.SwingMain;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);

    FileFactory fileFactory = new FileFactory();
    //    private Player user;

    public static void main(String[] args) {
        Main program = new Main();
        program.run();
    }

    private void gameStart() {
        //        DavinCiCode game = new DavinCiCode(Player firstPlayer, Player secondPlayer);
    }

    private void viewStatus() {

    }


    private void viewRecordList() {

    }

    private void viewRanking() {
        ArrayList<ArrayList<String>> arrayList = fileFactory.readRankFile();
        for (Object data : arrayList) {
            System.out.println(data);
        }
    }

    void run() {
        SwingMain myPage = new SwingMain();
        myPage.main();
        
    }

}