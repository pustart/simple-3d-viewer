package com.vsu.cgcourse.vectormath;


public class Vector4f extends Vector {

    public Vector4f() {
        coordinates = new float[4];
    }

    public Vector4f(final float[] coordinates) {
        if (coordinates.length != 4) {
            throw new RuntimeException("Wrong dimension");
        }
        this.coordinates = coordinates;
    }

    public Vector4f(float x, float y, float z, float h) {
        coordinates = new float[4];
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
        coordinates[3] = h;
    }
}
