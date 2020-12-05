package com.example.mathgame_ex;

public class Balloon {
    int x, y;
    int speed;

    Balloon(int x, int y, int speed) {
        this.x = x; this.y =y;
        this.speed = speed;
    }

    public void move() {
        y+=speed;
    }
}
