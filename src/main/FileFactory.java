package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileFactory {
    Scanner openFile(String filename) {
        Scanner filein = null;
        try {
            filein = new Scanner(new File(filename));
        } catch (IOException e) {
            System.out.println("파일 오픈 실패 : " + filename);
            throw new RuntimeException(e);
        }
        return filein;
    }

    void readFile(String filename) {
        Scanner inFile = openFile(filename);

    }

    void makeFile(String filename) {

    }

    //아래는 별도 클래스로 뺄건지 정해야함
    //세이브 파일 양식도 정해야함
    public void readSaveFile(String filename) {

    }

    public void updateSaveFile(String filename) {

    }

    public void readReplayFile(String filename) {

    }

    public void makeReplayFile(String filename) {

    }

}
