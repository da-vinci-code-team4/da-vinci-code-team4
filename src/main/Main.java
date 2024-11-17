package main;

import game.DavinCiCode;
import game.player.Player;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);

    FileFactory fileFactory = new FileFactory();

    private Player user;
    private Player computer;

    public static void main(String[] args) {
        Main program = new Main();
        program.run();
    }

    void menu() {
        //테스트용 메뉴로 스윙 작업 시 변경 예정
        int inpMenu = scanner.nextInt();
        while(true){
            switch (inpMenu) {
                case 1: //게임시작
                    DavinCiCode game = new DavinCiCode(Player firstPlayer, Player secondPlayer);
                    break;
                case 2: //현황보기
                    viewStatus();
                    break;
                case 3: //기록보기
                    viewRecordList();
                    break;
                case 4: //랭크보기
                    viewRanking();
                    break;
            }

            if(inpMenu == 0) {
                break;
            }
        }

    }

    private void viewStatus() {

    }

    private void viewRecordList() {

    }

    private void viewRanking() {

    }

    void run() {
        menu();
    }
}