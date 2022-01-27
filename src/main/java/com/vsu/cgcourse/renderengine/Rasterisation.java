package com.vsu.cgcourse.renderengine;

import com.vsu.cgcourse.vectormath.Point2f;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Rasterisation {

    private static final Color fillColor = Color.rgb(122,102,88);
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

    public static void drawTriangle(PixelWriter pixelWriter, Point2f p0, Point2f p1, Point2f p2) {
        drawLine(pixelWriter, (int) p0.getX(), (int) p0.getY(), (int) p1.getX(), (int) p1.getY());
        drawLine(pixelWriter, (int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        drawLine(pixelWriter, (int) p2.getX(), (int) p2.getY(), (int) p0.getX(), (int) p0.getY());
    }

    public static void drawLine(PixelWriter pixelWriter, Point2f p0, Point2f p1) {
        Point2f temp = new Point2f(0, 0);
        if (Math.abs(p1.getX() - p0.getX()) > Math.abs(p1.getY() - p0.getY())) {
            if (p0.getX() > p1.getX()) {
                temp.set(p0.getX(), p0.getY());
                p0.set(p1.getX(), p1.getY());
                p1.set(temp.getX(), temp.getY());
            }
            List<Float> ys = interpolate(p0.getX(), p0.getY(), p1.getX(), p1.getY());
            for (float x = p0.getX(); x < p1.getX(); x++) {
                int index = (int) (x - p0.getX());
                float y = ys.get(index);
                pixelWriter.setColor((int) x, (int) y, Color.WHITE);
            }
        } else {
            if (p0.getY() > p1.getY()) {
                temp.set(p0.getX(), p0.getY());
                p0.set(p1.getX(), p1.getY());
                p1.set(temp.getX(), temp.getY());
            }
            List<Float> xs = interpolate(p0.getY(), p0.getX(), p1.getY(), p1.getX());
            for (float y = p0.getY(); y < p1.getY(); y++) {
                int index = (int) (y - p0.getY());
                float x = xs.get(index);
                pixelWriter.setColor((int) x, (int) y, Color.WHITE);
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

    public static void fillTriangle(PixelWriter pixelWriter, Point2f p0, Point2f p1, Point2f p2) {
        Point2f temp = new Point2f(0, 0);
        if (p1.getY() < p0.getY()) {
            temp.set(p0.getX(), p0.getY());
            p0.set(p1.getX(), p1.getY());
            p1.set(temp.getX(), temp.getY());
        }
        if (p2.getY() < p0.getY()) {
            temp.set(p0.getX(), p0.getY());
            p0.set(p2.getX(), p2.getY());
            p2.set(temp.getX(), temp.getY());
        }
        if (p2.getY() < p1.getY()) {
            temp.set(p2.getX(), p2.getY());
            p2.set(p1.getX(), p1.getY());
            p1.set(temp.getX(), temp.getY());
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
}
