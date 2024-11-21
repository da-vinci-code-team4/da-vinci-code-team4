package game.status;

import game.player.Computer;
import game.player.Player;
import game.player.User;
import game.tile.Tile;

import static game.status.TurnResult.*;

public class Status {

    int cntRemain;
    int cntOpponentCorrectTile;
    int cntMyCorrectTile;
    int cntOpponentRemainTile;
    int cntMyRemainTile;
    private Tile drawTile;
    private Tile selectTile;
    private Tile guessTile;
    private Tile openTile;

    public Status() {
        this.cntRemain = 0;
        this.cntOpponentCorrectTile = 0;
        this.cntMyCorrectTile = 0;
        this.cntOpponentRemainTile = 0;
        this.cntMyRemainTile = 0;
    }

    /**
     * 턴 결과를 저장하는 메서드
     *
     * @param result MATCH면 상대 타일을 맞춘 것 FAIL 이면 못 맞춤
     * @param player 현재 턴을 진행한 플레이어
     */
    public void saveResult(TurnResult result, Player player) {
        if (result.equals(MATCH)) {
            if (player instanceof User) {
                subCntOpponentRemainTile();
                addCntMyCorrectTile();
            } else if (player instanceof Computer) {
                subCntMyRemainTile();
                addCntOpponentRemainTile();
            }
        }
    }

    /**
     * 턴 마다 뽑은 타일을 저장하는 메서드
     *
     * @param tile 턴마다 뽑은 타일
     */
    public void saveDrawTile(Tile tile) {
        drawTile = tile;
    }

    /**
     * 플레이어가 고른 상대방 타일을 저장하는 메서드.
     *
     * @param tile 플레이어가 고른 상대방 타일
     */
    public void saveSelectTile(Tile tile) {
        selectTile = tile;
    }

    /**
     * 플레이어가 예상한 타일 정보를 저장하는 메서드
     *
     * @param clone 플레이어가 예상한 타일
     */
    public void saveGuessTile(Tile clone) {
        guessTile = clone;
    }

    /**
     * 턴에서 공개된 타일을 저장하는 메서드.
     *
     * @param tile 턴에서 공개된 타일
     */
    public void saveOpenTile(Tile tile) {
        openTile = tile;
    }

    /**
     * number 만큼 분배되지 않은 전체 남은 타일 개수를 감소시키는 메서드
     *
     * @param number number 횟수만큼 감소시킴 -> 초기에 4장 분배 시
     */
    public void updateCntRemain(int number) {
        for (int i = 0; i < number; i++) {
            this.cntRemain--;
        }
    }

    /**
     * 상대가 맞춘 내 타일 개수를 증가시킵니다
     */
    public void addCntOpponentCorrectTile() {
        this.cntOpponentCorrectTile++;
    }

    /**
     * 내가 맞춘 상대 타일 개수를 증가시킵니다
     */
    public void addCntMyCorrectTile() {
        this.cntMyCorrectTile++;
    }

    /**
     * 상대의 남은 타일 개수 개수를 증가시킵니다
     */
    public void addCntOpponentRemainTile() {
        this.cntOpponentRemainTile++;
    }

    /**
     * 내 남은 타일 개수를 증가시킵니다
     */
    public void addCntMyRemainTile() {
        this.cntMyRemainTile++;
    }

    /**
     * 상대의 남은 타일 개수 개수를 감소시킵니다
     */
    public void subCntOpponentRemainTile() {
        this.cntOpponentRemainTile--;
    }

    /**
     * 내 남은 타일 개수를 감소시킵니다
     */
    public void subCntMyRemainTile() {
        this.cntMyRemainTile--;
    }

    /**
     * 모든 타일이 맞춰져서 오픈되었는지 확인 후 boolean 값을 리턴합니다.
     */
    public boolean isAllTileOpen() {
        return cntRemain == 0;
    }

}