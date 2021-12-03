package com.vsu.cgcourse.vectormath;

import java.util.Arrays;

public class Vector {
    protected float[] coordinates;
    public static final float eps = 1e-7f;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() && getClass() != o.getClass().getSuperclass()) {
            return false;
        }
        Vector vector = (Vector) o;
        if (coordinates.length != vector.coordinates.length) {
            return false;
        }
        boolean result = true;
        for (int i = 0; i < coordinates.length; i++) {
            result = result && (Math.abs(coordinates[i] - vector.coordinates[i]) < eps);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    public Vector() {
    }

    public Vector(int dimension) {
        coordinates = new float[dimension];
    }

    public Vector(final float[] coordinates) {
        this.coordinates = coordinates;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public String toString() {
        return Arrays.toString(coordinates);
    }

    public static String toString(final Vector vector) {
        return vector.toString();
    }


    public static float[] addition(final float[] coordinates1, final float[] coordinates2) {
        if (coordinates1.length != coordinates2.length || coordinates1 == null || coordinates2 == null) {
            throw new RuntimeException("Exception.");
        }
        float[] result = new float[coordinates1.length];
        for (int i = 0; i < coordinates1.length; i++) {
            result[i] = coordinates1[i] + coordinates2[i];
        }
        return result;
    }

    public Vector addition(final Vector vector) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return new Vector(addition(coordinates, vector.coordinates));
    }

    public static Vector addition(final Vector vector1, final Vector vector2) {
        if (vector1 == null) {
            throw new RuntimeException("Exception.");
        }
        return vector1.addition(vector2);
    }

    public static float[] subtraction(final float[] coordinates1, final float[] coordinates2) {
        if (coordinates1.length != coordinates2.length || coordinates1 == null || coordinates2 == null) {
            throw new RuntimeException("Exception.");
        }
        float[] result = new float[coordinates1.length];
        for (int i = 0; i < coordinates1.length; i++) {
            result[i] = coordinates1[i] - coordinates2[i];
        }
        return result;
    }

    public Vector subtraction(final Vector vector) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return new Vector(subtraction(coordinates, vector.coordinates));
    }

    public static Vector subtraction(final Vector vector1, final Vector vector2) {
        if (vector1 == null) {
            throw new RuntimeException("Exception.");
        }
        return vector1.subtraction(vector2);
    }

    public static float[] multiplicationByANumber(final float[] coordinates, final float number) {
        if (coordinates == null) {
            throw new RuntimeException("Exception.");
        }
        float[] result = new float[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            result[i] = coordinates[i] * number;
        }
        return result;
    }

    public Vector multiplicationByANumber(final float number) {
        return new Vector(multiplicationByANumber(coordinates, number));
    }

    public static Vector multiplicationByANumber(final Vector vector, final float number) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return vector.multiplicationByANumber(number);
    }

    public static float lengthСalculation(final float[] coordinates) {
        if (coordinates == null) {
            throw new RuntimeException("Exception.");
        }
        float result = 0;
        for (Float coordinate : coordinates) {
            result += coordinate * coordinate;
        }
        return (float) Math.sqrt(result);
    }

    public float lengthСalculation() {
        return lengthСalculation(coordinates);
    }

    public static float lengthСalculation(final Vector vector) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return vector.lengthСalculation();
    }

    public static float[] normalization(final float[] coordinates) {
        return multiplicationByANumber(coordinates, 1 / lengthСalculation(coordinates));
    }

    public Vector normalization() {
        return new Vector(normalization(coordinates));
    }

    public static Vector normalization(final Vector vector) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return vector.normalization();
    }

    public static float scalarProduct(final float[] coordinates1, final float[] coordinates2) {
        if (coordinates1.length != coordinates2.length || coordinates1 == null || coordinates2 == null) {
            throw new RuntimeException("Exception.");
        }
        float result = 0;
        for (int i = 0; i < coordinates1.length; i++) {
            result += coordinates1[i] * coordinates2[i];
        }
        return result;
    }

    public float scalarProduct(final Vector vector) {
        if (vector == null) {
            throw new RuntimeException("Exception.");
        }
        return scalarProduct(coordinates, vector.coordinates);
    }

    public static float scalarProduct(final Vector vector1, final Vector vector2) {
        if (vector1 == null) {
            throw new RuntimeException("Exception.");
        }
        return vector1.scalarProduct(vector2);
    }

    //Устанавливает значение этого вектора как результат сложения этого вектора и v1
    public void add(Vector v1) {
        coordinates = addition(v1).coordinates;
    }

    //Устанавливает значение этого вектора как результат вычитания векторов v1 и v2
    public void sub(Vector v1, Vector v2) {
        coordinates = subtraction(v1, v2).coordinates;
    }

    //Устанавливает значение этого вектора как результат нормализации этого вектора
    public void normalize() {
        coordinates = normalization(this).coordinates;
    }

}
