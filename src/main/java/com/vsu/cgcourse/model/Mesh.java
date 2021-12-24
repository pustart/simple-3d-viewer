package com.vsu.cgcourse.model;

import java.util.*;
import com.vsu.cgcourse.vectormath.Vector3f;
import com.vsu.cgcourse.vectormath.Vector2f;

public class Mesh {
    private ArrayList<Vector3f> vertices;
    private ArrayList<Vector2f> textureVertices;
    private ArrayList<Vector3f> normals;
    private final ArrayList<Polygon> polygons;

    public Mesh(ArrayList<Vector3f> vertices,
                 ArrayList<Vector2f> textureVertices,
                 ArrayList<Vector3f> normals,
                 ArrayList<Polygon> polygons) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
    }

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3f> getNormals() {
        return normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void setVertices(ArrayList<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public void setTextureVertices(ArrayList<Vector2f> textureVertices) {
        this.textureVertices = textureVertices;
    }

    public void setNormals(ArrayList<Vector3f> normals) {
        this.normals = normals;
    }
}
