package com.vsu.cgcourse.vectormath;


public class Vector2f extends Vector {

    public Vector2f() {
        coordinates = new float[2];
    }

    public Vector2f(final float[] coordinates) {
        if (coordinates.length != 2) {
            throw new RuntimeException("Wrong dimension");
        }
        this.coordinates = coordinates;
    }

    public Vector2f(float x, float y) {
        coordinates = new float[2];
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public float getX() {
        return coordinates[0];
    }

    public float getY() {
        return coordinates[1];
    }
}
