package graphics;

import entities.balloons.Balloon;
import entities.bullets.Bullet;
import entities.monkeys.Monkey;
import graphics.surface.AsideUI;
import graphics.terrain.Tile;
import managers.*;
import utilities.math.Point;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import graphics.Draw;
import java.util.ArrayList;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;


public class Renderer {
    private long window;
    GameManager gm;
    TileManager tm;
    MonkeyManager mm;
    BalloonManager bm;
    BulletManager bulletManager;

    public Renderer(int width, int height) {
        this.gm = GameManager.getInstance();
        this.tm = gm.tileManager;
        this.mm = gm.monkeyManager;
        this.bm = gm.balloonManager;
        this.bulletManager = gm.bulletManager;

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

        glEnable(GL_BLEND); // Habilito el canal alpha para que pueda haber transparencias
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
    }

    public void update() {
        Draw.fill(Color.BLACK); //Rellena toda la pantalla de un color y limpia el buffer
        draw();
        glfwSwapBuffers(window); //Mostrar frame
        glfwPollEvents(); //Procesar eventos
    }

    private void draw(){
        for (Tile t : tm.tiles){Draw.drawPoly(t.corners, t.background);}
        for (Monkey t : mm.getMonkeys()){t.skin.render(t.position);}
        for (Balloon b : bm.getBalloons()){b.skin.render(b.position);}
        //Render interfaces
        Draw.drawPoly(gm.asideUI.corners, gm.asideUI.background);
        Draw.drawPoly(gm.bottomUI.corners, gm.bottomUI.background);
        for(Bullet b : bulletManager.getBullets()){b.skin.render(b.position);}
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
