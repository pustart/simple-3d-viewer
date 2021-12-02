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

    @Override
    public void mul(Matrix m1) {
        super.mul(m1);
    }

    public static Matrix createUnitaryMatrix4x4() {
        return createUnitaryMatrix(4);
    }

    public float getM00() {
        return values[0][0];
    }

    public float getM01() {
        return values[0][1];
    }

    public float getM02() {
        return values[0][2];
    }

    public float getM03() {
        return values[0][3];
    }

    public float getM10() {
        return values[1][0];
    }

    public float getM11() {
        return values[1][1];
    }

    public float getM12() {
        return values[1][2];
    }

    public float getM13() {
        return values[1][3];
    }

    public float getM20() {
        return values[2][0];
    }

    public float getM21() {
        return values[2][1];
    }

    public float getM22() {
        return values[2][2];
    }

    public float getM23() {
        return values[2][3];
    }

    public float getM30() {
        return values[3][0];
    }

    public float getM31() {
        return values[3][1];
    }

    public float getM32() {
        return values[3][2];
    }

    public float getM33() {
        return values[3][3];
    }

    public void setM00(float value) {
        values[0][0] = value;
    }

    public void setM11(float value) {
        values[1][1] = value;
    }

    public void setM22(float value) {
        values[2][2] = value;
    }

    public void setM23(float value) {
        values[2][3] = value;
    }

    public void setM032(float value) {
        values[3][2] = value;
    }

}
