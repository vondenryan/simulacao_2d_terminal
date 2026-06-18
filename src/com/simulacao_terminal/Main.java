package com.simulacao_terminal;

import com.simulacao_terminal.controllers.InputController;
import com.simulacao_terminal.controllers.MapController;
import com.simulacao_terminal.services.InputService;
import com.simulacao_terminal.engine.GraphicEngine;
import com.simulacao_terminal.models.GameState;
import com.simulacao_terminal.models.Player;
import com.simulacao_terminal.utils.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Utils utils = new Utils();
        
        //Map Generation
        MapController map = new MapController(2000, 2000, "natural");
        
        //Player starting
        int pPos[] = map.spawnPlayer();
        Player player = new Player(pPos[1], pPos[0]);

        //GameState starter
        GameState gameState = new GameState(true); //~30 Fps

        //Input controller starter
        InputController inputController = new InputController(gameState);
        inputController.start();    

        //Graphics engine starter
        GraphicEngine renderer = new GraphicEngine(map, player, gameState);
        utils.clearConsole();
        renderer.start();

        //Input processor
        InputService inputService = new InputService(gameState, player, map);
        inputService.start();
    }
}
