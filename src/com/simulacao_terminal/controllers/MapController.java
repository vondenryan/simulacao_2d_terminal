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
        float scale = 0.04f;

        float heightOffsetX = (float) (Math.random() * 10000);
        float heightOffsetY = (float) (Math.random() * 10000);

        float moistureOffsetX = (float) (Math.random() * 10000) + 5000;
        float moistureOffsetY = (float) (Math.random() * 10000) + 5000;

        for(int y = 0; y < m.length; y++) {
            for(int x = 0; x < m[y].length; x++) {
                float hX = (x * scale) + heightOffsetX;
                float hY = (y * scale * 2.0f) + heightOffsetY;
                float heightValue = (PerlinNoise.noise(hX, hY) + 1.0f) / 2.0f;

                float mX = (x * (scale * 0.8f)) + moistureOffsetX;
                float mY = (y * (scale * 0.8f) * 2.0f) + moistureOffsetY;
                float moistureValue = (PerlinNoise.noise(mX, mY) + 1.0f) / 2.0f;
                
                if(heightValue < 0.40f) {
                    m[y][x] = 0;
                } else if(heightValue < 0.75f) {
                    m[y][x] = 5;
                } else {
                    if(moistureValue < 0.35f) {
                        m[y][x] = 3;
                    } else if(moistureValue < 0.70f) {
                        m[y][x] = 1;
                    } else {
                        m[y][x] = 2;
                    }
                }
            }
        }
        return m;
    }

    //! Player WorldPos Controller Methods

    public int[] spawnPlayer(int m[][]) {
        int minY = 1;
        int maxY = m.length - 1;

        int minX = 1;
        int maxX = m[0].length - 1;

        boolean sucessfullyPlaced = false;

        int y = 0, x = 0;
        int attempts = 0;

        while (!sucessfullyPlaced) {
            y = random.nextInt((maxY - minY) + 1) + minY;
            x = random.nextInt((maxX - minX) + 1) + minX;
            attempts++;
            
            if(m[y][x] == 0) {
                if(m[y + 1][x] >= 1 && m[y + 1][x] <= 5) {
                    sucessfullyPlaced = true;
                }
            }

            if(attempts >= 2000 && m[y][x] == 0) {
                sucessfullyPlaced = true;
            }
        }

        return new int[] {y, x};
    }
}
