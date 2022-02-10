package com.vsu.cgcourse.renderengine;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import com.vsu.cgcourse.vectormath.*;
import com.vsu.cgcourse.model.Mesh;
import javafx.scene.image.PixelWriter;

import static com.vsu.cgcourse.renderengine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Mesh mesh,
            final int width,
            final int height,
            boolean polygonFill) {
        //TODO: in graphicsContest something like setPixel/pixelWriter with our own custom
        Matrix4x4 modelMatrix = rotateScaleTranslate();
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();
        //old: MVP matrix multiplication
        //new: makes reversed matrix multiplication for vector-column: PVM
        Matrix4x4 modelViewProjectionMatrix = new Matrix4x4(projectionMatrix.transposition().getValues());
        modelViewProjectionMatrix.mul(viewMatrix.transposition());
        modelViewProjectionMatrix.mul(modelMatrix.transposition());

        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        float[][] depthBuffer = new float[width][height];
        for (int i = 0; i < depthBuffer.length; i++) {
            for (int j = 0; j < depthBuffer[0].length; j++) {
                depthBuffer[i][j] = Float.MAX_VALUE;
            }
        }

        final int nPolygons = mesh.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndexes().size();

            ArrayList<Point2f> resultPoints2f = new ArrayList<>();
            ArrayList<Point3f> resultPoints3f = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndexes().get(vertexInPolygonInd));
                Point2f resultPoint2f = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                Point3f resultPoint3f = vertexToPoint3f(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                resultPoints2f.add(resultPoint2f);
                resultPoints3f.add(resultPoint3f);
            }

            if (resultPoints2f.size() != 3 || !polygonFill) {
                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    //todo color of model is here
                    Rasterisation.drawLine(pixelWriter, (int) resultPoints2f.get(vertexInPolygonInd - 1).getX(),
                            (int) resultPoints2f.get(vertexInPolygonInd - 1).getY(),
                            (int) resultPoints2f.get(vertexInPolygonInd).getX(),
                            (int) resultPoints2f.get(vertexInPolygonInd).getY());
                }

                if (nVerticesInPolygon > 0) {
                    Rasterisation.drawLine(pixelWriter, (int) resultPoints2f.get(nVerticesInPolygon - 1).getX(),
                            (int) resultPoints2f.get(nVerticesInPolygon - 1).getY(),
                            (int) resultPoints2f.get(0).getX(),
                            (int) resultPoints2f.get(0).getY());
                }
            } else {
                Rasterisation.fillTriangleZ(pixelWriter,depthBuffer, resultPoints3f.get(0), resultPoints3f.get(1), resultPoints3f.get(2));
                Rasterisation.drawTriangleZ(pixelWriter, depthBuffer, resultPoints3f.get(0), resultPoints3f.get(1), resultPoints3f.get(2));
            }
        }
    }
}