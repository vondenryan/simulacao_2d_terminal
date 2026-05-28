package com.simulacao_terminal.engine;

import com.simulacao_terminal.models.GameState;
import com.simulacao_terminal.models.Player;
import com.simulacao_terminal.utils.Utils;

public class GraphicEngine {
    private Utils utils = new Utils();
    private volatile String frameToRender = "";

    private int map[][];
    private Player player;
    private GameState gs;

    public GraphicEngine(int map[][], Player player, GameState gs) {
        this.map = map;
        this.player = player;
        this.gs = gs;
    }

    public void start() {
        Thread renderThread = new Thread(this::renderLoop);
        renderThread.start();
        
        Thread gameLoopThread = new Thread(this::mainLoop);
        gameLoopThread.start();
    }

    private String displayConfig[] = {
        " ", //0 - Air
        "\033[32m■\033[0m", //1 - Grass
        "\033[90m■\033[0m" //2 - Rock
    };

    private void mainLoop() {
        while(gs.isRunning()) {
            updatePhysics();
            
            int pY = player.getGridY();
            int pX = player.getGridX();
            
            int heightVisionRange = 25;
            int lenghtVisionRange = 90;
            
            int leftSide = Math.max(0, pX - lenghtVisionRange);
            int rightSide = Math.min(map[0].length, pX + lenghtVisionRange);
            
            int topSide = Math.max(0, pY - heightVisionRange);
            int bottomSide = Math.min(map.length, pY + heightVisionRange);
            
            StringBuilder frame = new StringBuilder();
    
            for(int lin = topSide; lin < bottomSide; lin++) {
                for(int col = leftSide; col < rightSide; col++) {
                    if(col == player.getGridX() && lin == player.getGridY()) {
                        frame.append("\033[31mO\033[0m");
                    } else {
                        int blockVal = map[lin][col];

                        if(blockVal < 0 || blockVal >= displayConfig.length) {
                            frame.append("\033[35m?\033[0m");
                        } else {
                            String blockId = displayConfig[blockVal];
                            frame.append(blockId);
                        }
                    }
                }
                frame.append("\n");
            }
    
            frameToRender = frame.toString();
    
            try {
                Thread.sleep(33);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void renderLoop() {
        String lastFrame = "";

        while(gs.isRunning()) {
            if(!frameToRender.equals(lastFrame) && !frameToRender.isEmpty()) {
                utils.resetConsolePosition();
                System.out.print(frameToRender);
                lastFrame = frameToRender;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePhysics() {
        if(!player.onGround) {
            player.velY += player.GRAVITY / 2;

            if(player.velY > 0.9f) {
                player.velY = 0.9f;
            }
        }

        float nextY = player.y + player.velY;
        int gridX = player.getGridX();

        int footPos = Math.round(nextY);

        if(footPos >= 0 && footPos < map.length) {
            if(map[footPos][gridX] != 0) {
                if(player.velY > 0) {
                    player.y = footPos - 1;
                    player.onGround = true;
                } else if(player.velY < 0) {
                    player.y = footPos + 1;
                }
                player.velY = 0;
            } else {
                player.y = nextY;
                player.onGround = false;
            }
        }
    }
}