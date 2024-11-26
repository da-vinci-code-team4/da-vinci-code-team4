package com.example.project.game.save;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임에서 각 턴을 기록하는 클래스다.
 * 리플레이 시 사용된다.
 */
public class Recorder {

    /**게임의 턴 기록이 저장되어 있다*/
    private final List<Record> gameRecords = new ArrayList<>();

    /**
     * 턴 기록을 저장할 때 사용되는 메서드다.
     *
     * @param record 각 턴 마다 발생한 정보를 입력 받는다
     */
    public void save(Record record) {
        gameRecords.add(record);
    }
}
