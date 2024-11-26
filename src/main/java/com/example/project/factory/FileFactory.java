package com.example.project.factory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public ArrayList<ArrayList<String>> readRankFile() {
        Scanner rankFile = openFile("ranking.txt");
        ArrayList returnList = new ArrayList();
        while (rankFile.hasNext()) {
            ArrayList temp = new ArrayList();
            temp.add(rankFile.next());
            temp.add(rankFile.next());
            temp.add(rankFile.next());
            temp.add(rankFile.next());
            returnList.add(temp);
        }
        return returnList;
    }

    public void readSaveFile(String filename) {

    }

    public void updateSaveFile(String filename) {

    }

    public void readReplayFile(String filename) {

    }

    public void makeReplayFile(String filename) {

    }

}
