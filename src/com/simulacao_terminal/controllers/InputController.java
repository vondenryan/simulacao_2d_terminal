package com.simulacao_terminal.controllers;

import com.simulacao_terminal.models.GameState;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import java.io.Reader;

public class InputController {
    private boolean running = true;
    private GameState gs;

    private Terminal terminal;
    private Reader reader;

    public InputController(GameState gs) {
        this.gs = gs;
    }

    public void start() {
        Thread inputReaderThread = new Thread(this::readInputs);
        inputReaderThread.start();
    }

    private void readInputs() {
        try {
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .jna(true)
                    .build();

            this.terminal.enterRawMode();
            this.reader = terminal.reader();

            while (running && gs.isRunning()) {
                int key = reader.read();
                if(key == -1) break;
                    
                char c = (char) key;

                if(key == 27) {
                    gs.setRunning(false);
                    break;
                }

                synchronized (gs) {
                    gs.lastInput = c;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            restoreTerminal();
        }
    }

    private void restoreTerminal() {
        try {
            if(terminal != null) {
                running = false;
                terminal.close();
            }
        } catch (Exception e) {

        }
    }
}
