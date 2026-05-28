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
        map = mapController.generateMap(2000, 7000, 1);
        
        //Player starting
        int pPos[] = mapController.spawnPlayer(map);
        Player player = new Player(pPos[1], pPos[0]);

        //GameState starter
        GameState gameState = new GameState(true);

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
                // if(player.onGround) {
                //     player.velY = player.JUMP_POWER;
                // }

                player.velY = player.JUMP_POWER;
            } else if(keyPressed == 'd' || keyPressed == 'D') {
                //todo Lógica de andar
                player.x += 1;
            } else if(keyPressed == 'a' || keyPressed == 'A') {
                //todo Lógica de andar
                player.x -= 1;
            }

            try {
                Thread.sleep(33);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
