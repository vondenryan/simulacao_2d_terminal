package com.simulacao_terminal.models;

public class Player {
    public float x;
    public float y;
    
    public float velY = 0f;
    public float velX = 0f;

    public final float GRAVITY = 0.2f;
    public final float JUMP_POWER = -0.9f;
    public final float ACCELERATION = 0.9f;
    public final float DECELERATION = 0.4f;

    public boolean onGround = true;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    public int getGridX() {
        return Math.round(x);
    }

    public int getGridY() {
        return Math.round(y);
    }
}
