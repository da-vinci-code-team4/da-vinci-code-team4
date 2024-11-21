package game.save;

import game.player.Player;
import game.status.Status;

/**
 * 각 턴에서 발생한 정보가 기록되는 클래스다.
 */
public class Record {

    /**몇번 째 턴인지 저장*/
    private final int turn;

    /**어떤 플레이어의 턴인지 저장*/
    private final Player player;

    /**각 턴 상황을 저장*/
    private final Status status;

    /**
     * @param turn 몇번 째 턴인지 입력 받는다
     * @param player 어떤 플레이어의 턴인지 입력 받는다
     */
    private Record(int turn, Player player, Status status) {
        this.turn = turn;
        this.player = player;
        this.status = status;
    }

    /**
     * @param turn 몇번 째 턴인지 입력 받는다
     * @param player 어떤 플레이어의 턴인지 입력 받는다
     * @param status 게임 턴 별 상황을 입력 받는다
     * @return 생성된 객체를 반환한다
     */
    public static Record of(int turn, Player player, Status status) {
        return new Record(turn, player, status.clone());
    }
}
