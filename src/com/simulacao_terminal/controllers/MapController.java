package com.simulacao_terminal.controllers;

import com.simulacao_terminal.utils.PerlinNoise;
import java.util.Random;
import java.util.Scanner;

/**
 * Terrain generation with Perlin noise and player spawning.
*/
public class MapController {
    static Scanner in = new Scanner(System.in);
    static Random random = new Random();

    /**
     * Organizes the map generation logic
     * 
     * @param y                 Number of rows (height) in the map
     * @param x                 Number of columns (width) in the map
     * @param generationType    1 = flat, 2 = natural with Perlin noise
     * @return                  2D array representing the terrain map
     */
    public int[][] generateMap(int y, int x, int generationType) {
        int map[][] = new int[y][x];

        switch (generationType) {
            case 1:
                map = flatMap(map);
                break;
            case 2:
                map = naturalGeneration(map);
                break;
            default:
                System.out.println("EROR! Invalid option!");
                return new int[0][0];
        }

        return map;
    }

    //! Generation Types

    /**
     * Generates a terrain map with the specified dimensions and generation type.
     * 
     * @param m     Map matrix
     * @return      2D array representing the terrain map
    */
    private int[][] flatMap(int m[][]) {
        for(int lin = 0; lin < m.length; lin++) {
            for(int col = 0; col < m[lin].length; col++) {
                if(lin > m.length / 2) {
                    if(Math.random() > 0.5) {
                        m[lin][col] = 1;
                    } else {
                        m[lin][col] = 2;
                    }
                }
            }
        }

        return m;
    }

    /**
     * Generates a terrain map with the specified dimensions and generation type.
     *
     * @param m     Map matrix
     * @return      2D array representing the terrain map
    */
    private int[][] naturalGeneration(int m[][]) {
        float surfaceScale = 0.02f;
        float caveScale = 0.05f; // Controls cave size/frequency
        
        //todo Make offset seed based either than random based
        double offsetX = Math.random() * 300;
        double offsetY = Math.random() * 300;
    
        for (int y = 0; y < m.length; y++) {
            for (int x = 0; x < m[y].length; x++) {
                
                double surfaceNoise = PerlinNoise.noise((float)(offsetX + (x * surfaceScale)), 0.6f);
                int groundLevel = m.length / 2 + (int)(surfaceNoise * 8);
    
                if (y > groundLevel) {
                    float cX = (float)(offsetX + (x * caveScale));
                    float cY = (float)(offsetY + (y * caveScale * 2.0f));
                    float caveNoise = (PerlinNoise.noise(cX, cY) + 1.0f) / 2.0f;
    
                    if (y > groundLevel + 15 && caveNoise > 0.68f) {
                        m[y][x] = 0; // Cave air
                    } 
                    else {
                        if (y == groundLevel + 1) {
                            // Grass surface
                            m[y][x] = 1;
                        } else if (y <= groundLevel + 5) {
                            // Shallow Dirt layer
                            m[y][x] = 2;
                        } else {
                            // Deep Stone layer
                            m[y][x] = 4;
                        }
                    }
                } else {
                    // Sky Air
                    m[y][x] = 0;
                }
            }
        }
        return m;
    }

    //! Player WorldPos Controller Methods

    /**
     * Finds a valid spawn position for the player on the terrain surface.
     * 
     * @param m     The generated terrain map
     * @return      Array with [y, x] coordinates for player spawn
     */
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
}
