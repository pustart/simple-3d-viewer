package com.vsu.cgcourse.renderengine;

import com.vsu.cgcourse.vectormath.Point2f;
import com.vsu.cgcourse.vectormath.Point3f;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Rasterisation {

    private static final Color fillColor = Color.rgb(122, 102, 88);
    private static final Color color = Color.WHITE;

    public static void drawLine(PixelWriter pixelWriter, int x1, int y1, int x2, int y2) {
        int dx = (x2 - x1 >= 0 ? 1 : -1);
        int dy = (y2 - y1 >= 0 ? 1 : -1);

        int lengthX = Math.abs(x2 - x1);
        int lengthY = Math.abs(y2 - y1);

        int length = Math.max(lengthX, lengthY);

        if (length == 0)
            pixelWriter.setColor(x1, y1, color);

        if (lengthY <= lengthX) {
            int x = x1;
            int y = y1;
            int d = -lengthX;

            length++;
            while (length > 0) {
                pixelWriter.setColor(x, y, color);
                x += dx;
                d += 2 * lengthY;
                if (d > 0) {
                    d -= 2 * lengthX;
                    y += dy;
                }
                length--;
            }
        } else {
            int x = x1;
            int y = y1;
            int d = -lengthY;

            length++;
            while (length > 0) {
                pixelWriter.setColor(x, y, color);
                y += dy;
                d += 2 * lengthX;
                if (d > 0) {
                    d -= 2 * lengthY;
                    x += dx;
                }
                length--;
            }
        }
    }

    public static void drawLine(PixelWriter pixelWriter, Point2f p0, Point2f p1) {
        if (Math.abs(p1.getX() - p0.getX()) > Math.abs(p1.getY() - p0.getY())) {
            if (p0.getX() > p1.getX()) {
                swap(p0, p1);
            }
            List<Float> ys = interpolate(p0.getX(), p0.getY(), p1.getX(), p1.getY());
            for (float x = p0.getX(); x < p1.getX(); x++) {
                int index = (int) (x - p0.getX());
                if (index == ys.size()) {
                    index--;
                }
                float y = ys.get(index);
                pixelWriter.setColor((int) x, (int) y, Color.WHITE);
            }
        } else {
            if (p0.getY() > p1.getY()) {
                swap(p0, p1);
            }
            List<Float> xs = interpolate(p0.getY(), p0.getX(), p1.getY(), p1.getX());
            for (float y = p0.getY(); y < p1.getY(); y++) {
                int index = (int) (y - p0.getY());
                if (index == xs.size()) {
                    index--;
                }
                float x = xs.get(index);
                pixelWriter.setColor((int) x, (int) y, Color.WHITE);
            }
        }
    }

    //drawLine(pixelWriter, (int) p0.getX(), (int) p0.getY(), (int) p1.getX(), (int) p1.getY());
    //drawLine(pixelWriter, (int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
    //drawLine(pixelWriter, (int) p2.getX(), (int) p2.getY(), (int) p0.getX(), (int) p0.getY());

    public static void drawTriangle(PixelWriter pixelWriter, Point2f p0, Point2f p1, Point2f p2) {
        drawLine(pixelWriter, p0, p1);
        drawLine(pixelWriter, p1, p2);
        drawLine(pixelWriter, p2, p0);
    }

    public static void fillFlatTriangle(PixelWriter pixelWriter, Point2f p0, Point2f p1, Point2f p2) {
        if (p1.getY() < p0.getY()) {
            swap(p0, p1);
        }
        if (p2.getY() < p0.getY()) {
            swap(p0, p2);
        }
        if (p2.getY() < p1.getY()) {
            swap(p2, p1);
        }

        List<Float> x01 = interpolate(p0.getY(), p0.getX(), p1.getY(), p1.getX());
        List<Float> x12 = interpolate(p1.getY(), p1.getX(), p2.getY(), p2.getX());
        List<Float> x02 = interpolate(p0.getY(), p0.getX(), p2.getY(), p2.getX());

        int last = x01.size() - 1;
        x01.remove(last);
        List<Float> x012 = new ArrayList<>(x01);
        x012.addAll(x12);

        List<Float> xLeft;
        List<Float> xRight;

        int m = x012.size() / 2;
        if (x02.get(m) < x012.get(m)) {
            xLeft = x02;
            xRight = x012;
        } else {
            xLeft = x012;
            xRight = x02;
        }

        for (float y = p0.getY(); y < p2.getY(); y++) {
            int index = (int) (y - p0.getY());
            if (index == xLeft.size() || index == xRight.size()) {
                index--;
            }
            for (float x = xLeft.get(index); x < xRight.get(index); x++) {
                pixelWriter.setColor((int) x, (int) y, fillColor);
            }
        }
    }

    private static void drawLineZ(PixelWriter pixelWriter, float[][] depthBuffer, Point3f p0, Point3f p1) {
        if (Math.abs(p1.getX() - p0.getX()) > Math.abs(p1.getY() - p0.getY())) {
            if (p0.getX() > p1.getX()) {
                swap(p0, p1);
            }
            List<Float> ys = interpolate(p0.getX(), p0.getY(), p1.getX(), p1.getY());
            List<Float> zs = interpolate(p0.getX(), p0.getZ(), p1.getX(), p1.getZ());
            for (float x = p0.getX(); x < p1.getX(); x++) {
                int index = (int) (x - p0.getX());
                if (index == ys.size() || index == zs.size()) {
                    index--;
                }
                float y = ys.get(index);
                float z = zs.get(index);
                if (z <= depthBuffer[(int) x][(int) y]) {
                    pixelWriter.setColor((int) x, (int) y, Color.WHITE);
                    depthBuffer[(int) x][(int) y] = z;
                }
            }
        } else {
            if (p0.getY() > p1.getY()) {
                swap(p0, p1);
            }
            List<Float> xs = interpolate(p0.getY(), p0.getX(), p1.getY(), p1.getX());
            List<Float> zs = interpolate(p0.getY(), p0.getZ(), p1.getY(), p1.getZ());
            for (float y = p0.getY(); y < p1.getY(); y++) {
                int index = (int) (y - p0.getY());
                if (index == xs.size() || index == zs.size()) {
                    index--;
                }
                float x = xs.get(index);
                float z = zs.get(index);
                if (z <= depthBuffer[(int) x][(int) y]) {
                    pixelWriter.setColor((int) x, (int) y, Color.WHITE);
                    depthBuffer[(int) x][(int) y] = z;
                }
            }
        }
    }

    public static void drawTriangleZ(PixelWriter pixelWriter,float[][] depthBuffer, Point3f p0, Point3f p1, Point3f p2) {
        drawLineZ(pixelWriter, depthBuffer, p0, p1);
        drawLineZ(pixelWriter, depthBuffer, p1, p2);
        drawLineZ(pixelWriter, depthBuffer, p2, p0);
    }

    public static void fillTriangleZ(PixelWriter pixelWriter,float[][] depthBuffer, Point3f p0, Point3f p1, Point3f p2) {
        if (p1.getY() < p0.getY()) {
            swap(p0, p1);
        }
        if (p2.getY() < p0.getY()) {
            swap(p0, p2);
        }
        if (p2.getY() < p1.getY()) {
            swap(p2, p1);
        }

        List<Float> x01 = interpolate(p0.getY(), p0.getX(), p1.getY(), p1.getX());
        List<Float> x12 = interpolate(p1.getY(), p1.getX(), p2.getY(), p2.getX());
        List<Float> x02 = interpolate(p0.getY(), p0.getX(), p2.getY(), p2.getX());

        List<Float> z01 = interpolate(p0.getY(), p0.getZ(), p1.getY(), p1.getZ());
        List<Float> z12 = interpolate(p1.getY(), p1.getZ(), p2.getY(), p2.getZ());
        List<Float> z02 = interpolate(p0.getY(), p0.getZ(), p2.getY(), p2.getZ());

        int last = x01.size() - 1;
        x01.remove(last);
        List<Float> x012 = new ArrayList<>(x01);
        x012.addAll(x12);

        last = z01.size() - 1;
        z01.remove(last);
        List<Float> z012 = new ArrayList<>(z01);
        z012.addAll(z12);

        List<Float> xLeft;
        List<Float> xRight;

        List<Float> zLeft;
        List<Float> zRight;

        int m = x012.size() / 2;
        if (x02.get(m) < x012.get(m)) {
            xLeft = x02;
            xRight = x012;

            zLeft = z02;
            zRight = z012;
        } else {
            xLeft = x012;
            xRight = x02;

            zLeft = z012;
            zRight = z02;
        }

        for (float y = p0.getY(); y < p2.getY(); y++) {
            int index = (int) (y - p0.getY());
            if (index == xLeft.size() || index == xRight.size()) {
                index--;
            }
            float xl = xLeft.get(index);
            float xr = xRight.get(index);
            List<Float> zSegment = interpolate(xl, zLeft.get(index), xr, zRight.get(index));
            for (float x = xl; x < xr; x++) {
                int zIndex = (int) (x - xl);
                float z = zSegment.get(zIndex);
                int intX = (int) x;
                int intY = (int) y;
                if (z < depthBuffer[intX][intY]) {
                    pixelWriter.setColor(intX, intY, fillColor);
                    depthBuffer[intX][intY] = z;
                }
            }
        }
    }

    private static List<Float> interpolate(float i0, float d0, float i1, float d1) {
        List<Float> values = new ArrayList<>();
        if (i0 == i1) {
            values.add(d0);
            return values;
        }
        float a = (d1 - d0) / (i1 - i0);
        float d = d0;
        for (float i = i0; i < i1; i++) {
            values.add(d);
            d = d + a;
        }
        return values;
    }

    private static void swap(Point2f p0, Point2f p1) {
        Point2f temp = new Point2f(0, 0);
        temp.set(p0.getX(), p0.getY());
        p0.set(p1.getX(), p1.getY());
        p1.set(temp.getX(), temp.getY());
    }

    private static void swap(Point3f p0, Point3f p1) {
        Point3f temp = new Point3f(0, 0, 0);
        temp.set(p0.getX(), p0.getY(), p0.getZ());
        p0.set(p1.getX(), p1.getY(), p1.getZ());
        p1.set(temp.getX(), temp.getY(), temp.getZ());
    }

}
