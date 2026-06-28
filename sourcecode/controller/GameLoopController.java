package controller;

import javax.swing.Timer;

public class GameLoopController {
    private final Timer timer;

    public GameLoopController(Runnable gameTick) {
        timer = new Timer(500, e -> gameTick.run());
    }

    public void start() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void stop() {
        timer.stop();
    }

    public void setDelay(int ms) {
        timer.setDelay(ms);
    }
}
