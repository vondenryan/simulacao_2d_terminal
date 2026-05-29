package com.simulacao_terminal;

import com.simulacao_terminal.controllers.InputController;
import com.simulacao_terminal.controllers.MapController;
import com.simulacao_terminal.engine.GraphicEngine;
import com.simulacao_terminal.models.GameState;
import com.simulacao_terminal.models.Player;
import com.simulacao_terminal.utils.*;

public class Main {
    public static void main(String[] args) throws Exception {
        MapController mapController = new MapController();
        Utils utils = new Utils();
        
        //Map Generation
        int map[][] = {};
        map = mapController.generateMap(2000, 10000, 2);
        
        //Player starting
        int pPos[] = mapController.spawnPlayer(map);
        Player player = new Player(pPos[1], pPos[0]);

        //GameState starter
        GameState gameState = new GameState(true, 33); //~30 Fps

        //Input controller starter
        InputController inputController = new InputController(gameState);
        inputController.start();    

        //Graphics engine starter
        GraphicEngine renderer = new GraphicEngine(map, player, gameState);
        utils.clearConsole();
        renderer.start();

        //Input processor
        while (gameState.isRunning()) {
            char keyPressed = '°';

            synchronized (gameState) {
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
