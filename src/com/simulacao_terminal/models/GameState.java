package com.simulacao_terminal.models;

public class GameState {
    private boolean running;
    public char lastInput;
    private int fps;

    public GameState(boolean running, int fps) {
        this.running = running;
        this.fps = fps;
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
