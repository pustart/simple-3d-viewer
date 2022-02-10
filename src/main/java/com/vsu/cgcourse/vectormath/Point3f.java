package com.vsu.cgcourse.vectormath;

public class Point3f {
    float[] coordinates = new float[3];

    public Point3f(float x, float y, float z) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
        this.coordinates[2] = z;
    }

    public float getX() {
        return coordinates[0];
    }

    public float getY() {
        return coordinates[1];
    }

    public float getZ() {
        return coordinates[2];
    }

    public void setX(float x) {
        coordinates[0] = x;
    }

    public void setY(float y) {
        coordinates[1] = y;
    }

    public void setZ(float z) {
        coordinates[2] = z;
    }
    public void set(float x, float y, float z) {
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
    }
}
