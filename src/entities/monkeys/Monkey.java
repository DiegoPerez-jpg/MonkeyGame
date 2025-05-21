package entities.monkeys;

import entities.Entity;
import entities.balloons.Balloon;
import entities.bullets.Bullet;
import entities.bullets.BulletPrefab;
import graphics.Texture;
import graphics.terrain.Tile;
import managers.GameManager;
import utilities.math.Vector;
import utilities.Util;

public abstract class Monkey extends Entity {
    String nombre;
    protected float range;
    float cost;
    float rate;
    public BulletPrefab bp;
    float lastShotTime;
    int[] mejoras;
    boolean canDetectCamos = false;



    int cantidadBalasDisparadas = 1;

    public Monkey(int size, BulletPrefab bp, float rate, float cost, float range, String nombre, String skin, Tile tile) {
        super((new Texture(skin)), tile.getPosition(),size);
        mejoras = new int[]{0,0,0};
        this.bp = bp;
        this.rate = rate;
        this.cost = cost;
        this.range = range*GameManager.getInstance().tileSize;
        this.nombre = nombre;
    }
    public Monkey(int size, BulletPrefab bp, float rate, float cost, float range, String nombre, String skin, Tile tile, boolean canDetectCamos) {
        super((new Texture(skin)), tile.getPosition(),size);
        mejoras = new int[]{0,0,0};
        this.bp = bp;
        this.rate = rate;
        this.cost = cost;
        this.range = range*GameManager.getInstance().tileSize;
        this.nombre = nombre;
        this.canDetectCamos = canDetectCamos;
    }
    protected Balloon getCloserBalloon(){
        for(Balloon b : GameManager.getInstance().balloonManager.getBalloons()) {
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
    public Bullet disparar(float time){
        if(time-lastShotTime>rate || lastShotTime==0){
            for (int i = 0; i < cantidadBalasDisparadas; i++) {
                Balloon b = this.getCloserBalloon();
                if(b!=null){
                    lastShotTime = time;
                    return new Bullet(bp,1 , this.position, b,this);
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
    public boolean canIUpgradeFirst(){
        if(this.mejoras[0]==5)return false;
        if(this.mejoras[1]==3)return false;
        if(this.mejoras[1]==3)return false;
        if(this.mejoras[1]>1&&this.mejoras[2]>1)return false;
        return true;
    }
    public boolean canIUpgradeSecond(){
        if(this.mejoras[1]==5)return false;
        if(this.mejoras[0]==3)return false;
        if(this.mejoras[2]==3)return false;
        if(this.mejoras[0]>1&&this.mejoras[2]>1)return false;
        return true;
    }
    public boolean canIUpgradeThird(){
        if(this.mejoras[2]==5)return false;
        if(this.mejoras[0]==3)return false;
        if(this.mejoras[1]==3)return false;
        if(this.mejoras[0]>1&&this.mejoras[1]>1)return false;
        return true;
    }
}
