package com.vsu.cgcourse.vectormath;

public class Matrix4x4 extends Matrix {

    public Matrix4x4() {
        values = new float[4][4];
    }

    public Matrix4x4(final float[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new RuntimeException("Wrong dimension");
        }
        this.values = values;
    }

    public float get(int row, int column) {
        return values[row][column];
    }

    public static Matrix createUnitaryMatrix4x4() {
        return createUnitaryMatrix(4);
    }
}
