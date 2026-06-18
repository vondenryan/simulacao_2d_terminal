package com.simulacao_terminal.controllers;

import com.simulacao_terminal.utils.PerlinNoise;
import java.util.Random;

public class MapController {
    private int map[][];
    private int mapWidth;
    private int mapHeight;
    private String terrainType;

    static Random random = new Random();

    public MapController(int mapHeight, int mapWidth, String terrainType) {
        this.map = new int[mapHeight][mapWidth];
        this.terrainType = terrainType;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;

        switch (terrainType) {
            case "flat":
                flatMap();
                break;
            case "natural":
                naturalGeneration();
                break;
            default:
                //Assign flat as default
                flatMap();
        }
    }

    private void flatMap() {
        for(int lin = 0; lin < map.length; lin++) {
            for(int col = 0; col < map[lin].length; col++) {
                if(lin > map.length / 2) {
                    if(Math.random() > 0.5) {
                        map[lin][col] = 1;
                    } else {
                        map[lin][col] = 2;
                    }
                }
            }
        }
    }
    
    private void naturalGeneration() {
        float surfaceScale = 0.02f;
        float caveScale = 0.025f; // Controls cave size/frequency
        
        double offsetX = Math.random() * 300;
        double offsetY = Math.random() * 300;
    
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                
                double surfaceNoise = PerlinNoise.noise((float)(offsetX + (x * surfaceScale)), 0.6f);
                int groundLevel = map.length / 2 + (int)(surfaceNoise * 8);

                if (y > groundLevel) {
                    float cX = (float)(offsetX + (x * caveScale));
                    float cY = (float)(offsetY + (y * caveScale * 2.0f));
                    float caveNoise = (PerlinNoise.noise(cX, cY) + 1.0f) / 2.0f;
    
                    if (y > groundLevel + 15 && caveNoise > 0.68f) {
                        map[y][x] = 0; // Cave air
                    } 
                    else {
                        if (y == groundLevel + 1) {
                            map[y][x] = 1; // Grass surface
                        } else if (y <= groundLevel + 5) {
                            map[y][x] = 2; // Shallow Dirt layer
                        } else {
                            map[y][x] = 4; // Deep Stone layer
                        }
                    }
                } else {
                    map[y][x] = 0; // Sky Air
                }
            }
        }
    }

    public int[] spawnPlayer(int m[][]) {
        int minX = 1;
        int maxX = m[0].length - 1;
        int x = random.nextInt((maxX - minX) + 1) + minX;
    
        int y = 0;
    
        for (int row = 0; row < m.length - 1; row++) {
            if (m[row][x] == 0 && m[row + 1][x] != 0) {
                y = row;
                break;
            }
        }
    
        if (y == 0) {
            y = m.length / 2;
        }
    
        return new int[] {y, x};
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public String getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
}
