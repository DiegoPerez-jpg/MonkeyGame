package managers;

import entities.monkeys.TribeMonkey;
import graphics.Color;
import graphics.Renderer;
import graphics.UI;
import levels.Level;
import utilities.Timer;
import utilities.Util;
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
    public UIManager uiManager;
    public int width, height;
    public int gameWidth, gameHeight;
    public int extension_x, extension_y;
    public Renderer renderer;
    public double vida;
    public int tileSize;
    public Timer timer;
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
        this.uiManager = new UIManager();
        this.renderer = new Renderer(width, height);
        uiManager.initUI();
        this.inputManager = new InputManager();
        monkeyManager.addMonkey(new TribeMonkey(tileManager.searchTile(4,5)));
        getCurrentLevel().crearCamino();
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
            uiManager.checkTriggers();
        }
        renderer.clean();
    }

    public void doDamage(double damage){this.vida = vida - damage;}

    public Level getCurrentLevel(){return levelManager.currentLevel;}
}
