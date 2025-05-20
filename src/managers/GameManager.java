package managers;

import entities.bullets.BulletPrefab;
import entities.monkeys.Monkey;
import entities.monkeys.TribeMonkey;
import graphics.Color;
import graphics.Renderer;
import graphics.surface.UI;
import graphics.terrain.Tile;
import levels.Level;
import utilities.Timer;
import utilities.math.Point;
import entities.balloons.Balloon;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class GameManager {
    private static GameManager instance;
    public InputManager inputManager;
    public TileManager tileManager;
    public MonkeyManager monkeyManager;
    public BalloonManager balloonManager;
    public BulletManager bulletManager;
    public LevelManager levelManager;
    public int width, height;
    public int gameWidth, gameHeight;
    public int extension_x, extension_y;
    public Renderer renderer;
    public float time;
    public double vida;
    public int tileSize;
    public Timer timer;
    public UI asideUI, bottomUI;
    private GameManager(){
        this.gameWidth = 640;
        this.gameHeight = 480;
        this.extension_x = 256; //Extensión de la interfaz derecha
        this.extension_y = 128; //Extensión de la interfaz inferior
        this.width = gameWidth + extension_x;
        this.height = gameHeight + extension_y;
        this.timer = new Timer();
        this.tileSize = 32;
    }

    public static GameManager getInstance() {
        if (instance == null) {instance = new GameManager();}
        return instance;
    }

    private void init() {
        //Estas clases llaman al gm en sus constructores por lo que no pueden ser creadas en el contructor del gm
        this.tileManager = new TileManager();
        this.levelManager = new LevelManager();
        this.monkeyManager = new MonkeyManager();
        this.balloonManager = new BalloonManager();
        this.bulletManager = new BulletManager();
        this.renderer = new Renderer(width, height);
        this.inputManager = new InputManager();
        balloonManager.addBalloon(new Balloon( 4, 4.0, "",100));
        monkeyManager.addMonkey(new TribeMonkey(tileManager.searchTile(4,5)));
        getCurrentLevel().crearCamino();
        //sentido antihorario empezando por arriba la izq
        this.asideUI = new UI(Color.BLUEUI, new ArrayList<>() {{
            add(new Point(gameWidth, height)); add(new Point(gameWidth, extension_y));
            add(new Point(width, extension_y)); add(new Point(width, height));
        }}, true);
        this.bottomUI = new UI(Color.BLUEUI2, new ArrayList<>() {{
            add(new Point(0, extension_y)); add(new Point(0, 0));
            add(new Point(width, 0)); add(new Point(width, extension_y));
        }}, false);

    }

    public void play(){
        init();
        //glfwWindowShouldClose devuelve true si se cierra la ventana
        while (!glfwWindowShouldClose(renderer.getWindow())) { //Game loop
            renderer.update();
            inputManager.update();
            balloonManager.updateBalloons();
            monkeyManager.updateMonkey();
            bulletManager.updateBullets();
        }
        renderer.clean();
    }

    public void doDamage(double damage){this.vida = vida - damage;}

    public Level getCurrentLevel(){return levelManager.currentLevel;}
}
