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

/**
 * Clase Singleton que gestiona todos los elementos del juego, incluyendo lógica,
 * renderizado, entrada de usuario y control de entidades.
 */
public class GameManager {

    /** Instancia única del GameManager. */
    private static GameManager instance;

    /** Administrador de entrada de usuario. */
    public InputManager inputManager;

    /** Administrador de tiles (terreno). */
    public TileManager tileManager;

    /** Administrador de monos (torres). */
    public MonkeyManager monkeyManager;

    /** Administrador de globos enemigos. */
    public BalloonManager balloonManager;

    /** Administrador de balas. */
    public BulletManager bulletManager;

    /** Administrador de niveles. */
    public LevelManager levelManager;

    /** Ancho total de la ventana (juego + interfaz). */
    public int width;

    /** Alto total de la ventana (juego + interfaz). */
    public int height;

    /** Ancho del área de juego (sin interfaz). */
    public int gameWidth;

    /** Alto del área de juego (sin interfaz). */
    public int gameHeight;

    /** Extensión horizontal de la interfaz. */
    public int extension_x;

    /** Extensión vertical de la interfaz. */
    public int extension_y;

    /** Encargado del renderizado gráfico. */
    public Renderer renderer;

    /** Tiempo total transcurrido en el juego. */
    public float time;

    /** Vida restante del jugador. */
    public double vida;

    /** Tamaño de los tiles. */
    public int tileSize;

    /** Temporizador general. */
    public Timer timer;

    /** Interfaz de usuario lateral (derecha). */
    public UI asideUI;

    /** Interfaz de usuario inferior. */
    public UI bottomUI;

    /**
     * Constructor privado para aplicar el patrón Singleton.
     * Inicializa parámetros básicos del juego.
     */
    private GameManager() {
        this.gameWidth = 640;
        this.gameHeight = 480;
        this.extension_x = 256;
        this.extension_y = 128;
        this.width = gameWidth + extension_x;
        this.height = gameHeight + extension_y;
        this.timer = new Timer();
        this.tileSize = 32;
    }

    /**
     * Devuelve la instancia única de GameManager.
     *
     * @return instancia singleton de GameManager.
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Inicializa todos los elementos del juego, incluyendo entidades, nivel, UI, etc.
     */
    private void init() {
        this.tileManager = new TileManager();
        this.levelManager = new LevelManager();
        this.monkeyManager = new MonkeyManager();
        this.balloonManager = new BalloonManager();
        this.bulletManager = new BulletManager();
        this.renderer = new Renderer(width, height);
        this.inputManager = new InputManager();
        monkeyManager.addMonkey(new MonoCanon(tileManager.searchTile(6, 7)));
        getCurrentLevel().crearCamino();
        initUI();
    }

    /**
     * Inicializa la interfaz de usuario lateral e inferior.
     */
    private void initUI() {
        this.asideUI = new UI(Color.BLUEUI, new ArrayList<>() {{
            add(new Point(gameWidth, height));
            add(new Point(gameWidth, extension_y));
            add(new Point(width, extension_y));
            add(new Point(width, height));
        }}, false, 10, 15);

        this.bottomUI = new UI(Color.BLUEUI2, new ArrayList<>() {{
            add(new Point(0, extension_y));
            add(new Point(0, 0));
            add(new Point(width, 0));
            add(new Point(width, extension_y));
        }}, false, 10, 0);

        // Inicialización de secciones de la UI inferior
        UI bui1 = bottomUI.addLayout(0.13f, Color.ORANGE);
        bui1.setVerticalAlign(true);
        bui1.addLayout(0.8f, Color.BLUE);  // Foto del mono
        bui1.addLayout(0.2f, Color.RED);   // Nombre del mono

        UI bui2 = bottomUI.addLayout(0.29f, Color.ORANGE);
        UI bui21 = bui2.addSupport(0.3f);
        bui21.setVerticalAlign(true);
        bui21.addSupport(0.2f);
        bui21.addLayout(0.6f, Color.GREEN); // Foto
        UI bui22 = bui2.addLayout(0.7f, Color.MAGENTA); // Texto

        UI bui3 = bottomUI.addLayout(0.29f, Color.ORANGE);
        UI bui31 = bui3.addSupport(0.3f);
        bui31.setVerticalAlign(true);
        bui31.addSupport(0.2f);
        bui31.addLayout(0.6f, Color.GREEN);
        UI bui32 = bui3.addLayout(0.7f, Color.MAGENTA);

        UI bui4 = bottomUI.addLayout(0.29f, Color.ORANGE);
        UI bui41 = bui4.addSupport(0.3f);
        bui41.setVerticalAlign(true);
        bui41.addSupport(0.2f);
        bui41.addLayout(0.6f, Color.GREEN);
        UI bui42 = bui4.addLayout(0.7f, Color.MAGENTA);

        // UI lateral derecha
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

    /**
     * Comienza la ejecución del juego. Inicia todos los sistemas y entra en el bucle de juego principal.
     */
    public void play() {
        init();
        while (!glfwWindowShouldClose(renderer.getWindow())) {
            renderer.update();
            inputManager.update();
            balloonManager.updateBalloons();
            monkeyManager.updateMonkey();
            bulletManager.updateBullets();
        }
        renderer.clean();
    }

    /**
     * Aplica daño a la vida del jugador.
     *
     * @param damage cantidad de daño a aplicar.
     */
    public void doDamage(double damage) {
        this.vida = vida - damage;
    }

    /**
     * Obtiene el nivel actual activo.
     *
     * @return nivel actual.
     */
    public Level getCurrentLevel() {
        return levelManager.currentLevel;
    }
}
