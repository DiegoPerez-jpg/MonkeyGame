package graphics;


import utilities.math.Point;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class Draw {
    public static void fill(Color color) {
        GL11.glClearColor(color.r/255f, color.g/255f, color.b/255f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void drawPoint(Point point, Color color) {
        glPointSize(5);
        glColor4f(color.r/255, color.g/255, color.b/255, color.a);
        glBegin(GL_POINTS);
            glVertex2f(point.x, point.y);
        glEnd();
        glColor4f(1f, 1f, 1f, 1f); //Restablezco el siguiente color a un color neutral
    }
    public static void drawPoint(Point point, Float[] rgb) {
        glPointSize(5);
        glColor3f(rgb[0]/255, rgb[1]/255, rgb[2]/255);
        glBegin(GL_POINTS);
            glVertex2f(point.x, point.y);
        glEnd();
        glColor4f(1f, 1f, 1f, 1f); //Restablezco el siguiente color a un color neutral
    }

    public static void drawLine(Point start, Point end, Color color, int size) {
        glLineWidth(size);
        glColor4f(color.r/255, color.g/255, color.b/255, color.a);
        glBegin(GL_LINES);
            glVertex2f(start.x, start.y);
            glVertex2f(end.x, end.y);
        glEnd();
        glColor4f(1f, 1f, 1f, 1f); //Restablezco el siguiente color a un color neutral
    }
    public static void drawLine(Point start, Point end, Float[] rgb, int size) {
        glLineWidth(size);
        glColor3f(rgb[0]/255, rgb[1]/255, rgb[2]/255);
        glBegin(GL_LINES);
            glVertex2f(start.x, start.y);
            glVertex2f(end.x, end.y);
        glEnd();
        glColor4f(1f, 1f, 1f, 1f); //Restablezco el siguiente color a un color neutral
    }

    //Importante puntos en sentido antihorario empezando por arriba a la izq
    public static void drawPoly(ArrayList<Point> points, Color color) {
        glColor4f(color.r/255, color.g/255, color.b/255, color.a);
        glBegin(GL_POLYGON);
            for (Point p : points) {glVertex2f(p.x, p.y);}
        glEnd();
        glColor4f(1f, 1f, 1f, 1f); //Restablezco el siguiente color a un color neutral
    }
    public static void drawPoly(ArrayList<Point> points, Float[] rgb) {
        glColor3f(rgb[0]/255, rgb[1]/255, rgb[2]/255);
        glBegin(GL_POLYGON);
            for (Point p : points) {glVertex2f(p.x, p.y);}
        glEnd();
        glColor4f(1f, 1f, 1f, 1f); //Restablezco el siguiente color a un color neutral
    }

    public static void drawBorderPoly(ArrayList<Point> points, Color color, int borderSize, Color colorLine) {
        drawPoly(points, color);
        drawLine(points.get(0), points.get(1), colorLine, borderSize);
        drawLine(points.get(1), points.get(2), colorLine, borderSize);
        drawLine(points.get(2), points.get(3), colorLine, borderSize);
        drawLine(points.get(3), points.get(0), colorLine, borderSize);
    }

    public static void drawBorderPoly(ArrayList<Point> points, Color color, int borderSize){
        drawBorderPoly(points, color, borderSize, Color.BLACK);
    }

    public static void drawText(String text, Point pos, int size, int letSpace) {
        int i = 0;
        for (char c : text.toCharArray()) {
            if (c != ' ') new Texture("src/assets/alphabet/" + c +".png").render(new Point(pos.x+i, pos.y), size);
            i += (5 + letSpace)*size;
        }
    }
    public static void drawText(String text, Point pos) {drawText(text, pos, 1, 2);}
}
