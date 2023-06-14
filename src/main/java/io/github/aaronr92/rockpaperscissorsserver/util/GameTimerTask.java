package io.github.aaronr92.rockpaperscissorsserver.util;

import java.util.TimerTask;

public abstract class GameTimerTask extends TimerTask {

    private final int remainingTime;

    public GameTimerTask(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

}
