package com.vsu.cgcourse.vectormath;

import com.vsu.cgcourse.model.Polygon;
import java.util.ArrayList;

public class Triangulation {
    public static ArrayList<Polygon> polygonsTriangulation(ArrayList<Polygon> polygons) {
        ArrayList<Polygon> result = new ArrayList<>();
        for (Polygon currPolygon : polygons) {
            Polygon triangulatedPolygon = new Polygon();
            if (currPolygon.getVertexIndexes().size() != 0) {
                triangulatedPolygon.setVertexIndexes(triangulation(currPolygon.getVertexIndexes()));
            }

            if (currPolygon.getTextureVertexIndexes().size() != 0) {
                triangulatedPolygon.setTextureVertexIndexes(triangulation(currPolygon.getTextureVertexIndexes()));
            }

            if (currPolygon.getNormalIndexes().size() != 0) {
                triangulatedPolygon.setNormalIndexes(currPolygon.getNormalIndexes());
            }

            result.add(triangulatedPolygon);
        }

        return result;
    }

    public static ArrayList<Integer> triangulation(ArrayList<Integer> polygon) {
        ArrayList<Integer> result = new ArrayList<>();
            int countOfAngles = polygon.size();
            if (countOfAngles > 3) {
                for (int j = 0; j < countOfAngles - 2; j++) {
                    result.add(polygon.get(0));
                    result.add(polygon.get(j + 1));
                    result.add(polygon.get(j + 2));
                }
            } else {
                return polygon;
            }

        return result;
    }
}
