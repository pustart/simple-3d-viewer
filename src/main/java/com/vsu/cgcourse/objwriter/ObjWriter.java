package com.vsu.cgcourse.objwriter;

import com.vsu.cgcourse.model.Polygon;
import com.vsu.cgcourse.vectormath.Vector2f;
import com.vsu.cgcourse.vectormath.Vector3f;
import com.vsu.cgcourse.model.Mesh;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ObjWriter {

    private static int lineInd = 0;

    public static void write(List<Mesh> meshList, File file) {
        Locale.setDefault(Locale.ROOT);

        try (PrintWriter writer = new PrintWriter(file)) {
            int verticesCount = 0;
            for (Mesh mesh : meshList) {
                final ArrayList<Vector3f> vertices = mesh.getVertices();
                final ArrayList<Vector2f> textureVertices = mesh.getTextureVertices();
                final ArrayList<Vector3f> normals = mesh.getNormals();
                final ArrayList<Polygon> polygons = mesh.getPolygons();

                writeV(vertices, writer);
                writeVt(textureVertices, writer);
                writeVn(normals, writer);

                writeF(
                        polygons,
                        verticesCount,
                        writer
                );

                verticesCount += vertices.size();
                writer.flush();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void write(Mesh mesh, File file) {
        Locale.setDefault(Locale.ROOT);

        try (PrintWriter writer = new PrintWriter(file)) {
            final ArrayList<Vector3f> vertices = mesh.getVertices();
            final ArrayList<Vector2f> textureVertices = mesh.getTextureVertices();
            final ArrayList<Vector3f> normals = mesh.getNormals();
            final ArrayList<Polygon> polygons = mesh.getPolygons();

            writeV(vertices, writer);
            writeVt(textureVertices, writer);
            writeVn(normals, writer);
            writeF(
                    polygons,
                    0,
                    writer
            );

            writer.flush();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void writeV(ArrayList<Vector3f> vertices, PrintWriter writer) throws IOException {
        for (Vector3f v : vertices) {
            writer.write(String.format("v %.6f %.6f %.6f\n", v.getX(), v.getY(), v.getZ()));
            lineInd++;
        }
    }

    private static void writeVt(ArrayList<Vector2f> textureVertices, PrintWriter writer) throws IOException {
        if (!textureVertices.isEmpty()) {
            writer.write("\n");
            lineInd++;
        }
        for (Vector2f vt : textureVertices) {
            writer.write(String.format("vt %.6f %.6f\n", vt.getX(), vt.getY()));
            lineInd++;
        }
    }

    private static void writeVn(ArrayList<Vector3f> normals, PrintWriter writer) throws IOException {
        if (!normals.isEmpty()) {
            writer.write("\n");
            lineInd++;
        }
        for (Vector3f vn : normals) {
            writer.write(String.format("vn %.6f %.6f %.6f\n", vn.getX(), vn.getY(), vn.getZ()));
            lineInd++;
        }
    }

    private static void writeF(
            ArrayList<Polygon> polygons,
            int verticesCount,
            PrintWriter writer
    ) throws IOException {

        if (!polygons.isEmpty()) {
            writer.write("\n");
            lineInd++;
        }

        for (Polygon polygon : polygons) {
            StringBuilder builder = new StringBuilder();
            builder.append("f ");

            ArrayList<Integer> integersIndices = polygon.getVertexIndexes();
            ArrayList<Integer> integersTexture = polygon.getTextureVertexIndexes();
            ArrayList<Integer> integersNormal = polygon.getNormalIndexes();

            int jMax = Math.max(
                    integersIndices.size(),
                    Math.max(
                            integersTexture.size(),
                            integersNormal.size()
                    )
            );

            for (int j = 0; j < jMax; j++) {
                try {
                    int indices = integersIndices.get(j) + 1 + verticesCount;
                    builder.append(indices);

                    if (!integersTexture.isEmpty()) {
                        int texture = integersTexture.get(j) + 1 + verticesCount;
                        builder.append("/");
                        builder.append(texture);
                    }

                    if (integersTexture.isEmpty() && !integersNormal.isEmpty()) {
                        builder.append("/");
                    }
                    if (!integersNormal.isEmpty()) {
                        int normal = integersNormal.get(j) + 1 + verticesCount;
                        builder.append("/");
                        builder.append(normal);
                    }
                    builder.append(" ");

                } catch (IndexOutOfBoundsException e) {
                    throw new ObjWriterException("Too few arguments.", lineInd);
                }
            }
            writer.write(builder + "\n");
            lineInd++;
        }
    }
}
