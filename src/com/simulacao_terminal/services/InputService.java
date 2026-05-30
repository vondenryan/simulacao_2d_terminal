package com.simulacao_terminal.services;

import com.simulacao_terminal.models.GameState;
import com.simulacao_terminal.models.Player;

public class InputService {
    private GameState gameState;
    private Player player;
    private int map[][];

    public InputService(GameState gameState, Player player, int map[][]) {
        this.gameState = gameState;
        this.player = player;
        this.map = map;
    }

    public void start() {
        Thread inputLoopThread = new Thread(this::inputProcessingLoop);
        inputLoopThread.start();
    }

    private void inputProcessingLoop() {
        while(gameState.isRunning()) {
            char keyPressed = '°';

            synchronized(gameState) {
                keyPressed = gameState.lastInput;
                gameState.lastInput = '°';
            }

            if(keyPressed == 'w' || keyPressed == 'W' || keyPressed == ' ') {
                int playerX = player.getGridX();
                int playerY = player.getGridY();

                if(map[playerY + 1][playerX] != 0) {
                    player.velY = player.JUMP_POWER;
                }
            } else if(keyPressed == 'd' || keyPressed == 'D') {
                player.velX += player.ACCELERATION;
            } else if(keyPressed == 'a' || keyPressed == 'A') {
                player.velX -= player.ACCELERATION;
            }

            //Calculate Deceleration
            if(keyPressed == '°' && player.velX != 0) {
                if(player.velX > 0) {
                    if(!player.onGround) {
                        player.velX -= player.DECELERATION / 2.5;
                        if(player.velX < 0) player.velX = 0;
                    } else {
                        player.velX -= player.DECELERATION;
                        if(player.velX < 0) player.velX = 0;
                    }
                } else {
                    if(!player.onGround) {
                        player.velX += player.DECELERATION / 2.5;
                        if(player.velX > 0) player.velX = 0;
                    } else {
                        player.velX += player.DECELERATION;
                        if(player.velX > 0) player.velX = 0;
                    }
                }
            }

            try {
                Thread.sleep(gameState.getFps());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
