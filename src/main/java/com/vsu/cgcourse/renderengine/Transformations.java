package com.vsu.cgcourse.renderengine;

import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.vectormath.Matrix3x3;
import com.vsu.cgcourse.vectormath.Matrix4x4;
import com.vsu.cgcourse.vectormath.Vector3f;

import static com.vsu.cgcourse.renderengine.GraphicConveyor.*;
import static com.vsu.cgcourse.renderengine.GraphicConveyor.multiplyMatrix4ByVector3;

public class Transformations {

    private Mesh original;
    //private final Mesh translated = original;

    public Transformations(Mesh original) {
        this.original = original;
        //this.translated = original;
    }

    public void rotateAboutX(double alpha) {
        Matrix3x3 matrix3x3 = GraphicConveyor.rotateAboutX(alpha);
        translation3x3(matrix3x3);
    }

    public void rotateAboutY(double alpha) {
        Matrix3x3 matrix3x3 = GraphicConveyor.rotateAboutY(alpha);
        translation3x3(matrix3x3);
    }

    public void rotateAboutZ(double alpha) {
        Matrix3x3 matrix3x3 = GraphicConveyor.rotateAboutZ(alpha);
        translation3x3(matrix3x3);
    }

    public void scaleX(float s) {
        Matrix3x3 matrix3x3 = GraphicConveyor.scaleX(s);
        translation3x3(matrix3x3);
    }

    public void scaleY(float s) {
        Matrix3x3 matrix3x3 = GraphicConveyor.scaleY(s);
        translation3x3(matrix3x3);
    }

    public void scaleZ(float s) {
        Matrix3x3 matrix3x3 = GraphicConveyor.scaleZ(s);
        translation3x3(matrix3x3);
    }

    public void offsetX(float s) {
        Matrix4x4 matrix4x4 = GraphicConveyor.offsetX(s);
        translation4x4(matrix4x4);
    }

    public void offsetY(float s) {
        Matrix4x4 matrix4x4 = GraphicConveyor.offsetY(s);
        translation4x4(matrix4x4);
    }

    public void offsetZ(float s) {
        Matrix4x4 matrix4x4 = GraphicConveyor.offsetZ(s);
        translation4x4(matrix4x4);
    }

    public void translation3x3(Matrix3x3 matrix) {
        final int size = this.original.getVertices().size();
        for (int vertexInd = 0; vertexInd < size; vertexInd++) {
            Vector3f temp = this.original.getVertices().get(vertexInd);

            this.original.getVertices().get(vertexInd).setCoordinates(multiplyMatrix3ByVector3(matrix, temp).getCoordinates());
        }
    }

    public void translation4x4(Matrix4x4 matrix) {
        final int size = this.original.getVertices().size();
        for (int vertexInd = 0; vertexInd < size; vertexInd++) {
            Vector3f temp = this.original.getVertices().get(vertexInd);

            this.original.getVertices().get(vertexInd).setCoordinates(multiplyMatrix4ByVector3(matrix, temp).getCoordinates());
        }
    }

    public void setOriginal(Mesh original) {
        this.original = original;
    }
}
