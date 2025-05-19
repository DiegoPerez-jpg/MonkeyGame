package entities.monkeys;

import entities.Entity;
import entities.balloons.Balloon;
import entities.bullets.Bullet;
import entities.bullets.BulletPrefab;
import graphics.Color;
import graphics.Texture;
import managers.GameManager;
import utilities.math.Point;
import utilities.math.Vector;
import utilities.util;

public class Monkey extends Entity {
    Texture skin;
    String nombre;
    float range;
    float cost;
    float rate;
    BulletPrefab bp;
    GameManager gm;
    float lastShotTime;

    public Monkey(int size, BulletPrefab bp, float rate, float cost, float range, String nombre, Color skin, Point position) {
        super((new Texture("")), position,size);
        this.bp = bp;
        this.rate = rate;
        this.cost = cost;
        this.range = range;
        this.nombre = nombre;
    }
    private Balloon getCloserBalloon(){
        for(Balloon b : gm.balloonManager.getBalloons()) {
            Vector distance = util.createVector(b.position, this.position);
            if (distance.getMod() < this.range) {
                return b;
            }
        }
        return null;
    }
    public Bullet disparar(float time){
        if(time-lastShotTime>rate){
            Balloon b = this.getCloserBalloon();
            if(b!=null){
                lastShotTime = time;
                return new Bullet(bp, this.position, b);
            }
        }
        return null;
    }
}
