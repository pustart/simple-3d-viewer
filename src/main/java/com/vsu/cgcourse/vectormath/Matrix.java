package com.vsu.cgcourse.vectormath;


import java.util.Arrays;

public class Matrix {
    protected float[][] values;
    public static final float eps = 1e-7f;

    public Matrix() {
    }

    public Matrix(int rows, int columns) {
        values = new float[rows][columns];
    }

    public Matrix(final float[][] values) {
        this.values = values;
    }

    public float[][] getValues() {
        return values;
    }

    public void setValues(final float[][] values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() && getClass() != o.getClass().getSuperclass()) return false;
        Matrix matrix = (Matrix) o;
        if (values.length != matrix.values.length || values[0].length != matrix.values[0].length) {
            return false;
        }
        boolean result = true;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                result = result && (Math.abs(values[i][j] - matrix.values[i][j]) < eps);
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    public String toString() {
        final StringBuffer str = new StringBuffer();
        int i = 0, j = 0;
        for (i = 0; i < values.length; i++) {
            for (j = 0; j < values[i].length; j++) {
                str.append(values[i][j] + "  ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public String toString(final Matrix matrix) {
        return matrix.toString();
    }


    public static float[][] addition(final float[][] values1, final float[][] values2) {
        if (values1.length != values2.length || values1[0].length != values2[0].length || values1 == null || values2 == null) {
            throw new RuntimeException("Exception.");
        }
        float[][] result = new float[values1.length][values1[0].length];
        for (int i = 0; i < values1.length; i++) {
            for (int j = 0; j < values1[0].length; j++) {
                result[i][j] = values1[i][j] + values2[i][j];
            }
        }
        return result;
    }

    public Matrix addition(final Matrix matrix) {
        if (matrix == null) {
            throw new RuntimeException("Exception.");
        }
        return new Matrix(addition(values, matrix.values));
    }

    public static Matrix addition(final Matrix matrix1, final Matrix matrix2) {
        if (matrix1 == null) {
            throw new RuntimeException("Exception.");
        }
        return matrix1.addition(matrix2);
    }

    public static float[][] subtraction(final float[][] values1, final float[][] values2) {
        if (values1.length != values2.length || values1[0].length != values2[0].length || values1 == null || values2 == null) {
            throw new RuntimeException("Exception.");
        }
        float[][] result = new float[values1.length][values1[0].length];
        for (int i = 0; i < values1.length; i++) {
            for (int j = 0; j < values1[0].length; j++) {
                result[i][j] = values1[i][j] - values2[i][j];
            }
        }
        return result;
    }

    public Matrix subtraction(final Matrix matrix) {
        if (matrix == null) {
            throw new RuntimeException("Exception.");
        }
        return new Matrix(subtraction(values, matrix.values));
    }

    public static Matrix subtraction(final Matrix matrix1, final Matrix matrix2) {
        if (matrix1 == null) {
            throw new RuntimeException("Exception.");
        }
        return matrix1.subtraction(matrix2);
    }

    public static float[] multiplicationByAVector(final float[][] values, final float[] coordinates) {
        if (values[0].length != coordinates.length || values == null || coordinates == null) {
            throw new RuntimeException("Exception.");
        }
        float[] result = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                result[i] += values[i][j] * coordinates[j];
            }
        }
        return result;
    }

    public Vector multiplicationByAVector(final Vector vector) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return new Vector(multiplicationByAVector(values, vector.coordinates));
    }

    public static Vector multiplicationByAVector(final Matrix matrix, final Vector vector) {
        if (matrix == null) {
            throw new RuntimeException("Exception.");
        }
        return matrix.multiplicationByAVector(vector);
    }

    public static float[][] transposition(final float[][] values) {
        if (values == null) {
            throw new RuntimeException("Exception.");
        }
        float[][] result = new float[values[0].length][values.length];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                result[j][i] = values[i][j];
            }
        }
        return result;
    }

    public Matrix transposition() {
        return new Matrix(transposition(values));
    }

    public static Matrix transposition(final Matrix matrix) {
        if (matrix == null) {
            throw new RuntimeException("Exception.");
        }
        return matrix.transposition();
    }

    public static float[][] createZeroTwoDimensionalArray(final int rows, int columns) {
        return new float[rows][columns];
    }

    public static Matrix createZeroMatrix(final int rows, int columns) {
        return new Matrix(new float[rows][columns]);
    }

    public static float[][] createUnitaryTwoDimensionalArray(final int dimension) {
        float[][] unitaryTwoDimensionalArray = new float[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == j) {
                    unitaryTwoDimensionalArray[i][j] = 1;
                }
            }
        }
        return unitaryTwoDimensionalArray;
    }

    public static Matrix createUnitaryMatrix(final int dimension) {
        return new Matrix(createUnitaryTwoDimensionalArray(dimension));
    }

    public void mul(Matrix m1) {
        final int size = m1.values.length;
        float[][] result = new float[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                float tempCell = 0;
                for (int internalColumn = 0; internalColumn < size; internalColumn++) {
                    tempCell = tempCell + values[row][internalColumn] * m1.values[internalColumn][column];
                }
                result[row][column] = tempCell;
            }
        }
        values = result;
    }
}
