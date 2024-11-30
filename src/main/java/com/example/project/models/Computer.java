package com.example.project.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.project.config.Tile;
import com.example.project.config.TileType;
import com.example.project.controllers.Controller;

/**
 * Computer.java
 *
 * 컴퓨터 플레이어를 나타냅니다.
 */
public class Computer extends Player {

    private Controller controller;

    public Computer(String name, Controller controller) {
        super(name);
        this.controller = controller;
    }

    public int countGuessedCorrectly() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.isGuessedCorrectly()) {
                count++;
            }
        }
        return count;
    }

    public int countGuessedCorrectly() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.isGuessedCorrectly()) {
                count++;
            }
        }
        return count;
    }


    @Override
    public int guessNumber(Tile tile) {
        // 컴퓨터가 열려있는 타일과 자신이 가지고 있는 타일을 확인하여 추측할 숫자를 결정
        
        System.out.println("Computer is guessing number for tile: " + tile.getNumber()+" "+tile.getTileColor());
        List<Integer> possibleNumbers = new ArrayList<>(); // 추측 가능한 숫자 목록
        List<Tile> userTiles = controller.getUserTiles();
        
        Loop:
        for (int i = 0; i < 12; i++) {
            // 컴퓨터가 이미 추측한 타일은 제외
            for(Tile t : this.tiles){
                if(t.getNumber()/10 == i && t.getTileColor().equals(tile.getTileColor())){
                    System.out.println("Computer's tile: "+t.getNumber()+" "+t.getTileColor());
                    continue Loop;
                } 
            }

            // 사용자가 이미 추측한 타일은 제외
            for(Tile t : userTiles){
                if(t.getNumber()/10 == i && t.isGuessedCorrectly()){
                    System.out.println("User's tile: "+t.getNumber()+" "+t.getTileColor()+" "+t.isGuessedCorrectly());
                    continue Loop;
                }
            }
            //나머지 숫자 추가
            possibleNumbers.add(i*10);
        }
        
        System.out.println("Possible Numbers:");
        for(Integer i : possibleNumbers){
            System.out.println(i);
        }

        int max = findMaxOpendTile(userTiles, tile);
        int min = findMinOpendTile(userTiles, tile);
        System.out.println("Max: "+max+" Min: "+min);
        
        // 가능한 숫자 중에서 오픈된 최대값과 최소값을 고려하여 추측할 숫자를 결정
        possibleNumbers = possibleNumbers.stream()
            .filter(i -> i >= min)
            .filter(i -> i <= max)
            .collect(Collectors.toList());

        System.out.println("Possible Numbers:");
        for(Integer i : possibleNumbers){
            System.out.println(i);
        }

        if(possibleNumbers.size() == 0 && !(tile.getTileType() == TileType.JOKER)){
            // return -1*10; //test환경에서 오류를 확인하기 위해
            return tile.getNumber(); //사용자는 오류를 볼 수 없게 하기 위해
        }
        else if(possibleNumbers.size() == 0 && tile.getTileType() == TileType.JOKER){
            return tile.getNumber();
        }

        return possibleNumbers.get(new Random().nextInt(possibleNumbers.size()));
    }
    
    //상대방이 열린 타일 중 tile보다 큰 수 중 가장 작은 수를 찾는다.
    private int findMaxOpendTile(List<Tile> tiles, Tile tile){
        Optional<Tile> maxTile = tiles.stream()
            .filter(Tile::isGuessedCorrectly)
            .filter(t -> t.getNumber() > tile.getNumber())
            .min((t1, t2) -> Integer.compare(t1.getNumber(), t2.getNumber()));
        
        if(tile.getNumber() == 11 *10||tile.getTileType() == TileType.JOKER){
            return maxTile.map(Tile::getNumber).orElse(12*10);
        }
        else{
            return maxTile.map(Tile::getNumber).orElse(11*10);
        }
    }

    //상대방이 열린 타일 중 tile보다 작은 수 중 가장 큰 수를 찾는다.
    private int findMinOpendTile(List<Tile> tiles, Tile tile){
        Optional<Tile> minTile = tiles.stream()
            .filter(Tile::isGuessedCorrectly)
            .filter(t -> t.getNumber() < tile.getNumber())
            .max((t1, t2) -> Integer.compare(t1.getNumber(), t2.getNumber()));

        if(tile.getNumber() == 0||tile.getTileType() == TileType.JOKER){
            return minTile.map(Tile::getNumber).orElse(-1*10);
        }
        else{
            return minTile.map(Tile::getNumber).orElse(0);
        }
    }
    
    @Override
    public Tile selectTile() {
        // 상대방의 열리지 않은 타일 중 하나를 선택
        return getRandomUnopenedTile();
    }
}
