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

    public static Matrix createUnitaryMatrix4x4() {
        return createUnitaryMatrix(4);
    }

    public void mul4x3(Matrix3x3 matrix3x3) {
        float[][] newArray = new float[values.length][values.length];
        for (int i = 0; i < matrix3x3.getValues().length; i++) {
            for (int j = 0; j < matrix3x3.getValues().length; j++) {
                newArray[i][j] = matrix3x3.getElement(i, j);
            }
        }
        newArray[0][3] = 0;
        newArray[1][3] = 0;
        newArray[2][3] = 0;
        newArray[3][3] = 1;
        newArray[3][0] = 0;
        newArray[3][1] = 0;
        newArray[3][2] = 0;
        Matrix4x4 newMatrix = new Matrix4x4(newArray);
        this.mul(newMatrix);
    }

}
