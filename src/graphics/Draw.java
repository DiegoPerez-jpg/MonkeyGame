package graphics;

import utilities.math.Point;
import org.lwjgl.opengl.GL11;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class Draw {
    public static void fill(Color color) {
        GL11.glClearColor(color.r, color.g, color.b, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void drawPoint(Point point, Color color) {
        glPointSize(5);
        glColor3f(color.r, color.g, color.b);
        glBegin(GL_POINTS);
            glVertex2f(point.x, point.y);
        glEnd();
    }
    public static void drawPoint(Point point, Float[] rgb) {
        glPointSize(5);
        glColor3f(rgb[0], rgb[1], rgb[2]);
        glBegin(GL_POINTS);
            glVertex2f(point.x, point.y);
        glEnd();
    }

    public static void drawLine(Point start, Point end, Color color) {
        glLineWidth(5);
        glColor3f(color.r, color.g, color.b);
        glBegin(GL_LINES);
            glVertex2f(start.x, start.y);
            glVertex2f(end.x, end.y);
        glEnd();
    }
    public static void drawLine(Point start, Point end, Float[] rgb) {
        glLineWidth(5);
        glColor3f(rgb[0], rgb[1], rgb[2]);
        glBegin(GL_LINES);
            glVertex2f(start.x, start.y);
            glVertex2f(end.x, end.y);
        glEnd();
    }

    public static void drawPoly(ArrayList<Point> points, Color color) {
        glColor3f(color.r, color.g, color.b);
        glBegin(GL_POLYGON);
            for (Point p : points) {glVertex2f(p.x, p.y);}
        glEnd();
    }
    public static void drawPoly(ArrayList<Point> points, Float[] rgb) {
        glColor3f(rgb[0], rgb[1], rgb[2]);
        glBegin(GL_POLYGON);
            for (Point p : points) {glVertex2f(p.x, p.y);}
        glEnd();
    }
}
