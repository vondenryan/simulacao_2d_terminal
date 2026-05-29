package com.simulacao_terminal.utils;

public class PerlinNoise {
    private static final int[] p = new int[512];
    private static final int[] permutation = { 151,160,137,91,90,15,
    131,13,201,95,96,53,194,233, 7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
    190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
    88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
    77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
    102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
    135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
    5,202,38,147,118,126,255,82,85,212,207,206, 59,227,47,162,114,244,65,4,21,12,
    111,136,149,152,141,40,113,222,205,181,135,15,19,41,44,122,176,175,212,18,11,
    22,123,138,245,19,211,131,34,126,219,104,151,252,213,97,228,251,34,242,193,238,
    210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,49,192,214, 31,181,199,
    106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,138,236,205,93,222,114,67,
    29,24,72,243,141,128,195,78,66,215,61,156,180
    };

    static {
        for (int i=0; i<permutation.length; i++) p[256+i] = p[i] = permutation[i];
    }

    public static float noise(float x, float y) {
        int X = (int)Math.floor(x) & 255;
        int Y = (int)Math.floor(y) & 255;
        x -= Math.floor(x);
        y -= Math.floor(y);
        float u = fade(x);
        float v = fade(y);
        int A = p[X]+Y, AA = p[A], AB = p[A+1], B = p[X+1]+Y, BA = p[B], BB = p[B+1];

        return lerp(v, lerp(u, grad(p[AA], x, y), grad(p[BA], x-1, y)),
                       lerp(u, grad(p[AB], x, y-1), grad(p[BB], x-1, y-1)));
    }

    private static float fade(float t) { return t * t * t * (t * (t * 6 - 15) + 10); }
    private static float lerp(float t, float a, float b) { return a + t * (b - a); }
    private static float grad(int hash, float x, float y) {
        int h = hash & 7;
        float u = h<4 ? x : y;
        float v = h<4 ? y : x;
        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }
}
