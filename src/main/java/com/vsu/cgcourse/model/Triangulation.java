package com.vsu.cgcourse.model;

import java.util.ArrayList;

public class Triangulation {
    public static ArrayList<Polygon> polygonsTriangulation(ArrayList<Polygon> polygons) {
        ArrayList<Polygon> result = new ArrayList<>();
        for (Polygon currPolygon : polygons) {
            ArrayList<ArrayList<Integer>> arrPolygonVertex = triangulation(currPolygon.getVertexIndexes());
            ArrayList<ArrayList<Integer>> arrPolygonTextureVertex = triangulation(currPolygon.getTextureVertexIndexes());
            ArrayList<ArrayList<Integer>> arrPolygonNormalVertex = triangulation(currPolygon.getNormalIndexes());

            for (int i = 0; i < arrPolygonVertex.size(); i++) {
                Polygon triangulatedPolygon = new Polygon();
                triangulatedPolygon.setVertexIndexes(arrPolygonVertex.get(i));
                if (!arrPolygonTextureVertex.isEmpty() && i < arrPolygonTextureVertex.size()) {
                    triangulatedPolygon.setTextureVertexIndexes(arrPolygonTextureVertex.get(i));
                }
                if (!arrPolygonNormalVertex.isEmpty() && i < arrPolygonNormalVertex.size()) {
                    triangulatedPolygon.setNormalIndexes(arrPolygonNormalVertex.get(i));
                }

                result.add(triangulatedPolygon);
            }
        }
        return result;
    }

    public static ArrayList<ArrayList<Integer>> triangulation(ArrayList<Integer> polygon) {
        ArrayList<ArrayList<Integer>> arrPolygon = new ArrayList<>();
        int countOfAngles = polygon.size();
        if (countOfAngles > 3) {
            for (int j = 0; j < countOfAngles - 2; j++) {
                ArrayList<Integer> result = new ArrayList<>();
                result.add(polygon.get(0));
                result.add(polygon.get(j + 1));
                result.add(polygon.get(j + 2));
                arrPolygon.add(result);
            }
        } else {
            arrPolygon.add(polygon);
        }
        return arrPolygon;
    }
}
