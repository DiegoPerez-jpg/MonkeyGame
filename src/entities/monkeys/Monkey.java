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

public class Monkey extends Entity {
    String nombre;
    float range;
    float cost;
    float rate;
    BulletPrefab bp;
    float lastShotTime;
    int[] mejoras;
    public Monkey(int size, BulletPrefab bp, float rate, float cost, float range, String nombre, String skin, Tile tile) {
        super((new Texture(skin)), tile.getPosition(),size);
        mejoras = new int[]{0,0,0};
        this.bp = bp;
        this.rate = rate;
        this.cost = cost;
        this.range = range*GameManager.getInstance().tileSize;
        this.nombre = nombre;
    }
    protected Balloon getCloserBalloon(){
        for(Balloon b : GameManager.getInstance().balloonManager.getBalloons()) {
            Vector distance = Util.createVector(b.position, this.position);
            if (distance.getMod() < this.range) {
                return b;
            }
        }
        return null;
    }
    public Bullet disparar(float time){
        if(time-lastShotTime>rate || lastShotTime==0){
            Balloon b = this.getCloserBalloon();
            if(b!=null){
                lastShotTime = time;
                return new Bullet(bp,1 , this.position, b,this);
            }
        }
        return null;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
