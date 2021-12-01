package com.vsu.cgcourse.vectormath;

public class Matrix3x3 extends Matrix {

    public Matrix3x3() {
        values = new float[3][3];
    }

    public Matrix3x3(final float[][] values) {
        if (values.length != 3 || values[0].length != 3) {
            throw new RuntimeException("Wrong dimension");
        }
        this.values = values;
    }

    public static Matrix createUnitaryMatrix3x3() {
        return createUnitaryMatrix(3);
    }
}
