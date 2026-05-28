package com.simulacao_terminal.controllers;

import java.util.Random;
import java.util.Scanner;

public class MapController {
    static Scanner in = new Scanner(System.in);
    static Random random = new Random();

    public int[][] generateMap(int y, int x, int generationType) {
        int map[][] = new int[y][x];

        switch (generationType) {
            case 1:
                map = flatMap(map);
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

    //! Player WorldPos Controller Methods

    public int[] spawnPlayer(int m[][]) {
        int minY = 1;
        int maxY = m.length - 1;

        int minX = 1;
        int maxX = m[0].length - 1;

        boolean sucessfullyPlaced = false;

        int y = 0, x = 0;

        while (!sucessfullyPlaced) {
            y = random.nextInt((maxY - minY) + 1) + minY;
            x = random.nextInt((maxX - minX) + 1) + minX;
            
            if(m[y][x] == 0) {
                if(m[y + 1][x] == 1 || m[y + 1][x] == 2) {
                    sucessfullyPlaced = true;
                }
            }
        }

        return new int[] {y, x};
    }

    public int[][] movePlayer(int pPos[], int m[][], char direction) {
        int y = pPos[0];
        int x = pPos[1];

        switch (direction) {
            case 'R':
                if(x + 1 < m[0].length) {
                    if(m[y][x + 1] == 0) {
                        m[y][x] = 0;
                        m[y][x + 1] = 3;
                    }
                }
                break;
            case 'L':
                if(x - 1 >= 0) {
                    if(m[y][x - 1] == 0) {
                        m[y][x] = 0;
                        m[y][x - 1] = 3;
                    }
                }
                break;
            default:
                break;
        }

        return m;
    }
}
