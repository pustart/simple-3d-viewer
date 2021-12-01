package com.vsu.cgcourse.vectormath;


public class Vector3f extends Vector {

    public Vector3f() {
        coordinates = new float[3];
    }

    public Vector3f(final float[] coordinates) {
        if (coordinates.length != 3) {
            throw new RuntimeException("Wrong dimension");
        }
        this.coordinates = coordinates;
    }

    public Vector3f(float x, float y, float z) {
        coordinates = new float[3];
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
    }

    public static float[] crossProduct(final float[] coordinates1, final float[] coordinates2) {
        if (coordinates1 == null || coordinates2 == null) {
            throw new RuntimeException("Exception.");
        }
        float[] result = new float[3];
        result[0] = coordinates1[1] * coordinates2[2] - coordinates1[2] * coordinates2[1];
        result[1] = coordinates1[2] * coordinates2[0] - coordinates1[0] * coordinates2[2];
        result[2] = coordinates1[0] * coordinates2[1] - coordinates1[1] * coordinates2[0];
        return result;
    }

    public Vector3f crossProduct(final Vector3f vector) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return new Vector3f(crossProduct(coordinates, vector.coordinates));
    }

    public static Vector3f crossProduct(final Vector3f vector1, final Vector3f vector2) {
        if (vector1 == null) {
            throw new RuntimeException("Exception.");
        }
        return vector1.crossProduct(vector2);
    }
}
