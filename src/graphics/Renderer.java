package graphics;

import graphics.terrain.Tile;
import managers.GameManager;
import managers.TileManager;
import utilities.math.Point;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import graphics.Draw;

import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Renderer {
    private long window;
    GameManager gm;
    TileManager tm;

    public Renderer(int width, int height) {
        this.gm = GameManager.getInstance();
        this.tm = gm.tileManager;

        GLFWErrorCallback.createPrint(System.err).set(); //Imprime los errores que puedan ocurrir al usar GLFW *1

        if (!glfwInit()) //Inicializa la librería
            throw new IllegalStateException("No se pudo inicializar GLFW");

        //Crea una ventana de las dimensiones y título especificados
        this.window = glfwCreateWindow(width, height, "Title", NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("No se pudo crear la ventana");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // VSync
        glfwShowWindow(window);
        GL.createCapabilities(); // Habilita OpenGL

        GL11.glOrtho(0, gm.width, 0, gm.height, -1, 1); //Posiciona (0, 0) en la esquina inferior izquierda
        //glOrtho(-1*gm.width/2, gm.width/2, -1*gm.height, gm.height, -1, 1); //Posiciona (0, 0) en el centro de la ventana
    }

    public void update() {
        draw();
        glfwSwapBuffers(window); //Mostrar frame
        glfwPollEvents(); //Procesar eventos
    }

    private void draw(){
//        System.out.println("------");
//        for (Tile t : tm.tiles){
//            Draw.drawPoly(t.corners, t.background);
//            System.out.println("color: " + t.background);
//            for (Point p : t.corners){
//                System.out.println("x: " + p.x + ", y: " + p.y);
//            }
//        }
//        System.out.println("------");
//        ArrayList<Point> list = new ArrayList();
//        list.add(new Point(0, 0));
//        list.add(new Point(0, 500));
//        list.add(new Point(500, 0));
//        list.add(new Point(500, 500));
//        Draw.drawPoly(list, Color.GRASS);

    }

    public void clean() { //Libera los recursos
        // Libera todos los callbacks asociados a esa ventana (teclado, mouse, scroll, etc.)
        glfwFreeCallbacks(window);
        // Destruye la ventana GLFW (la borra de la pantalla y libera su memoria)
        glfwDestroyWindow(window);
        // Libera todo lo que GLFW haya reservado a nivel global
        glfwTerminate();
        //Libera la memoria del callback *1
        glfwSetErrorCallback(null).free();
    }

    public long getWindow() {return window;}
}
