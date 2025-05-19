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

    public static void renderImg(String path, float x, float y){
        MemoryStack stack = MemoryStack.stackPush(); //Stack de reserva de memoria temporal
        IntBuffer width  = stack.mallocInt(1);
        IntBuffer height = stack.mallocInt(1);
        IntBuffer channels = stack.mallocInt(1);

        // Ruta a tu imagen (puede estar en /resources o filesystem directo)
        ByteBuffer image = STBImage.stbi_load(path, width, height, channels, 4);
        if (image == null) throw new RuntimeException("No se pudo cargar la imagen: " + STBImage.stbi_failure_reason());

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Cargar la textura
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        // Generar mipmaps y configurar filtros
        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        STBImage.stbi_image_free(image);

        // Dibujar el cuadrado con la textura en la posición y tamaño deseado
        glEnable(GL_TEXTURE_2D); // Habilitar texturas

        glBindTexture(GL_TEXTURE_2D, textureID); // Usar la textura cargada

        // Dibujar un cuadrado (quad) con la textura aplicada
        glBegin(GL_QUADS);

        // Coordenadas del cuadrado (posición + tamaño)
        glTexCoord2f(0.0f, 1.0f); glVertex2f(x, y); // Abajo izquierda
        glTexCoord2f(1.0f, 1.0f); glVertex2f(x + width, y); // Abajo derecha
        glTexCoord2f(1.0f, 0.0f); glVertex2f(x + width, y + height); // Arriba derecha
        glTexCoord2f(0.0f, 0.0f); glVertex2f(x, y + height); // Arriba izquierda

        glEnd();

        glDisable(GL_TEXTURE_2D); // Deshabilitar texturas

        stack.close();
    }
}
