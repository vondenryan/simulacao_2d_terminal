package com.simulacao_terminal.models;

public class GameState {
    private boolean running;
    public volatile char lastInput;

    private static final int target_fps = 30;
    private static final int frame_time = 1000 / target_fps;

    private int fps;

    public GameState(boolean running) {
        this.running = running;
        this.fps = frame_time;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}
