package com.example.project.controller;

import com.example.project.game.player.Player;
import com.example.project.game.player.User;
import com.example.project.game.tile.NumberTile;
import com.example.project.game.tile.Tile;
import com.example.project.game.tile.TileColor;
import com.example.project.game.tile.TileType;
import com.example.project.game.manager.GameManager;
import com.example.project.game.manager.TileManager;
import com.example.project.utils.RoundedPanel;
import com.example.project.views.PlayGameWithPC;

import java.awt.*;
import java.awt.event.MouseEvent;

import static com.example.project.game.status.TurnResult.*;
import static com.example.project.game.tile.TileType.*;

public class Controller {
    private static TileManager tileManager;
    private static User user;
    private static Player opponentPlayer;
    private static GameManager gameManager;

    public static void startGame() {
        System.out.println("게임시작");

        user = new User("test1", 100, 100, 1, 1);
        opponentPlayer = new User("test2", 100, 23991, 1, 1);
        tileManager = new TileManager(user, opponentPlayer);

        gameManager = new GameManager(tileManager, user, opponentPlayer);

        user.makeTileManager(tileManager);
        opponentPlayer.makeTileManager(tileManager);

        gameManager.startGame();
    }

    public static String placeFirstPlayerTiles(int number) {
        return makeFileName(opponentPlayer.getTile(number));
    }

    public static String placeSecondPlayerTiles(int number) {
        return makeFileName(opponentPlayer.getTile(number));
    }

    public static String placeTileManagerTiles(int number) {
        return makeFileName(tileManager.getTile(number), 1);
    }

    public static String makeFileName(Tile tile) {
        if (tile.getTileType() == JOKER) {
            if (tile.getTileColor() == TileColor.BLACK) {
                return "bjoker";
            } else {
                return "wjoker";
            }
        } else {
            if (tile.getTileColor() == TileColor.BLACK) {
                return "b" + tile.getWeight() / 10;
            } else {
                return "w" + tile.getWeight() / 10;
            }
        }
    }

    public static String makeFileName(Tile tile, int number) {
        if (tile.getTileColor() == TileColor.BLACK) {
            return "black";
        } else {
            return "white";
        }
    }

    public static int getFirstPlayerDeckSize() {
        return user.getDeckSize();
    }

    public static int getSecondPlayerDeckSize() {
        return opponentPlayer.getDeckSize();
    }

    public static int getTileManagerSize() {
        return tileManager.getDeckSize();
    }

    public static void updateUserScore(int score) {
        user.updateScore(score);
    }

    public static void informToPlayerForSelectTile() {
        PlayGameWithPC.updateTiles();
        System.out.println("상대방 카드를 클릭하고 예상한 숫자를 입력하세요.");
    }

    public static void informSelectedTile(int tileIndex, String input) {
        if (gameManager.isTurnOf(user)) {
            System.out.println("턴x");
            return;
        }

        Tile tile = opponentPlayer.getTile(tileIndex);

        if (tile.isTileType(JOKER) && input.equals("조커")) {
            GameManager.setTurnResult(MATCH);
            System.out.println("match");
            return;
        }
        if (((NumberTile) tile).getNumber() == Integer.parseInt(input)) {
            GameManager.setTurnResult(MATCH);
            System.out.println("match");
            return;
        }
        GameManager.setTurnResult(FAIL);
        System.out.println("fail");
    }
}
