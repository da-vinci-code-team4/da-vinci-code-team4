package com.example.project.controllers;

import com.example.project.config.TileType;
import com.example.project.models.Computer;
import com.example.project.models.GameUser;
import com.example.project.models.*;
import com.example.project.config.Tile;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller.java
 *
 * 게임의 흐름을 관리하고 플레이어와 컴퓨터 간의 로직을 처리합니다.
 */
public class Controller {
    private TileManager tileManager;
    private GameUser user;
    private Computer computer;
    private GameState gameState;
    private GameObserver observer;
    private long startTime;
    private User currentUser;

    // 현재 게임의 상태
    private GamePhase currentPhase;

    // 최근에 가져간 타일
    private Tile userLatest;
    private Tile pcLatest;

    // private int targetCore;

    public Controller(GameObserver observer) {
        this.observer = observer;
        tileManager = new TileManager();
        tileManager.initializeTiles(); // 24개의 타일 초기화 (12 검정, 12 흰색) 및 섞기
        gameState = new GameState();
        gameState.setCentralTiles(tileManager.getCentralTiles());
        user = new GameUser("플레이어");
        computer = new Computer("컴퓨터", this);
        gameState.setUserTiles(user.getTiles());
        gameState.setComputerTiles(computer.getTiles());
        currentPhase = GamePhase.INITIAL_SELECTION;
        startTime = System.currentTimeMillis();
        currentUser = Session.getInstance().getCurrentUser();
    }

    /**
     * 게임을 시작합니다.
     */
    public void startGame() {
        // 게임 상태 업데이트
        observer.onGameStateChanged(gameState);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public int calculateTimeTaken() {
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    /**
     * 초기 선택 단계에서 중앙에서 플레이어가 타일을 선택하는 것을 처리합니다.
     *
     * @param index 중앙 영역에서의 타일 위치 (0-23)
     */
    public void playerSelectInitialTile(int index) {
        if(gameState.isGameOver()){
            return;
        }

        if (currentPhase != GamePhase.INITIAL_SELECTION) {
            JOptionPane.showMessageDialog(null, "타일을 선택할 차례가 아닙니다.");
            return;
        }

        Tile tile = tileManager.drawTile(index);
        if (tile != null && !tile.isOpened()) {
            if (!tile.isSelected()) {
                tile.setSelected(true); // 타일이 선택되었음을 표시
                JOptionPane.showMessageDialog(null, "타일이 선택되었습니다.");
            } else {
                tile.setSelected(false); // 타일 선택 해제
                JOptionPane.showMessageDialog(null, "타일 선택이 취소되었습니다.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "타일이 이미 열렸거나 유효하지 않습니다!");
        }
        observer.onGameStateChanged(gameState);
    }

    /**
     * 초기 선택 단계에서 선택된 타일을 확인합니다.
     */
    public void confirmInitialSelection() {
        if(gameState.isGameOver()){
            return;
        }
        if (currentPhase != GamePhase.INITIAL_SELECTION) {
            JOptionPane.showMessageDialog(null, "확인할 차례가 아닙니다.");
            return;
        }

        List<Tile> selectedTiles = new ArrayList<>();
        for (Tile tile : tileManager.getCentralTiles()) {
            if (tile.isSelected()) {
                selectedTiles.add(tile);
            }
        }

        if (selectedTiles.size() != 4) {
            JOptionPane.showMessageDialog(null, "정확히 4개의 타일을 선택해야 합니다.");
            return;
        }

        // 선택한 타일들 정렬
        selectedTiles.sort((tile1, tile2) -> Integer.compare(tile1.getNumber(), tile2.getNumber()));
        for (Tile tile : selectedTiles) {
            tile.isJoker(1, selectedTiles);
            tile.setOpened(true);
            tile.setSelected(false);
            user.addTile(tile);
        }
        gameState.setUserTiles(user.getTiles());

        // 선택된 타일을 중앙 영역에서 제거
        observer.onGameStateChanged(gameState);

        // 컴퓨터가 4개의 타일을 선택합니다
        currentPhase = GamePhase.COMPUTER_INITIAL_SELECTION;
        computerInitialDrawTiles();

        // 플레이어의 타일 뽑기 단계로 전환
        currentPhase = GamePhase.PLAYER_DRAW_PHASE;
        observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
        if(tileManager.hasTiles()){
            JOptionPane.showMessageDialog(null, "당신의 차례: 타일 하나를 선택하여 뽑아주세요 (1).");
        }
        else{
            JOptionPane.showMessageDialog(null, "당신의 차례: 상대방의 타일을 선택하여 추측해주세요. (조커는 13으로 입력)");
        }
    }

    /**
     * 컴퓨터가 초기 선택 단계에서 4개의 타일을 뽑습니다.
     */
    public void computerInitialDrawTiles() {
        if(gameState.isGameOver()){
            return;
        }
        int tilesToDraw = 4;
        for (int i = 0; i < tilesToDraw; i++) {
            int index = tileManager.getRandomAvailableTileIndex();
            if (index != -1) {
                Tile computerTile = tileManager.drawTile(index);
                computerTile.isJoker();
                if (computerTile != null && !computerTile.isOpened()) {
                    computerTile.setOpened(true);
                    computer.addTile(computerTile);
                }
            }
        }
        gameState.setComputerTiles(computer.getTiles());
        JOptionPane.showMessageDialog(null, "컴퓨터가 4개의 타일을 선택했습니다.");
        observer.onGameStateChanged(gameState);
    }

    /**
     * 플레이어가 타일을 뽑는 것을 처리합니다.
     *
     * @param index 중앙 영역에서의 타일 위치 (0-23)
     */
    public void playerDrawTile(int index) {
        if(gameState.isGameOver()){
            return;
        }
        if (currentPhase != GamePhase.PLAYER_DRAW_PHASE) {
            JOptionPane.showMessageDialog(null, "타일을 뽑을 차례가 아닙니다.");
            return;
        }
        if (!tileManager.hasTiles()) {
            JOptionPane.showMessageDialog(null, "중앙에 더 이상 뽑을 타일이 없습니다!");
            currentPhase = GamePhase.PLAYER_GUESS_PHASE;
            return;
        }

        Tile tile = tileManager.drawTile(index);
        userLatest = tile;

        if (tile != null && !tile.isOpened()) {
            tile.isJoker(user.getTiles());
            tile.setOpened(true);
            user.addTile(tile);
            gameState.setUserTiles(user.getTiles());
            JOptionPane.showMessageDialog(null, "타일이 당신의 손에 추가되었습니다.");
            observer.onGameStateChanged(gameState);

            // 플레이어의 추측 단계로 전환
            currentPhase = GamePhase.PLAYER_GUESS_PHASE;
            observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
            JOptionPane.showMessageDialog(null, "당신의 차례: 상대방의 타일을 선택하여 추측해주세요. (조커는 13으로 입력)");
        } else {
            JOptionPane.showMessageDialog(null, "유효하지 않은 타일 선택입니다.");
        }
    }

    /**
     * 플레이어가 컴퓨터의 타일을 추측하는 것을 처리합니다.
     *
     * @param index 컴퓨터 타일의 인덱스
     */
    public void playerGuessComputerTile(int index) {
        if(gameState.isGameOver()){
            return;
        }
        if (currentPhase != GamePhase.PLAYER_GUESS_PHASE) {
            JOptionPane.showMessageDialog(null, "추측할 차례가 아닙니다.");
            return;
        }

        if (index < 0 || index >= computer.getTiles().size()) {
            JOptionPane.showMessageDialog(null, "유효하지 않은 타일 선택입니다!");
            return;
        }

        Tile computerTile = computer.getTiles().get(index);
        if (computerTile.isGuessedCorrectly()) {
            JOptionPane.showMessageDialog(null, "이 타일은 이미 올바르게 추측되었습니다!");
            return;
        }

        // 플레이어가 추측할 숫자를 입력
        int guessedNumber = user.guessNumber(computerTile);
        if (guessedNumber == computerTile.getNumber() || (computerTile.getTileType().equals(TileType.JOKER) && guessedNumber == 130)) {
            JOptionPane.showMessageDialog(null, "정답을 맞추셨습니다!");
            computerTile.setGuessedCorrectly(true); // 올바르게 추측되었음을 표시
            user.increaseScore();
            gameState.setTileOpenedInComputer(index); // GameState에서 타일이 열렸음을 표시
            observer.onGameStateChanged(gameState);

            // 승리 조건 확인
            checkGameOver();
            if(gameState.isGameOver()){
                return;
            }
            String option = JOptionPane.showInputDialog(null, "계속하시겠습니까 (Y/N) :");
            if (option != null) {
                switch (option.trim().toUpperCase()) {
                    case "Y":
                        // 타일 뽑기 단계로 전환
                        currentPhase = GamePhase.PLAYER_GUESS_PHASE;
                        observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
                        JOptionPane.showMessageDialog(null, "당신의 차례: 상대방의 타일을 선택하여 추측해주세요.(조커는 13으로 입력)");
                        break;
                    case "N":
                        currentPhase = GamePhase.COMPUTER_TURN;
                        observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
                        computerTurn();
                        checkGameOver();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "잘못된 입력입니다. 자동으로 컴퓨터 턴으로 전환됩니다.");
                        currentPhase = GamePhase.COMPUTER_TURN;
                        observer.onGameStateChanged(gameState);
                        computerTurn();
                        checkGameOver();
                        break;
                }
            } else {
                // 입력이 null인 경우 (취소 또는 닫기), 컴퓨터 턴으로 전환
                currentPhase = GamePhase.COMPUTER_TURN;
                observer.onGameStateChanged(gameState);
                computerTurn();
                checkGameOver();
            }
        } else {
            JOptionPane.showMessageDialog(null, "틀렸습니다!");
            // 컴퓨터의 턴으로 전환
            user.openLatest(userLatest);
            computer.increaseScore();
            gameState.setTileOpenedInUser(user.getTiles().indexOf(userLatest)); // GameState에서 타일이 열렸음을 표시
            currentPhase = GamePhase.COMPUTER_TURN;
            observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
            computerTurn();
            checkGameOver();
        }
    }

    public void computerTurn() {
        if(gameState.isGameOver()){
            return;
        }
        // 컴퓨터가 중앙에서 타일을 뽑음
        computerDrawTileFromCenter();
        checkGameOver();

        // 컴퓨터가 추측할 타일을 선택
        Tile userTile = user.getRandomUnopenedTile();
        if (userTile != null) {
            int guessedNumber = computer.guessNumber(userTile);
            String userTileType = Integer.toString(userTile.getNumber() / 10);
            String guessedNumberType = Integer.toString(guessedNumber / 10);

            if (userTile.getTileType().equals(TileType.JOKER)) {
                userTileType = "JOKER";
            }

            if (guessedNumber == userTile.getNumber()) {
                JOptionPane.showMessageDialog(null, "컴퓨터가 선택한 타일 : " + userTileType + "\n컴퓨터의 추측: " + userTileType);
                JOptionPane.showMessageDialog(null, "컴퓨터가 정답을 맞추었습니다!");
                userTile.setOpened(true);
                userTile.setGuessedCorrectly(true); // 올바르게 추측되었음을 표시
                computer.increaseScore();
                gameState.setTileOpenedInUser(user.getTiles().indexOf(userTile)); // GameState에서 타일이 열렸음을 표시
                observer.onGameStateChanged(gameState);

                // 승리 조건 확인
                checkGameOver();
                if(gameState.isGameOver()){
                    return;
                }
                // 타일 뽑기 단계로 전환
                currentPhase = GamePhase.PLAYER_DRAW_PHASE;
                observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
                if(tileManager.hasTiles()){
                    JOptionPane.showMessageDialog(null, "당신의 차례: 타일 하나를 선택하여 뽑아주세요 (1).");
                }
                else{
                    JOptionPane.showMessageDialog(null, "당신의 차례: 상대방의 타일을 선택하여 추측해주세요. (조커는 13으로 입력)");
                }

            } else {
                JOptionPane.showMessageDialog(null, "컴퓨터가 선택한 타일 : " + userTileType + "\n컴퓨터의 추측: " + guessedNumberType);
                JOptionPane.showMessageDialog(null, "컴퓨터가 틀렸습니다!");
                computer.openLatest(pcLatest); // 최근 뽑은 타일 공개
                user.increaseScore();
                gameState.setTileOpenedInUser(computer.getTiles().indexOf(pcLatest)); // GameState에서 타일이 열렸음을 표시
                // 타일 뽑기 단계로 전환
                currentPhase = GamePhase.PLAYER_DRAW_PHASE;
                observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
                if(tileManager.hasTiles()){
                    JOptionPane.showMessageDialog(null, "당신의 차례: 타일 하나를 선택하여 뽑아주세요 (1).");
                }
                else{
                    JOptionPane.showMessageDialog(null, "당신의 차례: 상대방의 타일을 선택하여 추측해주세요. (조커는 13으로 입력)");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "컴퓨터가 추측할 타일이 더 이상 없습니다.");
            // 타일 뽑기 단계로 전환
            currentPhase = GamePhase.PLAYER_DRAW_PHASE;
            observer.onGameStateChanged(gameState); // UI 업데이트를 위해 추가
            if(tileManager.hasTiles()){
                JOptionPane.showMessageDialog(null, "당신의 차례: 타일 하나를 선택하여 뽑아주세요 (1).");
            }
            else{
                JOptionPane.showMessageDialog(null, "당신의 차례: 상대방의 타일을 선택하여 추측해주세요. (조커는 13으로 입력)");
            }
        }
    }

    /**
     * 컴퓨터가 중앙에서 타일을 뽑는 것을 처리합니다.
     */
    private void computerDrawTileFromCenter() {
        if(gameState.isGameOver()){
            return;
        }
        Tile tile = tileManager.drawRandomCentralTile();
        pcLatest = tile;
        if (tile != null) {
//            computer.addTile(tile);
            tile.isJoker();
            gameState.getComputerTiles().add(tile); // GameState 업데이트
            JOptionPane.showMessageDialog(null, "컴퓨터가 중앙에서 타일을 하나 뽑았습니다.");
        } else {
            JOptionPane.showMessageDialog(null, "중앙에 더 이상 뽑을 타일이 없습니다!");
        }
        observer.onGameStateChanged(gameState);
    }

    public GameUser getUser() {
        return user;
    }

    public Computer getComputer() {
        return computer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public List<Tile> getUserTiles() {
        return user.getTiles();
    }

    public void setCurrentPhase(GamePhase phase) {
        this.currentPhase = phase;
    }

    /**
     * 게임 종료 시 승리, 패배, 무승부 정보를 업데이트하고 점수 및 시간 계산을 처리합니다.
     */
    public void checkGameOver() {
        if (gameState.isGameOver()) {
            System.out.println("게임이 이미 종료되었습니다. checkGameOver를 종료합니다.");
            return; // 게임이 이미 종료된 경우 확인 중단
        }

        System.out.println("게임 종료 여부 확인 중. 현재 단계: " + currentPhase);
        System.out.println("tileManager.hasTiles(): " + tileManager.hasTiles());
        System.out.println("tileManager.allTilesGuessedCorrectly(): " + tileManager.allTilesGuessedCorrectly());

//        if (user.getTiles().size() == 13 && user.countGuessedCorrectly() == 13) {
//            System.out.println("플레이어가 모든 타일을 올바르게 맞추었습니다. 게임을 종료합니다.");
//            gameState.setGameOver(true);
//            gameState.setWinner("PLAYER");
//
//            observer.onGameStateChanged(gameState);
//            return;
//        }
//

//
//        if (tileManager.allTilesGuessedCorrectly()) {
//            System.out.println("모든 타일이 올바르게 맞추어졌습니다. 승자를 결정합니다.");
//            gameState.setGameOver(true);
//
//            int userCorrect = user.countGuessedCorrectly();
//            int computerCorrect = computer.countGuessedCorrectly();
//
//            if (userCorrect > computerCorrect) {
//                gameState.setWinner("PLAYER");
//            } else if (computerCorrect > userCorrect) {
//                gameState.setWinner("COMPUTER");
//            } else {
//                gameState.setWinner("DRAW");
//            }
//
//            observer.onGameStateChanged(gameState);
//            return;
//        }

        if (user.hasAllTilesOpened()) {
            System.out.println("컴퓨터가 승리 조건을 충족했습니다.");
            gameState.setGameOver(true);
            gameState.setWinner("COMPUTER");
            observer.onGameStateChanged(gameState);
            return;
        }

        if (computer.hasAllTilesOpened()) {
            System.out.println("플레이어가 승리 조건을 충족했습니다.");
            gameState.setGameOver(true);
            gameState.setWinner("PLAYER");

            observer.onGameStateChanged(gameState);
            return;
        }
        if (!tileManager.hasTiles()) {
            System.out.println("모든 중앙 타일이 선택되었습니다.");
            currentPhase = GamePhase.PLAYER_GUESS_PHASE;
            observer.onGameStateChanged(gameState);
            return;
        }
    }
}
