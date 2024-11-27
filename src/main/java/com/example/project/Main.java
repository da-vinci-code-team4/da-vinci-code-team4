package com.example.project;

import com.example.project.controller.FileController;
import com.example.project.factory.FileFactory;
import com.example.project.main.SwingMain;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main program = new Main();
        program.run();
    }


    void run() {

        SwingMain myPage = new SwingMain();
        myPage.main();
        
    }

}