package managers;

import entities.monkeys.MonoCanon;
import graphics.Color;
import graphics.Renderer;
import graphics.UI;
import levels.Level;
import utilities.Timer;
import utilities.math.Point;

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
//        monkeyManager.addMonkey(new TribeMonkey(tileManager.searchTile(4,5)));
//        monkeyManager.addMonkey(new ClaveMachineMono(tileManager.searchTile(9,9)));
        monkeyManager.addMonkey(new MonoCanon(tileManager.searchTile(6,7)));
        getCurrentLevel().crearCamino();
        initUI();
    }

    private void initUI(){
        //sentido antihorario empezando por arriba la izq
        this.asideUI = new UI(Color.BLUEUI, new ArrayList<>() {{
            add(new Point(gameWidth, height)); add(new Point(gameWidth, extension_y));
            add(new Point(width, extension_y)); add(new Point(width, height));
        }}, false, 10, 15);
        this.bottomUI = new UI(Color.BLUEUI2, new ArrayList<>() {{
            add(new Point(0, extension_y)); add(new Point(0, 0));
            add(new Point(width, 0)); add(new Point(width, extension_y));
        }}, false, 10, 0);


        UI bui1 = bottomUI.addLayout(0.13f, Color.ORANGE);
            bui1.setVerticalAlign(true);
            bui1.addLayout(0.8f, Color.BLUE); //Monkey pfp
            bui1.addLayout(0.2f, Color.RED); //Monkey name
        UI bui2 = bottomUI.addLayout(0.29f, Color.ORANGE);
            UI bui21 = bui2.addSupport(0.3f); //Parte izq
                bui21.setVerticalAlign(true);
                bui21.addSupport(0.2f);
                bui21.addLayout(0.6f, Color.GREEN); //foto
            UI bui22 = bui2.addLayout(0.7f, Color.MAGENTA); //txt
        UI bui3 = bottomUI.addLayout(0.29f, Color.ORANGE);
            UI bui31 = bui3.addSupport(0.3f); //Parte izq
                bui31.setVerticalAlign(true);
                bui31.addSupport(0.2f);
                bui31.addLayout(0.6f, Color.GREEN); //foto
            UI bui32 = bui3.addLayout(0.7f, Color.MAGENTA); //txt
        UI bui4 = bottomUI.addLayout(0.29f, Color.ORANGE);
            UI bui41 = bui4.addSupport(0.3f); //Parte izq
                bui41.setVerticalAlign(true);
                bui41.addSupport(0.2f);
                bui41.addLayout(0.6f, Color.GREEN); //foto
            UI bui42 = bui4.addLayout(0.7f, Color.MAGENTA); //txt

        UI aui1 = asideUI.addLayout(0.5f, Color.ORANGE);
            aui1.setVerticalAlign(true);
                aui1.addLayout(0.25f, Color.GREEN);
                aui1.addLayout(0.25f, Color.GREEN);
                aui1.addLayout(0.25f, Color.GREEN);
                aui1.addLayout(0.25f, Color.GREEN);

        UI aui2 = asideUI.addLayout(0.5f, Color.ORANGE);
            aui2.setVerticalAlign(true);
                aui2.addLayout(0.25f, Color.GREEN);
                aui2.addLayout(0.25f, Color.GREEN);
                aui2.addLayout(0.25f, Color.GREEN);
                aui2.addLayout(0.25f, Color.GREEN);
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
