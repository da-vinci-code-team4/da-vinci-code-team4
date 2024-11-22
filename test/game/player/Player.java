package main.game.player;

import static main.game.tiles.TileType.JOKER;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.game.tiles.Tile;
import main.game.tiles.TileManager;

public abstract class Player {

    private TileManager tileManager;
    protected String name;
    protected int rating;
    protected ArrayList<Tile> deck = new ArrayList<>();

    //생성자
    public Player(TileManager tileManager, String name, int rating, ArrayList<Tile> deck){
        this.tileManager = tileManager;
        this.name = name;
        this.rating = rating;
        this.deck = deck;
    }

    /**
     * 플레이어가 턴을 시작하면 호출
     *
     * @param record 턴을 시작하고 게임 내용을 기록할 객체
     * @return 턴 유지 : true, 턴 종료 : false
     */
    public boolean startTurn(Record record){
        //덱에서 타일 뽑기
        drawTile();
        
        //맞출 타일 선택하기
        Tile selectedTile = getSelectedTile();
        
        if (selectedTile.equals()) {
            //만약 같으면 chooseToKeepTurn() 호출
            if(keepTurn()){
                return true;
            }
        }

        return false;
    };

    /**
     * 게임 시작 시 타일을 메니져에게 분배 받을 때 호출되는 메서드다.
     * 만약 조커 타일이 있다면 사용자가 원하는 위치에 조커 타일을 둔다.
     *
     * @param tiles 분배된 타일을 입력 받는다
     */
    public void giveInitTile(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.compareTileType(JOKER)) { //조커 타일이면 사용자로부터 위치 입력 받음
                //TODO : 컨트롤러에서 getIndex()함수로 받아오기
                int pos = 0;

                insertTile(tile, pos);
                tile.setWeight(pos);
            }

            deck.add(tile);
        }
    }

    private void drawTile(){
        Optional<Tile> newTile = tileManager.drawTile();
        if (newTile.isEmpty()) { //덱이 비어있으면 return
            return;
        }
    }

    //deck에 일반 타일 삽입
    public void insertTile(Tile tile){
        deck.add(tile);
    }    
    
    //deck에 조커 타일 삽입
    public void insertTile(Tile tile, int index){
        deck.add(index, tile);
    };

    //Tile 공개
    public void openTile(Tile tile){
        tile.setOpen(true);
    }

    //deck 사이즈 반환
    public int getDeckSize(){
        return deck.size();
    }

    //deck의 특정 위치의 타일 반환
    public Tile getTile(int index){
        return deck.get(index);
    }

}
