package entities.monkeys;

import entities.Entity;
import entities.balloons.Balloon;
import entities.bullets.Bullet;
import entities.bullets.BulletPrefab;
import graphics.Texture;
import graphics.Tile;
import managers.GameManager;
import utilities.math.Vector;
import utilities.Util;

import java.util.ArrayList;


public abstract class Monkey extends Entity {
    String[][] srcImagenesDeUpgrades;
    String[] srcFirstImagenDeUpgrades;
    String[] srcSecondImagenDeUpgrades;
    String[] srcThirdImagenDeUpgrades;
    String nombre;
    ArrayList<ArrayList<String>> tituloMejoras = new ArrayList<>();
    ArrayList<ArrayList<String>> descripcionMejoras = new ArrayList<>();

    protected float range;

    float cost;
    float rate;
    public BulletPrefab bp;
    float lastShotTime;
    int[] mejoras;
    boolean canDetectCamos = false;
    int cantidadBalasDisparadas = 1;
    public Tile tile;
    protected int idMono = 0;

    public Monkey(int idMono,int size, BulletPrefab bp, float rate, float cost, float range, String nombre, String skin, Tile tile) {
        super((new Texture(skin)), tile.getPosition(), size);
        mejoras = new int[]{0, 0, 0};
        this.bp = bp;
        this.rate = rate;
        this.cost = cost;
        this.range = range * GameManager.getInstance().tileSize;
        this.nombre = nombre;
        this.idMono = idMono;
        this.tile = tile;
        setupInformation();
    }

    public Monkey(int idMono,int size, BulletPrefab bp, float rate, float cost, float range, String nombre, String skin, Tile tile, boolean canDetectCamos) {
        super((new Texture(skin)), tile.getPosition(), size);
        mejoras = new int[]{0, 0, 0};
        this.bp = bp;
        this.rate = rate;
        this.cost = cost;
        this.range = range * GameManager.getInstance().tileSize;
        this.nombre = nombre;
        this.canDetectCamos = canDetectCamos;
        this.idMono = idMono;
        setupInformation();
    }

    private void setupInformation(){

        MonkeyXMLLoader monoXml = GameManager.getInstance().monkeyManager.monkeyXMLLoader;
        System.out.println("id"+ idMono + "size" + monoXml.nombres.get(0));
        this.nombre = monoXml.nombres.get(idMono);
        this.tituloMejoras = monoXml.tituloMejoras.get(idMono);
        this.descripcionMejoras = monoXml.descripcionMejoras.get(idMono);
        srcFirstImagenDeUpgrades = new String[5];
        srcSecondImagenDeUpgrades = new String[5];
        srcThirdImagenDeUpgrades = new String[5];

        ArrayList<ArrayList<String>> srcMejorasMono = monoXml.srcMejoras.get(idMono);

        // Asegúrate de que hay al menos 3 mejoras
        if (srcMejorasMono.size() >= 3) {
            for (int i = 0; i < 5; i++) {
                srcFirstImagenDeUpgrades[i] = srcMejorasMono.get(0).get(i);
                srcSecondImagenDeUpgrades[i] = srcMejorasMono.get(1).get(i);
                srcThirdImagenDeUpgrades[i] = srcMejorasMono.get(2).get(i);
            }
        }

        // Arreglo bidimensional para imágenes
        srcImagenesDeUpgrades = new String[][] {
                srcFirstImagenDeUpgrades,
                srcSecondImagenDeUpgrades,
                srcThirdImagenDeUpgrades
        };
    }

    protected Balloon getCloserBalloon() {
        for (Balloon b : GameManager.getInstance().balloonManager.getBalloons()) {
            if (b.isACamoBalloon && !canDetectCamos) {
                continue;
            }
            Vector distance = Util.createVector(b.position, this.position);
            if (distance.getMod() < this.range) {
                return b;
            }
        }
        return null;
    }

    public Bullet disparar(float time) {
        if (time - lastShotTime > rate || lastShotTime == 0) {
            for (int i = 0; i < cantidadBalasDisparadas; i++) {
                Balloon b = this.getCloserBalloon();
                if (b != null) {
                    lastShotTime = time;
                    return new Bullet(bp, 1, this.position, b, this);
                }
            }
        }
        return null;
    }


    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getCantidadBalasDisparadas() {
        return cantidadBalasDisparadas;
    }

    public void setCantidadBalasDisparadas(int cantidadBalasDisparadas) {
        this.cantidadBalasDisparadas = cantidadBalasDisparadas;
    }

    public void upgrade(int i){
        if (canIUpgrade(i)==false)return;
        switch (i){
            case 0:
                upgradeFirst();
                return ;
            case 1:
                upgradeSecond();
                return ;
            case 2:
                upgradeThird();
                return;
        }
    }

    public boolean canIUpgrade(int i){
        switch (i){
            case 0:
                return canIUpgradeFirst();
            case 1:
                return canIUpgradeSecond();
            case 2:
                return canIUpgradeThird();
        }
        return false;
    }
    public boolean canIUpgradeFirst() {
        if (this.mejoras[0] == 5) return false;
        if (this.mejoras[1] == 3) return false;
        if (this.mejoras[2] == 3) return false;
        if (this.mejoras[1] > 1 && this.mejoras[2] > 1) return false;
        return true;
    }

    public boolean canIUpgradeSecond() {
        if (this.mejoras[1] == 5) return false;
        if (this.mejoras[0] == 3) return false;
        if (this.mejoras[2] == 3) return false;
        if (this.mejoras[0] > 1 && this.mejoras[2] > 1) return false;
        return true;
    }

    public boolean canIUpgradeThird() {
        if (this.mejoras[2] == 5) return false;
        if (this.mejoras[0] == 3) return false;
        if (this.mejoras[1] == 3) return false;
        if (this.mejoras[0] > 1 && this.mejoras[1] > 1) return false;
        return true;
    }

    public String getFirstImageUpgrade(){
        return this.srcImagenesDeUpgrades[0][this.mejoras[0]];
    }
    public String getSecondImageUpgrade(){
        return this.srcImagenesDeUpgrades[1][this.mejoras[1]];
    }
    public String getThirdImageUpgrade(){
        return this.srcImagenesDeUpgrades[2][this.mejoras[2]];
    }
    void upgradeFirst(){

    }
    void upgradeSecond(){

    }
    void upgradeThird(){

    }
}