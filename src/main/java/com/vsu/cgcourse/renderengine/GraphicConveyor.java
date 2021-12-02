package com.vsu.cgcourse.renderengine;

import com.vsu.cgcourse.vectormath.*;

public class GraphicConveyor {

    public static Matrix4x4 rotateScaleTranslate() {
        float[][] matrix = new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        return new Matrix4x4(matrix);
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX = new Vector3f();
        Vector3f resultY = new Vector3f();
        Vector3f resultZ = new Vector3f();

        resultZ.sub(target, eye);
        resultX.cross(up, resultZ);
        resultY.cross(resultZ, resultX);

        resultX.normalization();
        resultY.normalization();
        resultZ.normalization();

        float[][] matrix = new float[][]{
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1}};
        return new Matrix4x4(matrix);
    }

    public static Matrix4x4 perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4x4 result = new Matrix4x4();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.setM00(tangentMinusOnDegree / aspectRatio);
        result.setM11(tangentMinusOnDegree);
        result.setM22((farPlane + nearPlane) / (farPlane - nearPlane));
        result.setM23(1.0F);
        result.setM032(2 * (nearPlane * farPlane) / (nearPlane - farPlane));
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3 (final Matrix4x4 matrix, final Vector3f vertex) {
        final float x = (vertex.getX() * matrix.getM00()) + (vertex.getY() * matrix.getM10()) + (vertex.getZ() * matrix.getM20()) + matrix.getM30();
        final float y = (vertex.getX() * matrix.getM01()) + (vertex.getY() * matrix.getM11()) + (vertex.getZ() * matrix.getM21()) + matrix.getM31();
        final float z = (vertex.getX() * matrix.getM02()) + (vertex.getY() * matrix.getM12()) + (vertex.getZ() * matrix.getM22()) + matrix.getM32();
        final float w = (vertex.getX() * matrix.getM03()) + (vertex.getY() * matrix.getM13()) + (vertex.getZ() * matrix.getM23()) + matrix.getM33();
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}
