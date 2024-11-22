// package main;

// //import game.DavinCiCode;
// //import game.player.Player;
// import java.util.ArrayList;
// import java.util.Scanner;
// import javax.swing.*;

// public class Main {
//     Scanner scanner = new Scanner(System.in);

//     FileFactory fileFactory = new FileFactory();
//     //    private Player user;

//     public static void main(String[] args) {
//         Main program = new Main();
//         program.run();
//     }

//     private void gameStart() {
// //        DavinCiCode game = new DavinCiCode(Player firstPlayer, Player secondPlayer);
//     }

//     private void viewStatus() {

//     }


//     private void viewRecordList() {

//     }

//     private void viewRanking() {
//         ArrayList<ArrayList<String>> arrayList = fileFactory.readRankFile();
//         for (Object data : arrayList) {
//             System.out.println(data);
//         }
//     }

//     void run() {
//         makeUI();
//     }

//     public void makeUI() {
//         JFrame frame = new JFrame("다빈치코드");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(1080, 720);
//         frame.setLocationRelativeTo(null);

//         // JPanel 생성
//         JPanel mainPanel = new JPanel();
//         frame.add(mainPanel);

//         // 버튼 생성
//         JButton gameStartBtn = new JButton("게임시작");
//         JButton viewStatusBtn = new JButton();
//         JButton viewRankingBtn = new JButton();

//         // 버튼 클릭 시 행동 정의
//         gameStartBtn.addActionListener(e -> gameStart());
//         viewStatusBtn.addActionListener(e -> viewStatus());
//         viewRankingBtn.addActionListener(e -> viewRanking());

//         // 버튼을 패널에 추가
//         mainPanel.add(gameStartBtn);
//         mainPanel.add(viewStatusBtn);
//         mainPanel.add(viewRankingBtn);

//         // 프레임을 화면에 표시
//         frame.setVisible(true);
//     }
// }