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

        final int nPolygons = mesh.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndexes().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndexes().get(vertexInPolygonInd));
                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                resultPoints.add(resultPoint);
            }

            if (resultPoints.size() != 3 || !polygonFill) {
                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    //todo color of model is here
                    Rasterisation.drawLine(pixelWriter, (int) resultPoints.get(vertexInPolygonInd - 1).getX(),
                            (int) resultPoints.get(vertexInPolygonInd - 1).getY(),
                            (int) resultPoints.get(vertexInPolygonInd).getX(),
                            (int) resultPoints.get(vertexInPolygonInd).getY());
                }

                if (nVerticesInPolygon > 0) {
                    Rasterisation.drawLine(pixelWriter, (int) resultPoints.get(nVerticesInPolygon - 1).getX(),
                            (int) resultPoints.get(nVerticesInPolygon - 1).getY(),
                            (int) resultPoints.get(0).getX(),
                            (int) resultPoints.get(0).getY());
                }
            } else {
                Rasterisation.fillTriangle(pixelWriter, resultPoints.get(0), resultPoints.get(1), resultPoints.get(2));
                Rasterisation.drawTriangle(pixelWriter, resultPoints.get(0), resultPoints.get(1), resultPoints.get(2));
            }
        }
    }
}