package game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {

    private int time;
    private boolean isPaused = false;
    private boolean gameRunning = true;

    Timer(int time) {
        this.time = time;
    }

    public static void main(String[] args) {
        Timer timer = new Timer(0);
        timer.startWatch();
    }

    /**
     * 전체 게임시간을 측정하는 시계 메서드
     * */
    public void startWatch() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (gameRunning && !isPaused) {
                time++;
                System.out.println("Time: " + time / 60 + " : " + time % 60);
            } else if (isPaused) {
                System.out.println("게임이 일시 정지되었습니다.");
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    /**
     * 카운트다운 타이머 메서드
     * */
    public void startTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            if (gameRunning && !isPaused) {
                if (time > 0) {
                    System.out.println("남은 시간: " + time + "초");
                    time--;
                } else {
                    System.out.println("게임 오버!");
                    gameRunning = false;  // 게임 종료
                    scheduler.shutdown();
                }
            } else if (isPaused) {
                System.out.println("게임이 일시 정지되었습니다.");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void pauseTimer() {
        isPaused = true;
    }

    public void resumeTimer() {
        isPaused = false;
    }

    public void endTimer() {
        gameRunning = false;
    }

}