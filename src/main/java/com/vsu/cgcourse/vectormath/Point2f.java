package com.vsu.cgcourse.vectormath;

public class Point2f {
    float[] coordinates = new float[2];

    public Point2f(float x, float y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    public float getX() {
        return coordinates[0];
    }

    public float getY() {
        return coordinates[1];
    }

}
