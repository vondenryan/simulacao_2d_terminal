package com.simulacao_terminal.models;

public class GameState {
    private boolean running;
    public char lastInput;

    public GameState(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
