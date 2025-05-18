package managers;

import graphics.Renderer;
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
    private GameManager(){
        this.width = 1000;
        this.height = 800;
        this.time = 0;
    }

    public static GameManager getInstance() {
        if (instance == null) {instance = new GameManager();}
        return instance;
    }

    public void play(){
        //Estas clases llaman al gm en sus constructores por lo que no pueden ser creadas en el contructor del gm
        this.tileManager = new TileManager();
        this.renderer = new Renderer(width, height);
        this.inputManager = new InputManager();
        this.monkeyManager = new MonkeyManager();
        this.balloonManager = new BalloonManager();
        this.bulletManager = new BulletManager();
        this.levelManager = new LevelManager(null);
        //glfwWindowShouldClose devuelve true si se cierra la ventana
        while (!glfwWindowShouldClose(renderer.getWindow())) { //Game loop
            renderer.update();
            inputManager.update();
        }
        renderer.clean();
    }

    public void doDamage(double damage){
        this.vida = vida - damage;
    }
}
