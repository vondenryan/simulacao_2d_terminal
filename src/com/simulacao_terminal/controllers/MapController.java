package com.simulacao_terminal.controllers;

import java.util.Random;
import java.util.Scanner;

import com.simulacao_terminal.utils.PerlinNoise;

public class MapController {
    static Scanner in = new Scanner(System.in);
    static Random random = new Random();

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

    private int[][] naturalGeneration(int m[][]) {
        float surfaceScale = 0.02f;
        float caveScale = 0.05f; // Controls cave size/frequency
        
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
    
                    if (y > groundLevel + 2 && caveNoise > 0.68f) {
                        m[y][x] = 0; // Cave air
                    } 
                    else {
                        if (y == groundLevel + 1) {
                            m[y][x] = 1; // Grass surface
                        } else if (y <= groundLevel + 5) {
                            m[y][x] = 2; // Shallow Dirt layer
                        } else {
                            m[y][x] = 4; // Deep Stone layer
                        }
                    }
                } else {
                    m[y][x] = 0; // Sky Air
                }
            }
        }
        return m;
    }

    //! Player WorldPos Controller Methods

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
