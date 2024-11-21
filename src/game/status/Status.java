package game.status;

import game.tile.Tile;

public class Status {

    private Tile drawTile;
    private Tile selectedTile;

    public void saveDrawTile(Tile tile) {
        drawTile = tile;
    }

    public void saveSelectedTile(Tile tile) {
        selectedTile = tile;
    }
    public void saveGuessedTile(Tile clone) {

    }
    public void saveResult(boolean b) {

    }

    public void saveOpenedTile(Tile tile) {

    }
    int cntRemain;
    int cntOpponentCorrectTile;

    int cntMyCorrectTile;

    int cntOpponentRemainTile;
    int cntMyRemainTile;

    public Status() {
        this.cntRemain = 0;
        this.cntOpponentCorrectTile = 0;
        this.cntMyCorrectTile = 0;
        this.cntOpponentRemainTile = 0;
        this.cntMyRemainTile = 0;
    }
    //분배 해야할 남은 타일 수 업데이트

    public void updateCntRemain(int number) {
        for (int i = 0; i < number; i++) {
            this.cntRemain--;
        }
    }
    //상대가 맞춘 내 타일 개수 업데이트

    public void updateCntOpponentCorrectTile() {
        this.cntOpponentCorrectTile++;
    }
    //내가 맞춘 상대 타일 개수 업데이트

    public void updateCntMyCorrectTile() {
        this.cntMyCorrectTile++;
    }
    //상대 남은 타일 개수 업데이트 (+)

    public void updateCntOpponentRemainTile(int number) {
        this.cntOpponentRemainTile++;
    }
    //내 남은 타일 개수 업데이트 (+)

    public void updateCntMyRemainTile(int number) {
        this.cntMyRemainTile++;
    }
    //상대 남은 타일 개수 업데이트 (-)

    public void updateCntOpponentRemainTile() {
        this.cntOpponentRemainTile--;
    }
    //내 남은 타일 개수 업데이트 (-)

    public void updateCntMyRemainTile() {
        this.cntMyRemainTile--;
    }

    //모든 타일이 맞춰졌나요?

    public boolean isAllTileOpened() {
        return cntRemain == 0;
    }


}
