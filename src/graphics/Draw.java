package graphics;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import utilities.math.Point;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Draw {
    public static void fill(Color color) {
        GL11.glClearColor(color.r/255, color.g/255, color.b/255, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void drawPoint(Point point, Color color) {
        glPointSize(5);
        glColor3f(color.r/255, color.g/255, color.b/255);
        glBegin(GL_POINTS);
            glVertex2f(point.x, point.y);
        glEnd();
    }
    public static void drawPoint(Point point, Float[] rgb) {
        glPointSize(5);
        glColor3f(rgb[0]/255, rgb[1]/255, rgb[2]/255);
        glBegin(GL_POINTS);
            glVertex2f(point.x, point.y);
        glEnd();
    }

    public static void drawLine(Point start, Point end, Color color) {
        glLineWidth(5);
        glColor3f(color.r/255, color.g/255, color.b/255);
        glBegin(GL_LINES);
            glVertex2f(start.x, start.y);
            glVertex2f(end.x, end.y);
        glEnd();
    }
    public static void drawLine(Point start, Point end, Float[] rgb) {
        glLineWidth(5);
        glColor3f(rgb[0]/255, rgb[1]/255, rgb[2]/255);
        glBegin(GL_LINES);
            glVertex2f(start.x, start.y);
            glVertex2f(end.x, end.y);
        glEnd();
    }

    //Importante puntos en sentido antihorario empezando por arriba a la izq
    public static void drawPoly(ArrayList<Point> points, Color color) {
        glColor3f(color.r/255, color.g/255, color.b/255);
        glBegin(GL_POLYGON);
            for (Point p : points) {glVertex2f(p.x, p.y);}
        glEnd();
    }
    public static void drawPoly(ArrayList<Point> points, Float[] rgb) {
        glColor3f(rgb[0]/255, rgb[1]/255, rgb[2]/255);
        glBegin(GL_POLYGON);
            for (Point p : points) {glVertex2f(p.x, p.y);}
        glEnd();
    }
}
