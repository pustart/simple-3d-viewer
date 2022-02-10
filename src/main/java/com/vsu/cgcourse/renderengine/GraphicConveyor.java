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

    public static Matrix3x3 rotateAboutX(double alpha) {
        float[][] result = new float[][]{
                {1, 0, 0},
                {0, (float) Math.cos(alpha), (float) -Math.sin(alpha)},
                {0, (float) Math.sin(alpha), (float) Math.cos(alpha)},
        };

        return new Matrix3x3(result);
    }

    public static Matrix3x3 rotateAboutY(double alpha) {
        float[][] result = new float[][]{
                {(float) Math.cos(alpha), 0, (float) Math.sin(alpha)},
                {0, 1, 0},
                {(float) -Math.sin(alpha), 0, (float) Math.cos(alpha)},
        };

        return new Matrix3x3(result);
    }

    public static Matrix3x3 rotateAboutZ(double alpha) {
        float[][] result = new float[][]{
                {(float) Math.cos(alpha), (float) -Math.sin(alpha), 0},
                {(float) Math.sin(alpha), (float) Math.cos(alpha), 0},
                {0, 0, 1},
        };

        return new Matrix3x3(result);
    }

    public static Matrix3x3 scaleX(float s) {
        float[][] result = new float[][]{
                {s, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
        };
        return new Matrix3x3(result);
    }

    public static Matrix3x3 scaleY(float s) {
        float[][] result = new float[][]{
                {1, 0, 0},
                {0, s, 0},
                {0, 0, 1},
        };
        return new Matrix3x3(result);
    }

    public static Matrix3x3 scaleZ(float s) {
        float[][] result = new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, s},
        };
        return new Matrix3x3(result);
    }

    public static Matrix4x4 offsetX(float s) {
        float[][] result = new float[][]{
                {1, 0, 0, s},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4x4(result);
    }

    public static Matrix4x4 offsetY(float s) {
        float[][] result = new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, s},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4x4(result);
    }

    public static Matrix4x4 offsetZ(float s) {
        float[][] result = new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, s},
                {0, 0, 0, 1}
        };
        return new Matrix4x4(result);
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

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

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
        result.setElement(0, 0, tangentMinusOnDegree / aspectRatio);
        result.setElement(1, 1, tangentMinusOnDegree);
        result.setElement(2, 2, (farPlane + nearPlane) / (farPlane - nearPlane));
        result.setElement(2, 3, 1.0F);
        result.setElement(3, 2, 2 * (nearPlane * farPlane) / (nearPlane - farPlane));
        return result;
    }

    //done: changed the order of multiplication
    public static Vector3f multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3f vertex) {
        final float x = (matrix.getElement(0, 0) * vertex.getX()) + (matrix.getElement(0, 1) * vertex.getY()) + (matrix.getElement(0, 2) * vertex.getZ()) + matrix.getElement(0, 3);
        final float y = (matrix.getElement(1, 0) * vertex.getX()) + (matrix.getElement(1, 1) * vertex.getY()) + (matrix.getElement(1, 2) * vertex.getZ()) + matrix.getElement(1, 3);
        final float z = (matrix.getElement(2, 0) * vertex.getX()) + (matrix.getElement(2, 1) * vertex.getY()) + (matrix.getElement(2, 2) * vertex.getZ()) + matrix.getElement(2, 3);
        final float w = (matrix.getElement(3, 0) * vertex.getX()) + (matrix.getElement(3, 1) * vertex.getY()) + (matrix.getElement(3, 2) * vertex.getZ()) + matrix.getElement(3, 3);
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Vector3f multiplyMatrix3ByVector3(final Matrix3x3 matrix, final Vector3f vertex) {
        final float x = (matrix.getElement(0, 0) * vertex.getX()) + (matrix.getElement(0, 1) * vertex.getY()) + (matrix.getElement(0, 2) * vertex.getZ());
        final float y = (matrix.getElement(1, 0) * vertex.getX()) + (matrix.getElement(1, 1) * vertex.getY()) + (matrix.getElement(1, 2) * vertex.getZ());
        final float z = (matrix.getElement(2, 0) * vertex.getX()) + (matrix.getElement(2, 1) * vertex.getY()) + (matrix.getElement(2, 2) * vertex.getZ());
        return new Vector3f(x, y, z);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }

    public static Point3f vertexToPoint3f(final Vector3f vertex, final int width, final int height) {
        return new Point3f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F, vertex.getZ());
    }
}
