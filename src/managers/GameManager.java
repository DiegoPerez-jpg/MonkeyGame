package managers;

import entities.bullets.BulletPrefab;
import entities.monkeys.Monkey;
import graphics.Color;
import graphics.Renderer;
import graphics.terrain.Tile;
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
    public Renderer renderer;
    public float time;
    public double vida;
    public int tileSize;
    public Timer timer;
    private GameManager(){
        this.width = 640;
        this.height = 480;
        this.timer = new Timer();
        this.tileSize = 32;
    }

    public static GameManager getInstance() {
        if (instance == null) {instance = new GameManager();}
        return instance;
    }

    public void play(){
        //Estas clases llaman al gm en sus constructores por lo que no pueden ser creadas en el contructor del gm
        this.tileManager = new TileManager();
        this.monkeyManager = new MonkeyManager();
        this.balloonManager = new BalloonManager();
        this.bulletManager = new BulletManager();
        this.renderer = new Renderer(width, height);
        this.inputManager = new InputManager();
        this.levelManager = new LevelManager(new ArrayList<>());
        Tile t = new Tile(Color.ROAD,new Point(1,1));
        Tile t2 = new Tile(Color.ROAD,new Point(10,1));
        levelManager.add(t);
        levelManager.add(t2);
        levelManager.add(new Tile(Color.ROAD,new Point(10,5)));
        levelManager.add(new Tile(Color.ROAD,new Point(15,5)));
        levelManager.add(new Tile(Color.ROAD,new Point(15,10)));
        levelManager.add(new Tile(Color.ROAD,new Point(10,10)));
        levelManager.add(new Tile(Color.ROAD,new Point(10,15)));
        levelManager.add(new Tile(Color.ROAD,new Point(12,15)));
        levelManager.crearCamino();

        balloonManager.addBalloon(new Balloon(new Point(0, 0), 4, 4.0, "",100));
        monkeyManager.addMonkey(new Monkey(1, BulletPrefab.BULLET,1,1,1,"normal","src/assets/monkeyDarderolvl1.png",t2));
        //glfwWindowShouldClose devuelve true si se cierra la ventana
        while (!glfwWindowShouldClose(renderer.getWindow())) { //Game loop
            renderer.update();
            inputManager.update();
            balloonManager.updateBalloons();
        }
        renderer.clean();
    }

    public void doDamage(double damage){
        this.vida = vida - damage;
    }
}
